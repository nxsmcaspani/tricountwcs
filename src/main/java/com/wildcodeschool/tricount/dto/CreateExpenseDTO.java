package com.wildcodeschool.tricount.dto;

import java.util.List;

public class CreateExpenseDTO {
    private String name;
    private ContactDto owner;
    private float amount;
    private List<ContactDto> beneficiaries;

    public CreateExpenseDTO(String name, ContactDto owner, float amount, List<ContactDto> contacts) {
        this.name = name;
        this.owner = owner;
        this.amount = amount;
        this.beneficiaries = contacts;
    }

    public CreateExpenseDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String aName) {
        name = aName;
    }

    public ContactDto getOwner() {
        return owner;
    }

    public void setOwner(ContactDto aOwner) {
        owner = aOwner;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float aAmount) {
        amount = aAmount;
    }

    public List<ContactDto> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(List<ContactDto> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }
}
