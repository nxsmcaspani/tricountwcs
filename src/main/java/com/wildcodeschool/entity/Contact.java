package com.wildcodeschool.entity;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    
    @ManyToMany
    @JoinTable(name="contact_expense_list", 
        joinColumns = @JoinColumn(name="contact_id"), 
        inverseJoinColumns = @JoinColumn(name = "expenselist_id"))
    private List<ExpenseList> expenseLists;
    
    @ManyToMany
    @JoinTable(name="beneficiary", 
        joinColumns = @JoinColumn(name="contact_id"), 
        inverseJoinColumns = @JoinColumn(name = "expense_id"))
    private List<Contact> beneficiaries;
    
    
    @OneToMany
    @JoinColumn(name="expense_id")
    private List<Expense> ownExpenses;
        
    public Long getId() {
        return id;
    }
    public void setId(Long aId) {
        id = aId;
    }
    public String getName() {
        return name;
    }
    public void setName(String aName) {
        name = aName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String aEmail) {
        email = aEmail;
    }
    public List<ExpenseList> getExpenseLists() {
        return expenseLists;
    }
    public void setExpenseLists(List<ExpenseList> aExpenseLists) {
        expenseLists = aExpenseLists;
    }
    public List<Contact> getBeneficiaries() {
        return beneficiaries;
    }
    public void setBeneficiaries(List<Contact> aBeneficiaries) {
        beneficiaries = aBeneficiaries;
    }
    public List<Expense> getOwnExpenses() {
        return ownExpenses;
    }
    public void setOwnExpenses(List<Expense> aOwnExpenses) {
        ownExpenses = aOwnExpenses;
    }
    
    
}
