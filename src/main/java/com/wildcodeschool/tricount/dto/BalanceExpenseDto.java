package com.wildcodeschool.tricount.dto;

import java.util.ArrayList;
import java.util.List;

import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.entity.ExpenseList;

public class BalanceExpenseDto {

    private String name;
    private List<ContactForBalanceDto> lstContacts = new ArrayList<ContactForBalanceDto>();
    private Float total = 0f;
    private boolean balanceOk = false;
    private List<Expense> lstExpenseToDo = new ArrayList<Expense>();
    
    public BalanceExpenseDto(ExpenseList exp) {
        this.name = exp.getName();
        for (Contact ct : exp.getContacts()) {
            lstContacts.add(new ContactForBalanceDto(ct.getId(), ct.getName(), ct.getEmail()));
        }
        for (Expense dep : exp.getExpensesList()) {
            this.total += dep.getAmount();
            for (ContactForBalanceDto ctDto : lstContacts) {
                if (ctDto.getId()== dep.getOwner().getId()) {
                    ctDto.setAmountSpend(ctDto.getAmountDue() + dep.getAmount());
                    // TODO: si le owner pas présent dans les bénéficiaires, retirer le commentaire et faire +1 aussi aux bénefs
                    // ctDto.setAmountDue(dep.getAmount() / dep.getBeneficiaries().size() + 1);
                }
                for (Contact ct : dep.getBeneficiaries()) {
                    if (ctDto.getId() == ct.getId()) {
                        ctDto.setAmountDue(dep.getAmount() / dep.getBeneficiaries().size());
                    }
                }
            }
        }
        balanceOk = checkBalanceOk();
        lstExpenseToDo = calculateBalance();
    }
    
    private boolean checkBalanceOk() {
        if (lstContacts.size() <2) {
            return true;
        }
        for (int i = 1; i < lstContacts.size(); i++) {
            if (lstContacts.get(i).getAmountDue() != lstContacts.get(i-1).getAmountDue()) {
                return false;
            }
        }
        return true;
    }
    
    private List<Expense> calculateBalance() {
        List<Expense> lstExpense = new ArrayList<Expense>();
        // TODO
        
        
        return lstExpense;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String aName) {
        name = aName;
    }

    public List<ContactForBalanceDto> getLstContacts() {
        return lstContacts;
    }

    public void setLstContacts(List<ContactForBalanceDto> aLstContacts) {
        lstContacts = aLstContacts;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float aTotal) {
        total = aTotal;
    }

    public boolean isBalanceOk() {
        return balanceOk;
    }

    public void setBalanceOk(boolean aBalanceOk) {
        balanceOk = aBalanceOk;
    }

    public List<Expense> getLstExpenseToDo() {
        return lstExpenseToDo;
    }

    public void setLstExpenseToDo(List<Expense> aLstExpenseToDo) {
        lstExpenseToDo = aLstExpenseToDo;
    }
    
}
