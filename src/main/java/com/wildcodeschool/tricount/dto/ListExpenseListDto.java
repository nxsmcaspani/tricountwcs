package com.wildcodeschool.tricount.dto;
import com.wildcodeschool.tricount.entity.Expense;

import java.util.ArrayList;
import java.util.List;

public class ListExpenseListDto {
    private Integer id;
    private String name;
    private List<Expense> expenses = new ArrayList<>();

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

    public List<Expense> getIdExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
