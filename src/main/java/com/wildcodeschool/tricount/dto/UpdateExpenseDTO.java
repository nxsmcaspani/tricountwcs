package com.wildcodeschool.tricount.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.wildcodeschool.tricount.entity.Contact;

public class UpdateExpenseDTO {
    private int id;
    private String name;
    private ContactDto owner;
    private float amount;
    private Integer expenseListId;
    private Integer ownerId;
    private List<ContactForUpdateExpenseDto> ownerList;
    private List<ContactDto> beneficiaries;
    private List<ContactDto> expensesListContacts;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public UpdateExpenseDTO(int id, String name, ContactDto owner, float amount, LocalDate date) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.amount = amount;
        this.date = date;
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

    public void setBeneficiaries(List<ContactDto> aBeneficiaries) {
        beneficiaries = aBeneficiaries;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate aDate) {
        date = aDate;
    }

    public List<ContactForUpdateExpenseDto> getOwnerList() {
        return ownerList;
    }

    public void setOwnerList(List<ContactForUpdateExpenseDto> aOwnerList) {
        ownerList = aOwnerList;
    }

    public List<ContactDto> getExpensesListContacts() {
        return expensesListContacts;
    }

    public void setExpensesListContacts(List<ContactDto> aExpensesListContacts) {
        expensesListContacts = aExpensesListContacts;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer aOwnerId) {
        ownerId = aOwnerId;
    }

    public Integer getExpenseListId() {
        return expenseListId;
    }

    public void setExpenseListId(Integer aExpenseListId) {
        expenseListId = aExpenseListId;
    }
    
    
}
