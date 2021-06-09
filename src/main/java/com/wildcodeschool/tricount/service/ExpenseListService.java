package com.wildcodeschool.tricount.service;

import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.entity.ExpenseList;
import com.wildcodeschool.tricount.repository.ExpenseListRepository;
import com.wildcodeschool.tricount.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseListService {
    @Autowired
    private ExpenseListRepository expenseListRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public ExpenseList save(ExpenseList expenseList){
        return expenseListRepository.save(expenseList);
    }

    public void delete(Integer idList){
        Optional<ExpenseList> optionalExpenseList = expenseListRepository.findById(idList);
        if (optionalExpenseList.isPresent()) {
            expenseListRepository.delete(optionalExpenseList.get());
        }
    }

    public List<ExpenseList> findAll(){
        return expenseListRepository.findAll();
    }

    public List<Expense> getExpenseList(Integer idList){
        return expenseRepository.findAll();
    }

}
