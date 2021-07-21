package com.wildcodeschool.tricount.dto;

import com.wildcodeschool.tricount.entity.Contact;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class BalanceExpenseDto {
    private String name;
    private ReadExpenseListDto readExpenseListDto;
    private Integer expenseListId;
    private Contact owner;
    private ContactForBalanceDto beneficiary;

    private float amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expenseDate;

    public BalanceExpenseDto(String name, Contact owner, float amount, LocalDate expenseDate, Integer expenseListId) {
        this.name = name;
        this.owner = owner;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.expenseListId = expenseListId;
    }
    
    public BalanceExpenseDto(String name, Contact owner, float amount) {
        this.name = name;
        this.owner = owner;
        this.amount = amount;
    }

    public BalanceExpenseDto() {
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

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }


    public ContactForBalanceDto getBeneficiary() {
        return beneficiary;
    }


    public void setBeneficiary(ContactForBalanceDto aBeneficiary) {
        beneficiary = aBeneficiary;
    }
}
