package com.wildcodeschool.tricount.dto;

import java.util.ArrayList;
import java.util.List;

public class ReadExpenseListDto {
    private Integer id;
    private String name;
    private List<ContactDto> contactDtoList = new ArrayList<>();
//    private List<ReadExpenseDTO> readExpenseDTOS = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ContactDto> getContactDtoList() {
        return contactDtoList;
    }

    public void setContactDtoList(List<ContactDto> contactDtoList) {
        this.contactDtoList = contactDtoList;
    }
}
