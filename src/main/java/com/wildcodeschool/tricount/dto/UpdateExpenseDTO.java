package com.wildcodeschool.tricount.dto;

import java.util.List;

import com.wildcodeschool.tricount.entity.Contact;

public class UpdateExpenseDTO {
    private int id;
    private String name;
    private Contact owner;
    private float amount;
    private List<Contact> beneficiaries;

    public UpdateExpenseDTO(int id, String name, Contact owner, float amount) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.amount = amount;
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
    public float getAmount() {
        return amount;
    }
    public void setAmount(float aAmount) {
        amount = aAmount;
    }

    public List<Contact> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(List<Contact> aBeneficiaries) {
        beneficiaries = aBeneficiaries;
    }
    
}
