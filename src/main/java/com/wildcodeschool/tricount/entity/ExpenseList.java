package com.wildcodeschool.tricount.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

@Entity
@Table(name = "expense_list")
public class ExpenseList {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private LocalDate date;

    
    @OneToMany (mappedBy="expenseList", cascade = CascadeType.ALL)
    @Column(columnDefinition="int")
    private List<Expense> expensesList;
    
    @ManyToMany( cascade = CascadeType.ALL)
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
    public List<Expense> getExpensesList() {
        return expensesList;
    }
    public void setExpensesList(List<Expense> aExpenseList) {
        expensesList = aExpenseList;
    }
    public List<Contact> getContacts() {
        return contacts;
    }
    public List<Integer> getContactsIds(){
        return contacts.stream().map(Contact::getId).collect(Collectors.toList());
    }
    public void setContacts(List<Contact> aContacts) {
        contacts = aContacts;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate aDate) {
        date = aDate;
    }
}
