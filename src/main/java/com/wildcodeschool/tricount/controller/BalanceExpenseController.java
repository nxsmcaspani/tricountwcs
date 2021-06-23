package com.wildcodeschool.tricount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wildcodeschool.tricount.dto.BalanceExpenseDto;
import com.wildcodeschool.tricount.service.BalanceExpenseListService;

@Controller
public class BalanceExpenseController {

    @Autowired
    BalanceExpenseListService balanceExpenseListService;
    
    
    @GetMapping("balanceexpenses/{idList}")
    public String balanceExpenses(Model model, @PathVariable int idList) {
        BalanceExpenseDto balExpenseDto = balanceExpenseListService.getDtoBalanceExpense(idList);
        model.addAttribute("balExpenseDto", balExpenseDto);
        return "balanceexpenses";
    }
    
    
    
}
