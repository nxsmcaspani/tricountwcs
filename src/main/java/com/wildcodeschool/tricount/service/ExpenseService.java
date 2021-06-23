package com.wildcodeschool.tricount.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildcodeschool.tricount.dto.CreateExpenseDTO;
import com.wildcodeschool.tricount.dto.ReadExpenseDTO;
import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repo;

    public List<Expense> getAll() {
        return repo.findAll();
    }

    public ReadExpenseDTO getById(int id) {
         Expense expense = repo.getById(id);
        return mapExpenseToReadExpenseDTO(expense);
    }

    public Expense create(CreateExpenseDTO expenseDTO) {
        Expense expense = mapExpenseDTOToExpense(expenseDTO);
        return repo.save(expense);
    }

    public static ReadExpenseDTO mapExpenseToReadExpenseDTO(Expense expense) {
        if (expense == null) {
            return null;
        }

        ReadExpenseDTO dto = new ReadExpenseDTO(
                expense.getId(),
                expense.getName(),
                expense.getOwner(),
                expense.getExpenseDate(),
                expense.getAmount());
        return dto;
    }

    public static Expense mapExpenseDTOToExpense(CreateExpenseDTO expenseDTO) {
        Expense exp = new Expense();
        exp.setAmount(expenseDTO.getAmount());
        exp.setExpenseDate(LocalDate.now());
        exp.setName(expenseDTO.getName());
        exp.setOwner(expenseDTO.getOwner());
        return exp;
    }
}
