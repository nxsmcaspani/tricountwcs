package com.wildcodeschool.tricount.service;

import com.wildcodeschool.tricount.dto.CreateExpenseDTO;
import com.wildcodeschool.tricount.dto.ReadExpenseDTO;
import com.wildcodeschool.tricount.dto.UpdateExpenseDTO;
import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repo;

    @Autowired
    private ExpenseListService expenseListService;

    @Autowired
    private ContactService contactService;

    public List<Expense> getAll() {
        return repo.findAll();
    }

    public ReadExpenseDTO getById(int id) {
        Expense expense = repo.getById(id);
        return mapExpenseToReadExpenseDTO(expense);
    }

    public Expense create(CreateExpenseDTO expenseDTO) {
        Expense expense = mapCreateExpenseDTOToExpense(expenseDTO);
        return repo.save(expense);
    }

    public Expense update(UpdateExpenseDTO dto) {
        Expense expense = repo.getById(dto.getId());
        expense = mapUpdatExpenseDTOToExpense(dto, expense);
        return repo.save(expense);
    }
    
    public void delete(int id) {
        Optional<Expense> expense = repo.findById(id);
        if (expense.isPresent()) {
            repo.deleteById(id);
        }
    }

    // Method called when accessing the expense creation form
    public CreateExpenseDTO mapGetCreateExpenseToDTO(Integer idList){
        CreateExpenseDTO createExpenseDTO = new CreateExpenseDTO();
        createExpenseDTO.setExpenseListId(idList);
        createExpenseDTO.setReadExpenseListDto(expenseListService.fromEntityIdToDtoForRead(idList));
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

    private static Expense mapUpdatExpenseDTOToExpense(UpdateExpenseDTO dto, Expense current) {
        current.setName(dto.getName());
        current.setAmount(dto.getAmount());
        current.setOwner(dto.getOwner());
        current.setBeneficiaries(dto.getBeneficiaries());
        return current;
    }
}
