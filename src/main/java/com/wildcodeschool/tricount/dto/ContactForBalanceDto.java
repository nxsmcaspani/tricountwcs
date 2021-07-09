package com.wildcodeschool.tricount.dto;

import com.wildcodeschool.tricount.entity.Contact;

public class ContactForBalanceDto extends ContactDto implements Comparable<ContactForBalanceDto> {

    private float amountDue;
    private float amountSpend;
    
    public Contact toContact() {
        System.out.println("toContact : " + this.getId());
        return new Contact(this.getId(), this.getName(), this.getEmail());
    }
    
    public ContactForBalanceDto(int id, String name, String email) {
        super(id, name, email);
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
