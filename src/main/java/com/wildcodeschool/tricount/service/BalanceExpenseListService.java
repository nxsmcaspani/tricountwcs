package com.wildcodeschool.tricount.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.wildcodeschool.tricount.mappers.ExpenseMapper;

@Service
public class BalanceExpenseListService {

    @Autowired
    private ExpenseListService expenseListService;
    
    @Autowired
    private ExpenseService expenseService;
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private ExpenseMapper expenseMapper;
    
    private static String LABEL_BALANCE = "Balance operation";
    
    /**
     * Construit une BalanceExpenseListDto à partir de l'id d'une ExpenseList
     * 
     * @param id ExpenseList
     * @return BalanceExpenseListDto
     */
    public BalanceExpenseListDto getDtoBalanceExpense(int idList) {
        Optional<ExpenseList> exp = expenseListService.findById(idList);
        if (exp.isPresent()) {
            BalanceExpenseListDto balDto = convExpenseListToBalanceDto(exp.get());
            return balDto; 
        }
        return null;
    }
    
    
    /**
     * Création d'une ExpenseList en BalanceExpenseDto pour page Balance
     * @param ExpenseList
     * @return BalanceExpenseDto
     */
    private BalanceExpenseListDto convExpenseListToBalanceDto(ExpenseList expList) {
        List<ContactForBalanceDto> lstCt = new ArrayList<ContactForBalanceDto>();
        for (Contact ct : expList.getContacts()) {
            lstCt.add(new ContactForBalanceDto(ct.getId(), ct.getName(), ct.getEmail()));
        }
        BalanceExpenseListDto balDto = new BalanceExpenseListDto(expList.getName(), expList.getId(), lstCt);
        System.out.println("Répartition dépenses");
        for (Expense dep : expList.getExpensesList()) {
            if (!dep.getName().equals(LABEL_BALANCE)) {
                balDto.setTotal(balDto.getTotal() + dep.getAmount());
            }
            repartitionUneDepense(dep, balDto);
        }
        balDto.setBalanceOk();
        System.out.println("Balance dépenses équilibrées : " + balDto.isBalanceOk());
        for (ContactForBalanceDto ct : balDto.getLstContacts()) {
            ct.setAmountSpendHorsBalance(ct.getAmountSpend());
        }
        if (balDto.isBalanceOk() == false ) {
            calculeBalance(balDto);
        } else {
            removeRemboursementAmountSpend(expList.getExpensesList(), balDto);
        }
        return balDto;
    }

    /**
     * Retire des dépenses l'argent remboursé par les autres
     * uniquement pour les listes avec balance ok
     * @param expList 
     */
    private void removeRemboursementAmountSpend(List<Expense> expList, BalanceExpenseListDto balDto) {
        System.out.println("remove Balance expenses");
        for (Expense exp : expList) {
            System.out.println("exp : " + exp.getName() );
            if (exp.getName().equals(LABEL_BALANCE)) {
                for (ContactForBalanceDto ctDto : balDto.getLstContacts()) {
                    if (ctDto.getId() == exp.getBeneficiaries().get(0).getId()) {
                        ctDto.addAmountSpend(-exp.getAmount());
                        ctDto.setAmountSpendHorsBalance(ctDto.getAmountSpend());
                    }
                }
            }
        }
    }
    
