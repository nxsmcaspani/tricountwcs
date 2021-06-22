package com.wildcodeschool.tricount.dto;
import java.util.ArrayList;
import java.util.List;

public class CreateOrUpdateExpenseListDto {
    private Integer id;
    private String name;
    private List<Integer> idContacts = new ArrayList<>();


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

    public List<Integer> getIdContacts() {
        return idContacts;
    }

    public void setIdContacts(List<Integer> idContacts) {
        this.idContacts = idContacts;
    }

    public void showContacts(){
        for(int contact : this.idContacts) {
            System.out.println(contact);
        }
    }
}
