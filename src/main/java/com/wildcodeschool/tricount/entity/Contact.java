package com.wildcodeschool.tricount.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "contact")
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    
        @ManyToMany (mappedBy = "contacts")
    private List<ExpenseList> expenseLists;
    
    @ManyToMany (mappedBy ="beneficiaries")
    private List<Expense> expenses;
    
    @OneToMany(mappedBy = "owner", targetEntity = Expense.class)
    @Column(columnDefinition="int")
    private List<Expense> ownExpenses;
       
    public Contact() {
        
    }
    
    public Contact(String aName, String aEmail) {
        this.name = aName;
        this.email = aEmail;
    }

    public Contact(int aId, String aName, String aEmail) {
        this.id = aId;
        this.name = aName;
        this.email = aEmail;    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> aExpenses) {
        expenses = aExpenses;
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
 
    public List<Expense> getOwnExpenses() {
        return ownExpenses;
    }
    public void setOwnExpenses(List<Expense> aOwnExpenses) {
        ownExpenses = aOwnExpenses;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if( ! (obj instanceof Contact) ) return false;
        Contact other = (Contact) obj;
        return this.id == other.id;

    }
 
    @Override
    public int hashCode() {
        return name == null? 0 : name.length() + email.length() + id; // Ugly but correct!
    }
    
}
