package com.wildcodeschool.entity;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

public class ExpenseList {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @OneToMany (mappedBy="expenseList")
    private List<Expense> expensesList;
    
    @ManyToMany
    @JoinTable(name="contact_expense_list", 
        joinColumns = @JoinColumn(name="expenselist_id"), 
        inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private List<Contact> contacts;
        
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
    public List<Expense> getExpenseList() {
        return expensesList;
    }
    public void setExpenseList(List<Expense> aExpenseList) {
        expensesList = aExpenseList;
    }
    public List<Contact> getContacts() {
        return contacts;
    }
    public void setContacts(List<Contact> aContacts) {
        contacts = aContacts;
    }
    
    
    
}
