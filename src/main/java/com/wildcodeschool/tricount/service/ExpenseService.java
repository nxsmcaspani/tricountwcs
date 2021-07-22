package com.wildcodeschool.tricount.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.wildcodeschool.tricount.mappers.ExpenseListMapper;
import com.wildcodeschool.tricount.mappers.ExpenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildcodeschool.tricount.dto.ContactDto;
import com.wildcodeschool.tricount.dto.ContactForUpdateExpenseDto;
import com.wildcodeschool.tricount.dto.CreateExpenseDTO;
import com.wildcodeschool.tricount.dto.ReadExpenseDTO;
import com.wildcodeschool.tricount.dto.UpdateExpenseDTO;
import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseMapper expenseMapper;

    @Autowired
    private ContactService contactService;

    public List<Expense> getAll() {
        return expenseRepository.findAll();
    }
    
    public Expense getById(int id) {
        Expense expense = expenseRepository.getById(id);
        return expense;
    }

    public Expense create(CreateExpenseDTO expenseDTO) {
        Expense expense = expenseMapper.mapCreateExpenseDTOToExpense(expenseDTO);
        return expenseRepository.save(expense);
    }

    public Expense update(UpdateExpenseDTO dto) {
        Expense expense = expenseRepository.getById(dto.getId());
        expense.setAmount(dto.getAmount());
        expense.setName(dto.getName());
        expense.setExpenseDate(dto.getDate());
        expense.setOwner(contactService.findById(dto.getOwnerId()));
        List<Contact> beneficiaries = new ArrayList<>();
        for(Integer contact : dto.getBeneficiariesIds()){
            beneficiaries.add(contactService.findById(contact));
        }
        expense.setBeneficiaries(beneficiaries);
        return expenseRepository.save(expense);
    }
    
    public void delete(int id) {
        System.out.println("Deleting Expense Id: "+id);
        expenseRepository.deleteById(id);
    }

}