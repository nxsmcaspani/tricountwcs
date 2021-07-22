package com.wildcodeschool.tricount.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildcodeschool.tricount.dto.BalanceExpenseDto;
import com.wildcodeschool.tricount.dto.ContactForBalanceDto;
import com.wildcodeschool.tricount.dto.CreateExpenseDTO;
import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.entity.ExpenseList;

@Service
public class BalanceExpenseListService {

    @Autowired
    private ExpenseListService expenseListService;
    
    @Autowired
    private ExpenseService expenseService;
    
    @Autowired
    private ContactService contactService;
    
    private static String LABEL_BALANCE = "Balance operation";
    
    public BalanceExpenseDto getDtoBalanceExpense(int idList) {
        Optional<ExpenseList> exp = expenseListService.findById(idList);
        if (exp.isPresent()) {
            BalanceExpenseDto balDto = convExpenseListToBalanceDto(exp.get());
            return balDto; // expenseListService.convertFromEntityToDto(idList);
        }
        return null;
    }
    
    
    /**
     * Conversion d'une ExpenseList en BalanceExpenseDto pour page Balance
     * @param ExpenseList
     * @return void => alimente la variable BalanceExpenseDto de la classe
     */
    private BalanceExpenseDto convExpenseListToBalanceDto(ExpenseList expList) {
        BalanceExpenseDto balDto = new BalanceExpenseDto();
        balDto.setName(expList.getName());
        balDto.setIdOfExpenseList(expList.getId());
        List<ContactForBalanceDto> lstCt = new ArrayList<ContactForBalanceDto>();
        for (Contact ct : expList.getContacts()) {
            lstCt.add(new ContactForBalanceDto(ct.getId(), ct.getName(), ct.getEmail()));
        }
        balDto.setLstContacts(lstCt);
        for (Expense dep : expList.getExpensesList()) {
            if (!dep.getName().equals(LABEL_BALANCE)) {
                balDto.setTotal(balDto.getTotal() + dep.getAmount());
            }
            repartitionUneDepense(dep, balDto);
        }
        balDto.setBalanceOk(checkBalanceOk(balDto.getLstContacts()));
        System.out.println("Balance ok ? : " + balDto.isBalanceOk());
        return balDto;
    }
    
    /**
     * Répartition d'une depense vers le owner et les beneficiaires de BalanceExpenseDto
     * @param exp
     */
    private void repartitionUneDepense(Expense exp, BalanceExpenseDto balDto) {
        convContactToDto(exp.getOwner(), balDto).setAmountSpend(convContactToDto(exp.getOwner(), balDto).getAmountSpend() + exp.getAmount());
        float montant = arrondi2decimales(exp.getAmount() / exp.getBeneficiaries().size());
        System.out.println("Montant calculé : " + montant);
        float compl = 0f;
        if (montant * exp.getBeneficiaries().size() != exp.getAmount()) {
            compl = arrondi2decimales(exp.getAmount() - (montant * exp.getBeneficiaries().size()));
            System.out.println("compl calculé : " + compl);
        }
        for (Contact ct : exp.getBeneficiaries()) {
            convContactToDto(ct, balDto).setAmountDue(convContactToDto(ct, balDto).getAmountDue() + montant + compl);
            compl = 0f;
            System.out.println("Ajout Due à " + convContactToDto(ct, balDto).getName() + " montant " + (montant + compl));
        }
    }
    
    /**
     * Arrondi un float à 2 décimales 
     * @param fl
     * @return fl reduit à 2 décimales
     */
    private float arrondi2decimales(float fl) {
        System.out.println("arrondi de : " + fl + " donne : " + (float) (Math.round(( fl) * Math.pow(10, 2)) / Math.pow(10, 2)));
        return (float) (Math.round(( fl) * Math.pow(10, 2)) / Math.pow(10, 2));
    }
    
    /**
     * Converti un Contact en Contact for Balance Dto
     * pour faire l'équivalence entre les contacts d'une dépense et les contactDto de la balanceDto
     * @param ct
     * @return ctDto
     */
    private ContactForBalanceDto convContactToDto(Contact ct, BalanceExpenseDto balDto) {
        for (ContactForBalanceDto ctDto : balDto.getLstContacts()) {
            if (ctDto.getId() == ct.getId()) {
                return ctDto;
            }
        }
        return null;
    }
    
    /**
     * Vérifie si tous les contacts ont leur due et spend égaux
     * @param lstContacts
     * @return boolean
     */
    private boolean checkBalanceOk(List<ContactForBalanceDto> lstContacts) {
        if (lstContacts.size() <2) {
            System.out.println("inf 2, true");
            return true;
        }
        for (ContactForBalanceDto contact : lstContacts) {
            if (contact.getAmountDue() != contact.getAmountSpend())
                return false;
        }
        return true;
    }

    /**
     * Fait les expenses pour équilibrer la balance Dto
     */
    public void executeBalance(BalanceExpenseDto balExpenseDto) {
        // pour chaque contactDto : amount Spend - amount Due
        // reste un solde positif ou négatif : 
        //     test : +10
        //     test3 : -10
        // la somme des soldes doit faire 0
        
        // on doit générer des expenses 1 vers 1
        
        List<CreateExpenseDTO> lstExpenses = new ArrayList<CreateExpenseDTO>();
        
        Set<ContactForBalanceDto> tstContactsSpend = new TreeSet<ContactForBalanceDto>();
        Set<ContactForBalanceDto> tstContactsDue = new TreeSet<ContactForBalanceDto>();
    //    System.out.println("Nbre contacts : " + balExpenseDto.getLstContacts().size());
        for (ContactForBalanceDto contact : balExpenseDto.getLstContacts()) {
    //        System.out.println("contact : " + contact.getName() + " due " + contact.getAmountDue() + " spend : " + contact.getAmountSpend() + " solde : " +contact.getSolde());
            if (contact.getSolde() > 0) {
                tstContactsSpend.add(contact);
    //            System.out.println("ajouté au Spend");
            } else if (contact.getSolde() < 0) {
                tstContactsDue.add(contact);
    //            System.out.println("ajouté au Due");
            } else {
    //            System.out.println("non ajouté, solde 0 ?");
            }
        }
        System.out.println("Création des dues " + tstContactsDue.size() + " et des spend : " + tstContactsSpend.size());
        for (ContactForBalanceDto cts : tstContactsSpend) {
            Set<ContactForBalanceDto> toRemove = new TreeSet<ContactForBalanceDto>();
            for (ContactForBalanceDto ctd : tstContactsDue) {
                float montant = Math.min(cts.getSolde(), Math.abs(ctd.getSolde()));
                Contact owner = contactService.findById(ctd.getId());
                CreateExpenseDTO expense = new CreateExpenseDTO(LABEL_BALANCE, owner, montant);
                expense.getIdBeneficiaries().add(cts.getId());
                expense.setExpenseDate(LocalDate.now());
                expense.setExpenseListId(balExpenseDto.getIdOfExpenseList());
                lstExpenses.add(expense);
                cts.setAmountDue(montant);
                ctd.setAmountSpend(montant);
                if (ctd.getSolde() == 0)
                    toRemove.add(ctd);
                if (cts.getSolde() == 0)
                    break;
            }
            tstContactsDue.removeAll(toRemove);
        }
        // System.out.println("Fin des balances, expenses créées : " + lstExpenses.size());
        for (CreateExpenseDTO expense : lstExpenses) {
            // System.out.println("Expense : " + expense.getAmount() + " from " + expense.getOwner().getName() + " id : " + expense.getOwner().getId());
            expenseService.create(expense);
        }
    }

}
