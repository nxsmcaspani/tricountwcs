package com.wildcodeschool.tricount.dto;

public class ContactForBalanceDto {

    private int id;
    private String name;
    private String email;
    private float amountDue;
    private float amountSpend;

    public Integer getId() {
        return id;
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

    
    
}
