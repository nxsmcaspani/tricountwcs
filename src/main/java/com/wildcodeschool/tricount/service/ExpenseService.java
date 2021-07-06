package com.wildcodeschool.tricount.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildcodeschool.tricount.dto.ContactDto;
import com.wildcodeschool.tricount.dto.CreateExpenseDTO;
import com.wildcodeschool.tricount.dto.ReadExpenseDTO;
import com.wildcodeschool.tricount.dto.UpdateExpenseDTO;
import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepo;
    
    @Autowired
    private ContactService contactService;

    public List<Expense> getAll() {
        return expenseRepo.findAll();
    }

    public ReadExpenseDTO getById(int id) {
        Expense expense = expenseRepo.getById(id);
        return mapExpenseToReadExpenseDTO(expense);
    }

    public Expense create(CreateExpenseDTO expenseDTO) {
        Expense expense = mapCreateExpenseDTOToExpense(expenseDTO);
        return expenseRepo.save(expense);
    }

    public Expense update(UpdateExpenseDTO dto) {
        Expense expense = expenseRepo.getById(dto.getId());
        expense = mapUpdatExpenseDTOToExpense(dto, expense);
        return expenseRepo.save(expense);
    }
    
    public Boolean delete(int id) {
        Optional<Expense> expense = expenseRepo.findById(id);
        if (expense.isPresent()) {
            expenseRepo.deleteById(id);
            return true;
        }
        return false;
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
        exp.setExpenseDate(LocalDate.now());
        exp.setName(expenseDTO.getName());
        exp.setOwner(contactService.convDtoToContact(expenseDTO.getOwner()));
        
        List<Contact> beneficiaries = new ArrayList<Contact>();
        for (ContactDto contactDto : expenseDTO.getBeneficiaries()) {
            beneficiaries.add(contactService.convDtoToContact(contactDto));
        }
        exp.setBeneficiaries(beneficiaries);
        return exp;
    }

    private Expense mapUpdatExpenseDTOToExpense(UpdateExpenseDTO dto, Expense current) {
        current.setName(dto.getName());
        current.setAmount(dto.getAmount());
        current.setOwner(dto.getOwner());
        current.setBeneficiaries(dto.getBeneficiaries());
        return current;
    }
}
