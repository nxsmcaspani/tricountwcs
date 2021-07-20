package com.wildcodeschool.tricount.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class UpdateExpenseDTO {
    private int id;
    private String name;
    private float amount;
    private Integer expenseListId;
    private Integer ownerId;
    private List<Integer> beneficiariesIds;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public UpdateExpenseDTO(int id, String name, Integer ownerId, float amount, LocalDate date) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.amount = amount;
        this.date = date;
    }

    public List<Integer> getBeneficiariesIds() {
        return beneficiariesIds;
    }
    public void setBeneficiariesIds(List<Integer> beneficiariesIds) {
        this.beneficiariesIds = beneficiariesIds;
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
    public float getAmount() {
        return amount;
    }
    public void setAmount(float aAmount) {
        amount = aAmount;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate aDate) {
        date = aDate;
    }
    public Integer getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
    public Integer getExpenseListId() {
        return expenseListId;
    }
    public void setExpenseListId(Integer aExpenseListId) {
        expenseListId = aExpenseListId;
    }

}
