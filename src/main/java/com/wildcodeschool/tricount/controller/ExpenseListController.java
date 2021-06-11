package com.wildcodeschool.tricount.controller;

import com.wildcodeschool.tricount.dto.CreateOrUpdateExpenseListDto;
import com.wildcodeschool.tricount.entity.ExpenseList;
import com.wildcodeschool.tricount.service.ExpenseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    @ResponseBody
    public ExpenseList getExpensesList(@RequestParam Integer id){
        return expenseListService.getExpenseList(id);
    }

    @GetMapping("/editlist")
    public String editExpensesList(Model model, @RequestParam(required = false) Integer idList){
//        Optional<ExpenseList> expenseList = expenseListService.findById(idList);
        model.addAttribute("expenselist", new CreateOrUpdateExpenseListDto());
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

//    @DeleteMapping("/expenseslist")
    @GetMapping("/expenseslist/delete/{idList}")
//    public String deleteExpensesList(Model model, @RequestParam Integer idList){
    public String deleteExpensesList(@PathVariable Integer idList){
        Optional<ExpenseList> optionalExpenseList = expenseListService.findById(idList);
        if(optionalExpenseList.isPresent()) {
            expenseListService.delete(idList);
        } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense List ID not found "+idList);
        }
        return "redirect:/";
    }

}