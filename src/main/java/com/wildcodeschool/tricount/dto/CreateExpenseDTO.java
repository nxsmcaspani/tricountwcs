package com.wildcodeschool.tricount.dto;

import com.wildcodeschool.tricount.entity.Contact;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;

public class CreateExpenseDTO {
    private String name;
    private ReadExpenseListDto readExpenseListDto;
    private Integer expenseListId;
    private Contact owner;
    private ArrayList<Integer> idBeneficiaries = new ArrayList<>();

    private float amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expenseDate;

    public CreateExpenseDTO(String name, Contact owner, float amount, LocalDate expenseDate, Integer expenseListId) {
        this.name = name;
        this.owner = owner;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.expenseListId = expenseListId;
    }

    
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

    public ArrayList<Integer> getIdBeneficiaries() {
        return idBeneficiaries;
    }

    public void setIdBeneficiaries(ArrayList<Integer> aIdBeneficiaries) {
        idBeneficiaries = aIdBeneficiaries;
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
}
