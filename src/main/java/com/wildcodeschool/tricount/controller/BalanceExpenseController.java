package com.wildcodeschool.tricount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import com.wildcodeschool.tricount.dto.BalanceExpenseListDto;
import com.wildcodeschool.tricount.service.BalanceExpenseListService;

@Controller
public class BalanceExpenseController {

    @Autowired
    BalanceExpenseListService balanceExpenseListService;
    
    
    @GetMapping("/balanceexpenses/{idList}")
    public String balanceExpenses(Model model, @PathVariable int idList) {
        BalanceExpenseListDto balExpenseDto = balanceExpenseListService.getDtoBalanceExpense(idList);
        if (balExpenseDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense List ID not found "+idList);
        }
        model.addAttribute("balExpenseDto", balExpenseDto);
        return "balanceexpenses";
    }
    
    @PostMapping("/balanceexpenses/{id}")
    public String executeBalanceExpenses(@PathVariable int id) {
        System.out.println("executeBalanceExpenses idList sur " + id);
        BalanceExpenseListDto balExpenseDto = balanceExpenseListService.getDtoBalanceExpense(id);
        if (balExpenseDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense List ID not found "+id);
        }
        balanceExpenseListService.executeBalance(balExpenseDto);
        System.out.println("post mapping balanceexpenses, redirect to : redirect:/balanceexpenses/" + balExpenseDto.getIdOfExpenseList());
        return "redirect:/balanceexpenses/" + balExpenseDto.getIdOfExpenseList() + "?balanceOk=true";
    }
    
    
}
