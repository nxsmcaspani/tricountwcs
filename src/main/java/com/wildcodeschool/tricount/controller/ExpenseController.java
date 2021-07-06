package com.wildcodeschool.tricount.controller;

import com.wildcodeschool.tricount.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wildcodeschool.tricount.dto.CreateExpenseDTO;
import com.wildcodeschool.tricount.dto.ReadExpenseDTO;
import com.wildcodeschool.tricount.dto.UpdateExpenseDTO;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.service.ExpenseService;

@Controller
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;


    @Autowired
    ContactService contactService;
    

    @GetMapping("/expense/{id}")
    @ResponseBody
    public ReadExpenseDTO getExpense(@PathVariable int id) {
        ReadExpenseDTO dto = expenseService.getById(id);
        return dto;
    }


    @GetMapping("/expense")
    public String  getCreateExpensePage(Model model) {
        model.addAttribute("contactsdto",contactService.getAllContactsAsDto());
        return "createexpense";
    }
    

    @PostMapping("/expense")
    @ResponseBody
    public ResponseEntity<Expense> postExpense(@ModelAttribute CreateExpenseDTO dto) {
        Expense exp = expenseService.create(dto);
        return new ResponseEntity<Expense>(exp, HttpStatus.CREATED);
    }

    @PostMapping("/expense/update")
    @ResponseBody
    public ResponseEntity<Expense> postExpense(@ModelAttribute UpdateExpenseDTO dto) {
        Expense exp = expenseService.update(dto);
        return new ResponseEntity<Expense>(exp, HttpStatus.OK);
    }

    @DeleteMapping("/expense/{id}")
    @ResponseBody
    public ResponseEntity<Integer> deleteExpense(@PathVariable int id) {
        Boolean isRemoved = expenseService.delete(id);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
