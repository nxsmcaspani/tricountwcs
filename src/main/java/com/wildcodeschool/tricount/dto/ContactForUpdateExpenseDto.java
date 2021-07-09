package com.wildcodeschool.tricount.dto;

public class ContactForUpdateExpenseDto extends ContactDto {

    private Boolean selected;

    public ContactForUpdateExpenseDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ContactForUpdateExpenseDto(int aId, String aName, String aEmail) {
        super(aId, aName, aEmail);
        // TODO Auto-generated constructor stub
    }

    public ContactForUpdateExpenseDto(String aName, String aEmail) {
        super(aName, aEmail);
        // TODO Auto-generated constructor stub
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean aSelected) {
        selected = aSelected;
    }
    
    
}
