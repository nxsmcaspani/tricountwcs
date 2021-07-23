package com.wildcodeschool.tricount.dto;

import java.util.ArrayList;
import java.util.List;

public class BalanceExpenseListDto {

    private String name;
    private List<ContactForBalanceDto> lstContacts = new ArrayList<ContactForBalanceDto>();
    private Float total = 0f;
    private boolean balanceOk = false;
    private int idOfExpenseList;
    private List<BalanceExpenseDto> lstExpenseDto = new ArrayList<BalanceExpenseDto>();
    
    
    public BalanceExpenseListDto(String name, int idOfExpenseList, List<ContactForBalanceDto> lstContacts) {
        this.name = name;
        this.idOfExpenseList = idOfExpenseList;
        this.lstContacts = lstContacts;
    }
    
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

    public void setBalanceOk() {
        balanceOk = checkBalanceOk();
    }

    public int getIdOfExpenseList() {
        return idOfExpenseList;
    }

    public void setIdOfExpenseList(int aIdOfExpenseList) {
        idOfExpenseList = aIdOfExpenseList;
    }

    public List<BalanceExpenseDto> getLstExpenseDto() {
        return lstExpenseDto;
    }

    public void setLstExpenseDto(List<BalanceExpenseDto> aLstExpenseDto) {
        lstExpenseDto = aLstExpenseDto;
    }

    /**
     * Vérifie si tous les contacts ont leur due et spend égaux
     * @param lstContacts
     * @return boolean
     */
    private boolean checkBalanceOk() {
        System.out.println("CheckBalanceOk");
        if (lstContacts.size() <2) {
            System.out.println("inf 2, true");
            return true;
        }
        for (ContactForBalanceDto contact : lstContacts) {
            System.out.println("for contact " + contact.getName());
            if (contact.getAmountDue().compareTo(contact.getAmountSpend()) != 0) {
                System.out.println("amount différent : " + contact.getAmountDue() + " versus " + contact.getAmountSpend());
                return false;
            }
        }
        return true;
    }
}
