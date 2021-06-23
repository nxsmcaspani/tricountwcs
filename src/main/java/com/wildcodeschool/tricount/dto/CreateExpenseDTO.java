package com.wildcodeschool.tricount.dto;

import java.util.Date;

import com.wildcodeschool.tricount.entity.Contact;

public class CreateExpenseDTO {
    private String name;
    private Contact owner;
    private float amount;

    public CreateExpenseDTO(String name, Contact owner, float amount) {
        this.name = name;
        this.owner = owner;
        this.amount = amount;
    }
    //
    // public CreateExpenseDTO(String name, float amount) {
    // this.name = name;
    // this.amount = amount;
    // }

    // public CreateExpenseDTO(String name, String amount) {
    // this.name = name;
    // this.amount = amount;
    // }

    public CreateExpenseDTO() {
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float aAmount) {
        amount = aAmount;
    }

}
