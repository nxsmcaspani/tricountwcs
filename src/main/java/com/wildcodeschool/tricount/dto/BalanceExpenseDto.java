package com.wildcodeschool.tricount.dto;

import java.util.HashMap;

import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.entity.ExpenseList;

public class BalanceExpenseDto {

    private String name;
    private HashMap<Contact, Float> lstContacts;
    
    public BalanceExpenseDto() {
        
    }

    public BalanceExpenseDto(ExpenseList exp) {
        this.name = exp.getName();
        for (Expense dep : exp.getExpensesList()) {
            
        }
        for (Contact co : exp.getContacts()) {
            Float total = 0F;
            
        }
    }

    
    public String getName() {
        return name;
    }

    public void setName(String aName) {
        name = aName;
    }

    public HashMap<Contact, Float> getLstContacts() {
        return lstContacts;
    }

    public void setLstContacts(HashMap<Contact, Float> aLstContacts) {
        lstContacts = aLstContacts;
    }
    
    
    
}
