package com.wildcodeschool.tricount.dto;

import com.wildcodeschool.tricount.entity.Contact;

import java.util.List;

public class CreateExpenseListDto {
    private String name;
    private List<Integer> contactIds;
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getContactIds() {
        return contactIds;
    }

    public void setContactIds(List<Integer> contactIds) {
        this.contactIds = contactIds;
    }
}
