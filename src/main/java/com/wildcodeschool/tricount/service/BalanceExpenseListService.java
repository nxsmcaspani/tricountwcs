package com.wildcodeschool.tricount.service;

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
     * @return BalanceExpenseDto
     */
    private BalanceExpenseDto convExpenseListToBalanceDto(ExpenseList exp) {
        BalanceExpenseDto balDto = new BalanceExpenseDto();
        balDto.setName(exp.getName());
        balDto.setIdOfExpenseList(exp.getId());
        List<ContactForBalanceDto> lstCt = new ArrayList<ContactForBalanceDto>();
        for (Contact ct : exp.getContacts()) {
            lstCt.add(new ContactForBalanceDto(ct.getId(), ct.getName(), ct.getEmail()));
        }
        balDto.setLstContacts(lstCt);
      //  System.out.println("Creation BalanceExpenseDto");
        for (Expense dep : exp.getExpensesList()) {
            //      System.out.println("Ajout dépense " + dep.getName() + " amount : " + dep.getAmount() + " owner " + dep.getOwner().getName());
            balDto.setTotal(balDto.getTotal() + dep.getAmount());
            for (ContactForBalanceDto ctDto : balDto.getLstContacts()) {
                if (ctDto.getId()== dep.getOwner().getId()) {
                    ctDto.setAmountSpend(ctDto.getAmountSpend() + dep.getAmount());
                    // System.out.println("Ajout Spend à " + ctDto.getName());
                }
                for (Contact ct : dep.getBeneficiaries()) {
                    if (ctDto.getId() == ct.getId()) {
                        ctDto.setAmountDue(ctDto.getAmountDue() + (dep.getAmount() / dep.getBeneficiaries().size()));
                        //  System.out.println("Ajout Due à " + ctDto.getName() + " montant " + (dep.getAmount() / dep.getBeneficiaries().size()));
                    }
                }
            }
        }
        balDto.setBalanceOk(checkBalanceOk(balDto.getLstContacts()));
        System.out.println("Balance ok ? : " + balDto.isBalanceOk());
        return balDto;
    }

    private boolean checkBalanceOk(List<ContactForBalanceDto> lstContacts) {
        if (lstContacts.size() <2) {
            System.out.println("inf 2, true");
            return true;
        }
        for (int i = 1; i < lstContacts.size(); i++) {
            if (lstContacts.get(i).getAmountDue() != lstContacts.get(i-1).getAmountDue()) {
                System.out.println("val diff : " + lstContacts.get(i).getAmountDue() + " versus " + lstContacts.get(i-1).getAmountDue()); 
                return false;
            }
        }
        return true;
    }


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
    //    System.out.println("Création des dues " + tstContactsDue.size() + " et des spend : " + tstContactsSpend.size());
        for (ContactForBalanceDto cts : tstContactsSpend) {
            Set<ContactForBalanceDto> toRemove = new TreeSet<ContactForBalanceDto>();
            for (ContactForBalanceDto ctd : tstContactsDue) {
                float montant = Math.min(cts.getSolde(), Math.abs(ctd.getSolde()));
                // TODO: ajouter le bénéficiaire quand sera dispo dans le createExpenseDto
                lstExpenses.add(new CreateExpenseDTO("Balance operation", ctd.toContact(), montant));
                cts.setAmountDue(montant);
                ctd.setAmountSpend(montant);
                if (ctd.getSolde() == 0)
                    toRemove.add(ctd);
                if (cts.getSolde() == 0)
                    break;
            }
            tstContactsDue.removeAll(toRemove);
        }
        System.out.println("Fin des balances, expenses créées : " + lstExpenses.size());
        for (CreateExpenseDTO expense : lstExpenses) {
            System.out.println("Expense : " + expense.getAmount() + " from " + expense.getOwner().getName());
            // expenseService.create(expense);
        }
        
        
                
    }
    
    
    
}
