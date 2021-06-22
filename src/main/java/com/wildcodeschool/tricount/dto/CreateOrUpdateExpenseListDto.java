package com.wildcodeschool.tricount.dto;
import java.util.ArrayList;

public class CreateOrUpdateExpenseListDto {
    private Integer id;
    private String name;
    private ArrayList<ContactDto> contacts;
    private ArrayList<ContactDto> participants;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ContactDto> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<ContactDto> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<ContactDto> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<ContactDto> participants) {
        this.participants = participants;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
