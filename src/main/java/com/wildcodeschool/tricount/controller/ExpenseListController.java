package com.wildcodeschool.tricount.controller;

import com.wildcodeschool.tricount.repository.ExpenseListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
// Below for future use
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExpenseList {
    @Autowired
    private ExpenseListRepository expenseListRepository;

    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("expenseslists", expenseListRepository.findAll());
        return "expenseslists";
    }

    @PostMapping("/expenseslist")
    public String postExpensesList(@ModelAttribute ExpenseList expenseList) {
        expenseListRepository.save(expenseList);
        return "redirect:/";
    }

    @DeleteMapping("/expenseslist")
}