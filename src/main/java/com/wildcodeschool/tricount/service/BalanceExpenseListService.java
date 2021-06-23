package com.wildcodeschool.tricount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildcodeschool.tricount.dto.CreateOrUpdateExpenseListDto;
import com.wildcodeschool.tricount.repository.ContactRepository;

@Service
public class BalanceExpenseListService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ExpenseListService expenseListService;
    
    
    public CreateOrUpdateExpenseListDto getDtoExpenseList(int idList) {
        return expenseListService.convertFromEntityToDto(idList);
    }
    
    
    
    
}
