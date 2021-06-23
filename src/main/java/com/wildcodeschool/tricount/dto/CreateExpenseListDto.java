package com.wildcodeschool.tricount.dto;

import java.util.ArrayList;
import java.util.List;

public class CreateExpenseListDto {
    private String name;
    private List<Integer> idContacts = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getIdContacts() {
        return idContacts;
    }

    public void setIdContacts(List<Integer> idContacts) {
        this.idContacts = idContacts;
    }
}
