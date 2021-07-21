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
        for (Expense dep : expList.getExpensesList()) {
            if (!dep.getName().equals(LABEL_BALANCE)) {
                balDto.setTotal(balDto.getTotal() + dep.getAmount());
            }
            repartitionUneDepense(dep, balDto);
        }
        balDto.setBalanceOk();
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
        float montant = arrondi2decimales(exp.getAmount() / exp.getBeneficiaries().size());
        System.out.println("Montant divisé calculé : " + montant);
        float compl = 0f;
        System.out.println("DEBUG : " + montant * exp.getBeneficiaries().size() + " comparé à : " + exp.getAmount());
        if (montant * exp.getBeneficiaries().size() != exp.getAmount()) {
            compl = arrondi2decimales(exp.getAmount() - (montant * exp.getBeneficiaries().size()));
            System.out.println("compl calculé : " + compl);
        }
        // affecte part de chacun à amountDue et retire ce montant de giveOrTake
        for (Contact ct : exp.getBeneficiaries()) {
            float totalPart = arrondi2decimales(montant + compl);
            convContactToDto(ct, balDto).addAmountDue(totalPart);
            convContactToDto(ct, balDto).addToAmountGiveOrTake(-(totalPart));
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
        // DecimalFormat df = new DecimalFormat("0.00");
        BigDecimal big = new BigDecimal(fl).setScale(2, RoundingMode.CEILING);
        return big.floatValue(); 
        
        // return (float) (Math.round(( fl) * Math.pow(10, 2)) / Math.pow(10, 2));
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
                expense.setBeneficiary(cts);
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
            expenseService.create(expenseMapper.convBalanceExpenseDtoToCreateExpenseDto(expense));
        }
    }


}
