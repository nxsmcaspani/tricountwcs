package com.wildcodeschool.tricount.dto;

import java.util.ArrayList;
import java.util.List;

public class BalanceExpenseDto {

    private String name;
    private List<ContactForBalanceDto> lstContacts = new ArrayList<ContactForBalanceDto>();
    private Float total = 0f;
    private boolean balanceOk = false;
    private int idOfExpenseList;
    
    public String getName() {
        return name;
    }

    public void setName(String aName) {
        name = aName;
    }

    public List<ContactForBalanceDto> getLstContacts() {
        return lstContacts;
    }

    public void setLstContacts(List<ContactForBalanceDto> aLstContacts) {
        lstContacts = aLstContacts;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float aTotal) {
        total = aTotal;
    }

    public boolean isBalanceOk() {
        return balanceOk;
    }

    public void setBalanceOk(boolean aBalanceOk) {
        balanceOk = aBalanceOk;
    }

    public int getIdOfExpenseList() {
        return idOfExpenseList;
    }

    public void setIdOfExpenseList(int aIdOfExpenseList) {
        idOfExpenseList = aIdOfExpenseList;
    }
    
}
