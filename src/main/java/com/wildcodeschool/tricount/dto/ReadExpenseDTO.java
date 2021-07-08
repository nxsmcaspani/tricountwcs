package com.wildcodeschool.tricount.dto;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.Date;

import com.wildcodeschool.tricount.entity.Contact;

public class ReadExpenseDTO {

    private int id;
    private String name;
    private Contact owner;
    private LocalDate expenseDate;
    private float amount;
    
    
    public ReadExpenseDTO(int aId, String aName, Contact aOwner, LocalDate aExpenseDate, float aAmount) {
        super();
        id = aId;
        name = aName;
        owner = aOwner;
        expenseDate = aExpenseDate;
        amount = aAmount;
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
    public Contact getOwner() {
        return owner;
    }
    public void setOwner(Contact aOwner) {
        owner = aOwner;
    }
    public LocalDate getExpenseDate() {
        return expenseDate;
    }
    public void setExpenseDate(LocalDate aExpenseDate) {
        expenseDate = aExpenseDate;
    }
    public float getAmount() {
        return amount;
    }
    public void setAmount(float aAmount) {
        amount = aAmount;
    }
    
    
}
