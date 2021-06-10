package com.wildcodeschool.tricount.controller;

import com.wildcodeschool.tricount.entity.ExpenseList;
import com.wildcodeschool.tricount.service.ExpenseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ExpenseListController {
    @Autowired
    private ExpenseListService expenseListService;

    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("expenseslists", expenseListService.findAll());
        return "index";
    }

    @GetMapping("/expenseslist")
    public String getExpensesList(@RequestParam Integer idList){
        expenseListService.getExpenseList(idList);
        return "expenseslist";
    }

    @GetMapping("/editlist")
    public String editExpensesList(Model model, @RequestParam(required = false) Integer idList){
//        Optional<ExpenseList> expenseList = expenseListService.findById(idList);
        model.addAttribute("expenselist", expenseListService);
        return "editexpenselist";
    }

    @PostMapping("/expenseslist")
    public String postExpensesList(@ModelAttribute ExpenseList expenseList) {
        expenseListService.save(expenseList);
        return "redirect:/";
    }

    @PutMapping("/expenseslist")
    public String updateExpensesList(@ModelAttribute ExpenseList expenseList){
        expenseListService.save(expenseList);
        return "redirect:/";
    }

    @DeleteMapping("/expenseslist")
    public String deleteExpensesList(Model model, @RequestParam Integer idList){
        expenseListService.delete(idList);
        return "redirect:/";
    }

}