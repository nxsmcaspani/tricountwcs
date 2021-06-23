package com.wildcodeschool.tricount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildcodeschool.tricount.dto.BalanceExpenseDto;

@Service
public class BalanceExpenseListService {

    @Autowired
    private ExpenseListService expenseListService;
    
    
    public BalanceExpenseDto getDtoBalanceExpense(int idList) {
        BalanceExpenseDto balDto = new BalanceExpenseDto(expenseListService.getExpenseList(idList));
        return balDto; // expenseListService.convertFromEntityToDto(idList);
    }
    
    
    
    
    
    
}
