package com.wildcodeschool.tricount.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildcodeschool.tricount.dto.BalanceExpenseListDto;
import com.wildcodeschool.tricount.dto.ContactForBalanceDto;
import com.wildcodeschool.tricount.dto.BalanceExpenseDto;
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
    
    public BalanceExpenseListDto getDtoBalanceExpense(int idList) {
        Optional<ExpenseList> exp = expenseListService.findById(idList);
        if (exp.isPresent()) {
            BalanceExpenseListDto balDto = convExpenseListToBalanceDto(exp.get());
            return balDto; // expenseListService.convertFromEntityToDto(idList);
        }
        return null;
    }
    
    
    /**
     * Conversion d'une ExpenseList en BalanceExpenseDto pour page Balance
     * @param ExpenseList
     * @return BalanceExpenseDto
     */
    private BalanceExpenseListDto convExpenseListToBalanceDto(ExpenseList expList) {
        List<ContactForBalanceDto> lstCt = new ArrayList<ContactForBalanceDto>();
        for (Contact ct : expList.getContacts()) {
            lstCt.add(new ContactForBalanceDto(ct.getId(), ct.getName(), ct.getEmail()));
        }
        BalanceExpenseListDto balDto = new BalanceExpenseListDto(expList.getName(), expList.getId(), lstCt);
        for (Expense dep : expList.getExpensesList()) {
            if (!dep.getName().equals(LABEL_BALANCE)) {
                balDto.setTotal(balDto.getTotal() + dep.getAmount());
            }
            repartitionUneDepense(dep, balDto);
        }
        balDto.setBalanceOk(checkBalanceOk(balDto.getLstContacts()));
        System.out.println("Balance ok ? : " + balDto.isBalanceOk());
        for (ContactForBalanceDto ct : balDto.getLstContacts()) {
            ct.setAmountSpendHorsBalance(ct.getAmountSpend());
        }
        if (balDto.isBalanceOk() == false ) {
            calculeBalance(balDto);
        } else {
            removeEquilibrageDepense(balDto);
        }
        return balDto;
    }
    
    private void removeEquilibrageDepense(BalanceExpenseListDto balDto) {
        for (BalanceExpenseDto exp : balDto.getLstExpenseDto()) {
            if (exp.getName().equals(LABEL_BALANCE)) {
              //  convContactToDto(exp.getIdBeneficiaries(), balDto)
            }
        }
        for (ContactForBalanceDto ct : balDto.getLstContacts()) {
            
        }
        
    }


    /**
     * Répartition d'une depense vers le owner et les beneficiaires de BalanceExpenseDto
     * @param exp
     */
    private void repartitionUneDepense(Expense exp, BalanceExpenseListDto balDto) {
        convContactToDto(exp.getOwner(), balDto).setAmountSpend(convContactToDto(exp.getOwner(), balDto).getAmountSpend() + exp.getAmount());
        convContactToDto(exp.getOwner(), balDto).addToAmountGiveOrTake(exp.getAmount());
        System.out.println("Depense Spend : " +exp.getAmount() + " ajouté à : " + exp.getOwner().getName() + ", total spend : " + convContactToDto(exp.getOwner(), balDto).getAmountSpend());
        float montant = arrondi2decimales(exp.getAmount() / exp.getBeneficiaries().size());
        System.out.println("Montant divisé calculé : " + montant);
        float compl = 0f;
        if (montant * exp.getBeneficiaries().size() != exp.getAmount()) {
            compl = arrondi2decimales(exp.getAmount() - (montant * exp.getBeneficiaries().size()));
            System.out.println("compl calculé : " + compl);
        }
        for (Contact ct : exp.getBeneficiaries()) {
            convContactToDto(ct, balDto).setAmountDue(convContactToDto(ct, balDto).getAmountDue() + montant + compl);
            convContactToDto(ct, balDto).addToAmountGiveOrTake(-(compl + montant));
            if (!exp.getName().equals(LABEL_BALANCE)) {
                // System.out.println("Ajout Beneficiary Due "+ exp.getName() +" à " + convContactToDto(ct, balDto).getName() + " montant " + montant + " & compl " + compl);
                // convContactToDto(ct, balDto).setAmountDueBeneficiaire(convContactToDto(ct, balDto).getAmountDueBeneficiaire() + montant + compl);
            }
            System.out.println("Ajout Due à " + convContactToDto(ct, balDto).getName() + " montant " + montant + " & compl " + compl + ", total due : " + convContactToDto(ct, balDto).getAmountDue());
            compl = 0f;
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
    private ContactForBalanceDto convContactToDto(Contact ct, BalanceExpenseListDto balDto) {
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
     * Calcule les expenses pour équilibrer la balance Dto
     */
    public void calculeBalance(BalanceExpenseListDto balExpenseDto) {
        // on fait 2 groupes, ceux qui doivent et ceux à qui ont doit
        Set<ContactForBalanceDto> tstContactsSpend = new TreeSet<ContactForBalanceDto>();
        Set<ContactForBalanceDto> tstContactsDue = new TreeSet<ContactForBalanceDto>();
        // On met chaque contact dans l'un ou l'autre, ou nulle part s'il est à 0  
        for (ContactForBalanceDto contact : balExpenseDto.getLstContacts()) {
            System.out.println("Contact : " + contact.getName() + " balance : " + contact.getSolde());
            if (contact.getSolde() > 0) {
                tstContactsSpend.add(contact);
                System.out.println("Contact : " + contact.getName() + " ajouté à Spend, total Spend : " + tstContactsSpend.size());
            } else if (contact.getSolde() < 0) {
                tstContactsDue.add(contact);
                System.out.println("Contact : " + contact.getName() + " ajouté à Due, total Due"  + tstContactsDue.size());
            }
        }
        System.out.println("Création des dues " + tstContactsDue.size() + " et des spend : " + tstContactsSpend.size());
        for (ContactForBalanceDto cts : tstContactsSpend) {
            Set<ContactForBalanceDto> toRemove = new TreeSet<ContactForBalanceDto>();
            for (ContactForBalanceDto ctd : tstContactsDue) {
                float montant = Math.min(cts.getSolde(), Math.abs(ctd.getSolde()));
                System.out.println("Traitement de " + ctd.getName() + " pour montant " + montant);
                BalanceExpenseDto expense = new BalanceExpenseDto(LABEL_BALANCE, contactService.findById(ctd.getId()), montant, 
                        LocalDate.now(), balExpenseDto.getIdOfExpenseList());
                expense.getBeneficiaries().add(cts.getId());
                balExpenseDto.getLstExpenseDto().add(expense);
                cts.setAmountDue(montant);
                ctd.setAmountSpend(montant);
                if (ctd.getSolde() == 0)
                    toRemove.add(ctd);
                if (cts.getSolde() == 0)
                    break;
            }
            tstContactsDue.removeAll(toRemove);
        }
        System.out.println("Fin des balances, expenses créées : " + balExpenseDto.getLstExpenseDto().size());
    }
    
    /**
     * Applique les expenses pour équilibrer la balance Dto
     */
    public void executeBalance(BalanceExpenseListDto balExpenseDto) {
        for (BalanceExpenseDto expense : balExpenseDto.getLstExpenseDto()) {
            expenseService.create(expense);
        }
    }


}
