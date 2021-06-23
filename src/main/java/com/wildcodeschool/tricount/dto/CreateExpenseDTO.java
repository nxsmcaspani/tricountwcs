package com.wildcodeschool.tricount.dto;

import com.wildcodeschool.tricount.entity.Contact;

public class CreateExpenseDTO {
    private String name;
    private ReadExpenseListDto readExpenseListDto;
    private Integer expenseListId;
    private Contact owner;
    private float amount;

    public CreateExpenseDTO(String name, Contact owner, float amount) {
        this.name = name;
        this.owner = owner;
        this.amount = amount;
    }

    public CreateExpenseDTO() {
    }

    public ReadExpenseListDto getReadExpenseListDto() {
        return readExpenseListDto;
    }

    public void setReadExpenseListDto(ReadExpenseListDto readExpenseListDto) {
        this.readExpenseListDto = readExpenseListDto;
    }

    public Integer getExpenseListId() {
        return expenseListId;
    }

    public void setExpenseListId(Integer expenseListId) {
        this.expenseListId = expenseListId;
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
