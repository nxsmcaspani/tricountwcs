package com.wildcodeschool.tricount.dto;

public class ContactForBalanceDto extends ContactDto implements Comparable<ContactForBalanceDto> {

    /**
     * Montant de la part des achats pour la personne
     */
    private float amountDue;
    
    /**
     * Montant des dépenses totales
     */
    private float amountSpend;
    
    /**
     * Montant des dépenses faites hors opérations balance
     */
    private float amountSpendHorsBalance;
    
    /**
     * Montant de la balance : dépenses - dues
     */
    private float amountGiveOrTake;
    
    public ContactForBalanceDto(int id, String name, String email) {
        super(id, name, email);
    }

    public float getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(float aAmountDue) {
        amountDue = aAmountDue;
    }
    
    public void addAmountDue(float aAmountDue) {
        amountDue += aAmountDue;
    }

    public float getAmountSpend() {
        return amountSpend;
    }

    public void setAmountSpend(float aAmountSpend) {
        this.amountSpend = aAmountSpend;
    }

    public void addAmountSpend(float aAmountSpend) {
        this.amountSpend += aAmountSpend;
    }
    
    public float getAmountSpendHorsBalance() {
        return amountSpendHorsBalance;
    }

    public void setAmountSpendHorsBalance(float amountSpendHorsBalance) {
        this.amountSpendHorsBalance = amountSpendHorsBalance;
    }

    public float getAmountGiveOrTake() {
        return amountGiveOrTake;
    }

    public void setAmountGiveOrTake(float aAmountGiveOrTake) {
        amountGiveOrTake = aAmountGiveOrTake;
    }

    public void addToAmountGiveOrTake(float amount) {
        amountGiveOrTake += amount;
    }
    
    public float getSolde() {
        return amountSpend - amountDue;
    }
    
    @Override
    public int compareTo(ContactForBalanceDto other) {
        if (this.getSolde() < other.getSolde()) {
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
