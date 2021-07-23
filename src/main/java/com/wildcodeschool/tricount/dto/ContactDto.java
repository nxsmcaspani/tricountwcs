package com.wildcodeschool.tricount.dto;
import java.util.List;
public class ContactDto {
    private Integer id;
    private String name;
    private String email;

    public List<String> getListExpenseLists() {
        return listExpenseLists;
    }

    public void setListExpenseLists(List<String> listExpenseLists) {
        this.listExpenseLists = listExpenseLists;
    }

    private List<String> listExpenseLists;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ContactDto(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public ContactDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public ContactDto() {

    }
    
}
