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
        return "expenseslists";
    }

    @PostMapping("/expenseslist")
    public String postExpensesList(@ModelAttribute ExpenseList expenseList) {
        expenseListService.save(expenseList);
        return "redirect:/";
    }

    @PutMapping("/expenseslist")
    public String updateExpensesList(Model model, @RequestParam Integer idList){
        // do stuff
        Optional<ExpenseList> optionalExpenseList = expenseListService.findById(idList);
//        ExpenseList expenseList = new ExpenseList();
        if (optionalExpenseList.isPresent()) {
//            expenseList = optionalExpenseList.get();
            expenseListService.delete(optionalExpenseList.get());
        }
        return "redirect:/";
    }

    @DeleteMapping("/expenseslist")
    public String deleteExpensesList(Model model, @RequestParam Integer idList){
        //do stuff
        return "redirect:/";
    }

}