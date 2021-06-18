package com.wildcodeschool.tricount.service;

import com.wildcodeschool.tricount.dto.CreateOrUpdateExpenseListDto;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.entity.ExpenseList;
import com.wildcodeschool.tricount.repository.ExpenseListRepository;
import com.wildcodeschool.tricount.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseListService {
    @Autowired
    private ExpenseListRepository expenseListRepository;

    public ExpenseList save(ExpenseList expenseList){
        Optional<ExpenseList> optionalExpenseList = expenseListRepository.findById(expenseList.getId());
        if (!optionalExpenseList.isPresent()) {
            expenseList.setDate(new Date());
        }
        return expenseListRepository.save(expenseList);
    }

    public void delete(Integer idList){
        Optional<ExpenseList> optionalExpenseList = expenseListRepository.findById(idList);
        if (optionalExpenseList.isPresent()) {
            expenseListRepository.delete(optionalExpenseList.get());
        }
    }

    public Optional<ExpenseList> findById(Integer idList){ return expenseListRepository.findById(idList); }

    public List<ExpenseList> findAll(){
        return expenseListRepository.findAll();
    }

    public ExpenseList getExpenseList(Integer id) {
        Optional<ExpenseList> optionalExpensesList = expenseListRepository.findById(id);
        if (optionalExpensesList.isPresent()) {
            return optionalExpensesList.get();            
        }
        return null;
    }

//    public ExpenseList getById(Integer id) { return expenseListRepository.getById(id); }

    public ExpenseList convertFromDtoToEntity(CreateOrUpdateExpenseListDto dto){
        ExpenseList expenseListFromDto = new ExpenseList();
        expenseListFromDto.setName(dto.getName());
        if(dto.getId() != null){
            expenseListFromDto.setId(dto.getId());
        }
        return expenseListFromDto;
    }

    public CreateOrUpdateExpenseListDto convertFromEntityToDto(Integer idList){
        Optional<ExpenseList> optionalExpensesList = expenseListRepository.findById(idList);
        if (optionalExpensesList.isPresent()) {
            ExpenseList expenseList = optionalExpensesList.get();
            CreateOrUpdateExpenseListDto dto = new CreateOrUpdateExpenseListDto();
            dto.setId(expenseList.getId());
            dto.setName(expenseList.getName());
            return dto;
        } else return null;
    }
}
