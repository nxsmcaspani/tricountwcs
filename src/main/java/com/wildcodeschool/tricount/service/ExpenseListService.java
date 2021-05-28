package com.wildcodeschool.tricount.service;

import com.wildcodeschool.tricount.entity.ExpenseList;
import com.wildcodeschool.tricount.repository.ExpenseListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseListService {
    @Autowired
    private ExpenseListRepository expenseListRepository;

    @Override
    public ExpenseList save(ExpenseList expenseList){
        return expenseListRepository.save(expenseList);
    }
}
