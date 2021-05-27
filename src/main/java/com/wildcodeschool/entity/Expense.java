package com.wildcodeschool.entity;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

public class Expense {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name="expenselist_id")
    private ExpenseList expenseList;

    @ManyToOne
    @JoinColumn(name="contact_id")
    private Contact owner;
    
    @ManyToMany
    @JoinTable(name="beneficiary", 
        joinColumns = @JoinColumn(name="expense_id"), 
        inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private List<Contact> beneficiaries;
        
    
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

    public ExpenseList getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(ExpenseList aExpenseList) {
        expenseList = aExpenseList;
    }

    public Contact getOwner() {
        return owner;
    }

    public void setOwner(Contact aOwner) {
        owner = aOwner;
    }

    public List<Contact> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(List<Contact> aBeneficiaries) {
        beneficiaries = aBeneficiaries;
    }
    
    
}
