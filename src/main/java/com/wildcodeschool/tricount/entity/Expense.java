package com.wildcodeschool.tricount.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "expense")
public class Expense {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private float amount;
    private LocalDate expenseDate;
    
    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name="expense_list_id", columnDefinition="int")
    private ExpenseList expenseList;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name="contact_id", columnDefinition="int")
    private Contact owner;
    
    @ManyToMany( cascade = CascadeType.ALL)
    @JoinTable(name="beneficiary", 
        joinColumns = @JoinColumn(name="expense_id", columnDefinition="int"), 
        inverseJoinColumns = @JoinColumn(name = "contact_id", columnDefinition="int"))
    private List<Contact> beneficiaries;

    public Expense() {
        
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float aAmount) {
        amount = aAmount;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate aExpenseDate) {
        expenseDate = aExpenseDate;
    }
    
    
}
