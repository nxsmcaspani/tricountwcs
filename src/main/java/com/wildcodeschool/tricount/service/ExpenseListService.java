package com.wildcodeschool.tricount.service;

import com.wildcodeschool.tricount.entity.ExpenseList;
import com.wildcodeschool.tricount.repository.ExpenseListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseListService {
    @Autowired
    private ExpenseListRepository expenseListRepository;

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
}
