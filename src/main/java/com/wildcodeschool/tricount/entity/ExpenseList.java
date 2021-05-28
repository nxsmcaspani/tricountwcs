package com.wildcodeschool.tricount.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "expense_list")
public class ExpenseList {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    
    @OneToMany (mappedBy="expenseList")
    @Column(columnDefinition="int")
    private List<Expense> expensesList;
    
    @ManyToMany
    @JoinTable(name="contact_expense_list", 
        joinColumns = @JoinColumn(name="expenselist_id", columnDefinition="int"), 
        inverseJoinColumns = @JoinColumn(name = "contact_id", columnDefinition="int"))
    private List<Contact> contacts;
        
    public ExpenseList() {
        
    }
    
    public int getId() {
        return id;
    }
    public void setId(int aId) {
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
