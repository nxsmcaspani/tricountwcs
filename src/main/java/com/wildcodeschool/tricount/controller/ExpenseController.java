package com.wildcodeschool.tricount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wildcodeschool.tricount.dto.CreateExpenseDTO;
import com.wildcodeschool.tricount.dto.ReadExpenseDTO;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.service.ExpenseService;

@Controller
public class ExpenseController {
    
    @Autowired
    ExpenseService expenseService;
    
    @GetMapping("/expense/{id}")
    @ResponseBody
    public ReadExpenseDTO getExpense(@PathVariable int id) {
        ReadExpenseDTO dto = expenseService.getById(id);
        return dto;
    }
    
    @PostMapping("/expense")
    @ResponseBody
    public ResponseEntity<Expense> postExpense(@ModelAttribute CreateExpenseDTO dto) {
        Expense created = expenseService.create(dto);
        return new ResponseEntity<Expense>(created, HttpStatus.CREATED);
    }
    
    
}