    /**
     * Répartition d'une depense vers le owner et les beneficiaires de BalanceExpenseDto
     * @param exp
     */
    private void repartitionUneDepense(Expense exp, BalanceExpenseListDto balDto) {
        // ajout dépense
        convContactToDto(exp.getOwner(), balDto).addAmountSpend(exp.getAmount());
        convContactToDto(exp.getOwner(), balDto).addToAmountGiveOrTake(exp.getAmount());
        System.out.println("Depense Spend : " +exp.getAmount() + " ajouté à : " + exp.getOwner().getName() + ", total spend : " + convContactToDto(exp.getOwner(), balDto).getAmountSpend());
        // calcul part de chacun avec complément pour arrondi
        BigDecimal montant = new BigDecimal(exp.getAmount() / exp.getBeneficiaries().size()).setScale(2, RoundingMode.HALF_UP);
        System.out.println("Montant divisé calculé : " + montant);
        BigDecimal compl = new BigDecimal(0);
        System.out.println("DEBUG : " + montant.multiply(new BigDecimal(exp.getBeneficiaries().size())) + " comparé à : " + new BigDecimal(exp.getAmount()).setScale(2, RoundingMode.HALF_UP));
        if (montant.multiply(new BigDecimal(exp.getBeneficiaries().size())).compareTo(new BigDecimal(exp.getAmount()).setScale(2, RoundingMode.HALF_UP))  != 0) {
            compl = new BigDecimal(exp.getAmount()).subtract(montant.multiply(new BigDecimal(exp.getBeneficiaries().size()))).setScale(2, RoundingMode.HALF_UP);
            System.out.println("compl calculé : " + compl);
        }
        // affecte part de chacun à amountDue et retire ce montant de giveOrTake
        for (Contact ct : exp.getBeneficiaries()) {
            BigDecimal totalPart = montant.add(compl);
            convContactToDto(ct, balDto).addAmountDue(totalPart);
            convContactToDto(ct, balDto).addToAmountGiveOrTake(totalPart.negate());
            System.out.println("Ajout Due à " + convContactToDto(ct, balDto).getName() + " montant " + montant + " & compl " + compl + ", total due : " + convContactToDto(ct, balDto).getAmountDue());
            compl = new BigDecimal(0);
        }
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
     * Calcule les expenses pour équilibrer la balance Dto
     */
    public void calculeBalance(BalanceExpenseListDto balExpenseDto) {
        // on fait 2 groupes, ceux qui doivent et ceux à qui ont doit
        Set<ContactForBalanceDto> tstContactsSpend = new TreeSet<ContactForBalanceDto>();
        Set<ContactForBalanceDto> tstContactsDue = new TreeSet<ContactForBalanceDto>();
        // On met chaque contact dans l'un ou l'autre, ou nulle part s'il est à 0  
        for (ContactForBalanceDto contact : balExpenseDto.getLstContacts()) {
            System.out.println("Contact : " + contact.getName() + " balance : " + contact.getSolde());
            if (contact.getSolde().compareTo(BigDecimal.ZERO) > 0) {
                tstContactsSpend.add(contact);
                System.out.println("Contact : " + contact.getName() + " ajouté à Spend, total Spend : " + tstContactsSpend.size());
            } else if (contact.getSolde().compareTo(BigDecimal.ZERO) < 0) {
                tstContactsDue.add(contact);
                System.out.println("Contact : " + contact.getName() + " ajouté à Due, total Due"  + tstContactsDue.size());
            }
        }
        System.out.println("Création des dues " + tstContactsDue.size() + " et des spend : " + tstContactsSpend.size());
        
        boolean equilibreOk = true;
        int cpt = 10;
        do {
            System.out.println("prepare Expense, passe " + cpt);
            for (ContactForBalanceDto cts : tstContactsSpend) {
              for (ContactForBalanceDto ctd : tstContactsDue) {
                  if (ctd.getSolde().compareTo(BigDecimal.ZERO) != 0) {
                      BigDecimal montant = cts.getSolde().min(ctd.getSolde().abs());
                      if (montant.compareTo(BigDecimal.ZERO) != 0) {
                          System.out.println("Traitement de " + ctd.getName() + " pour montant " + montant + " vers "+cts.getName());
                          BalanceExpenseDto expense = new BalanceExpenseDto(LABEL_BALANCE, contactService.findById(ctd.getId()), montant.floatValue(), 
                                  LocalDate.now(), balExpenseDto.getIdOfExpenseList());
                          expense.setBeneficiary(cts);
                          balExpenseDto.getLstExpenseDto().add(expense);
                          cts.addAmountDue(montant);
                          ctd.addAmountSpend(montant);
                      } else {
                          System.out.println("Montant à 0 !");
                      }
                      if (cts.getSolde().compareTo(BigDecimal.ZERO) == 0) {
                          System.out.println(cts.getName() + " solde a 0, sortie spend");
                          break;
                      }
                  }
              }
          }
            
            for (ContactForBalanceDto contact : tstContactsSpend) {
                if (contact.getSolde().compareTo(BigDecimal.ZERO) != 0) {
                    System.out.println(" =====> Set Spend : solde de : " + contact.getName() + " non vide ! " +contact.getSolde());
                    equilibreOk = false;
                }
            }
            for (ContactForBalanceDto contact : tstContactsDue) {
                if (contact.getSolde().compareTo(BigDecimal.ZERO) != 0) {
                    System.out.println(" =====> Set Due : solde de : " + contact.getName() + " non vide ! " +contact.getSolde());
                    equilibreOk = false;
                }
            }
            cpt -=1;
        } while ((cpt>0)&&(equilibreOk == false));
        
        System.out.println("Fin des balances, expenses créées : " + balExpenseDto.getLstExpenseDto().size());
    }
    
    
    /**
     * Applique les expenses pour équilibrer la balance Dto
     */
    public void executeBalance(BalanceExpenseListDto balExpenseDto) {
        for (BalanceExpenseDto expense : balExpenseDto.getLstExpenseDto()) {
            expenseService.create(expenseMapper.convBalanceExpenseDtoToCreateExpenseDto(expense));
        }
    }


}
