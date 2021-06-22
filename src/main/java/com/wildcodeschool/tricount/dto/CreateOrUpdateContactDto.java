package com.wildcodeschool.tricount.dto;

public class CreateOrUpdateContactDto {

    private Integer id;
    private String name;
    private String email;
    
    public CreateOrUpdateContactDto(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public CreateOrUpdateContactDto() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}
