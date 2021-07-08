package com.wildcodeschool.tricount.dto;

import com.wildcodeschool.tricount.entity.Contact;

public class ContactForBalanceDto implements Comparable<ContactForBalanceDto> {

    private int id;
    private String name;
    private String email;
    private float amountDue;
    private float amountSpend;
    
    public Integer getId() {
        return id;
    }

    public Contact toContact() {
        return new Contact(this.id, this.name, this.email);
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ContactForBalanceDto(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public float getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(float aAmountDue) {
        amountDue = aAmountDue;
    }

    public float getAmountSpend() {
        return amountSpend;
    }

    public void setAmountSpend(float aAmountSpend) {
        amountSpend = aAmountSpend;
    }

    public float getSolde() {
        return amountSpend - amountDue;
    }
    
    @Override
    public int compareTo(ContactForBalanceDto other) {
        if (this.getSolde() < other.getSolde()) {
            return -1;
        }
        if (this.getSolde() == other.getSolde()) {
            return 0;
        }
        return 1;        
    }
}
