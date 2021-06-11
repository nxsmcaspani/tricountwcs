package com.wildcodeschool.tricount.dto;
import java.util.List;

public class CreateOrUpdateExpenseListDto {
    private String name;
    private List<Integer> contactIds;
    private Integer id;
//    private ContactDto contactDtoList;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
