package com.wildcodeschool.tricount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wildcodeschool.tricount.dto.CreateOrUpdateExpenseListDto;
import com.wildcodeschool.tricount.service.BalanceExpenseListService;

@Controller
public class BalanceExpenseController {

    @Autowired
    BalanceExpenseListService balanceExpenseListService;
    
    
    @GetMapping("balanceexpenses/{idList}")
    public String balanceExpenses(Model model, @PathVariable int idList) {
        CreateOrUpdateExpenseListDto createOrUpdateExpenseListDto = balanceExpenseListService.getDtoExpenseList(idList);
        model.addAttribute("expenselistdto", createOrUpdateExpenseListDto);
        return "balanceexpenses";
    }
    
    
    
}
