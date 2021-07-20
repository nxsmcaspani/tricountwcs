package com.wildcodeschool.tricount.mappers;
import com.wildcodeschool.tricount.dto.*;
import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.repository.ExpenseRepository;
import com.wildcodeschool.tricount.service.ContactService;
import com.wildcodeschool.tricount.service.ExpenseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ExpenseMapper {
    @Autowired
    ExpenseListMapper expenseListMapper;

    @Autowired
    ExpenseListService expenseListService;

    @Autowired
    ContactService contactService;

    @Autowired
    ExpenseRepository expenseRepository;

    // Method called when accessing the expense creation form
    public CreateExpenseDTO mapGetCreateExpenseToDTO(Integer idList){
        CreateExpenseDTO createExpenseDTO = new CreateExpenseDTO();
        createExpenseDTO.setExpenseListId(idList);
        createExpenseDTO.setReadExpenseListDto(expenseListMapper.fromEntityIdToDtoForRead(idList));
        return createExpenseDTO;
    }

    public static ReadExpenseDTO mapExpenseToReadExpenseDTO(Expense expense) {
        if (expense == null) {
            return null;
        }
        ReadExpenseDTO dto = new ReadExpenseDTO(
                expense.getId(),
                expense.getName(),
                expense.getOwner(),
                expense.getExpenseDate(),
                expense.getAmount());
        return dto;
    }

    public Expense mapCreateExpenseDTOToExpense(CreateExpenseDTO expenseDTO) {
        Expense exp = new Expense();
        exp.setAmount(expenseDTO.getAmount());
        exp.setExpenseDate(expenseDTO.getExpenseDate());
        exp.setName(expenseDTO.getName());
        exp.setOwner(expenseDTO.getOwner());
        exp.setExpenseList(expenseListService.getExpenseList(expenseDTO.getExpenseListId()));

        ArrayList<Contact> beneficiaries = new ArrayList<Contact>();
        for (Integer id : expenseDTO.getIdBeneficiaries()) {
            beneficiaries.add(contactService.findById(id));
        }
        exp.setBeneficiaries(beneficiaries);
        return exp;
    }

    public UpdateExpenseDTO mapGetUpdateExpenseDTO(Integer idExpense) {
        Expense expense = expenseRepository.getById(idExpense);
        UpdateExpenseDTO dto = new UpdateExpenseDTO(
                expense.getId(),
                expense.getName(),
                expense.getOwnerId(),
                expense.getAmount(),
                expense.getExpenseDate());
        dto.setExpenseListId(expense.getExpenseList().getId());
        dto.setBeneficiariesIds(expense.getBeneficiariesIds());
        return dto;
    }
}
