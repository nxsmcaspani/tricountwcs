package com.wildcodeschool.tricount.dto;

import java.math.BigDecimal;

public class ContactForBalanceDto extends ContactDto implements Comparable<ContactForBalanceDto> {

    /**
     * Montant de la part des achats pour la personne
     */
    private BigDecimal amountDue = new BigDecimal(0);
    
    /**
     * Montant des dépenses totales
     */
    private BigDecimal amountSpend = new BigDecimal(0);
    
    /**
     * Montant des dépenses faites hors opérations balance
     */
    private BigDecimal amountSpendHorsBalance = new BigDecimal(0);
    
    /**
     * Montant de la balance : dépenses - dues
     */
    private BigDecimal amountGiveOrTake = new BigDecimal(0);
    
    public ContactForBalanceDto(int id, String name, String email) {
        super(id, name, email);
    }

    public BigDecimal getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(BigDecimal aAmountDue) {
        amountDue = aAmountDue;
    }
    
    public void addAmountDue(BigDecimal aAmountDue) {
            amountDue = amountDue.add(aAmountDue);
    }

    public BigDecimal getAmountSpend() {
        return amountSpend;
    }

    public void setAmountSpend(BigDecimal aAmountSpend) {
        this.amountSpend = aAmountSpend;
    }

    public void addAmountSpend(BigDecimal aAmountSpend) {
            amountSpend = this.amountSpend.add(aAmountSpend);
    }
    
    public BigDecimal getAmountSpendHorsBalance() {
        return amountSpendHorsBalance;
    }

    public void setAmountSpendHorsBalance(BigDecimal amountSpendHorsBalance) {
        this.amountSpendHorsBalance = amountSpendHorsBalance;
    }

    public BigDecimal getAmountGiveOrTake() {
        return amountGiveOrTake;
    }

    public void setAmountGiveOrTake(BigDecimal aAmountGiveOrTake) {
        amountGiveOrTake = aAmountGiveOrTake;
    }

    public void addToAmountGiveOrTake(BigDecimal amount) {
        amountGiveOrTake = amountGiveOrTake.add(amount);
    }
    
    public BigDecimal getSolde() {
        return amountSpend.subtract(amountDue);
    }

    public void addAmountSpend(float aF) {
        amountSpend = amountSpend.add(new BigDecimal(Float.toString(aF)));
    }

    public void addToAmountGiveOrTake(float amount) {
        amountGiveOrTake = amountGiveOrTake.add(new BigDecimal(Float.toString(amount)));
    }

    public void addAmountDue(float fl) {
        amountDue = amountDue.add(new BigDecimal(Float.toString(fl)));
    }

    public void setAmountDue(float fl) {
        amountDue = new BigDecimal(Float.toString(fl));
    }

    public void setAmountSpend(float fl) {
        amountSpend = new BigDecimal(Float.toString(fl));
    }
    
    @Override
    public int compareTo(ContactForBalanceDto other) {
        if (this.getSolde().compareTo(other.getSolde()) < 0) {
            return -1;
        }
        if (this.getSolde() == other.getSolde()) {
            if (this.getId() != other.getId()) {
                return -1;
            }
            return 0;
        }
        return 1;        
    }


    
}
