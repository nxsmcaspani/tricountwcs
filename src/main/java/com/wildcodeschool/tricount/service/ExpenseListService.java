package com.wildcodeschool.tricount.service;

import com.wildcodeschool.tricount.dto.ListExpenseListDto;
import com.wildcodeschool.tricount.dto.UpdateExpenseListDto;
import com.wildcodeschool.tricount.dto.CreateExpenseListDto;
import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.entity.ExpenseList;
import com.wildcodeschool.tricount.repository.ContactRepository;
import com.wildcodeschool.tricount.repository.ExpenseListRepository;
import com.wildcodeschool.tricount.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpenseListService {
    private final int NB_DISPLAYED_EXPENSES = 3;

    @Autowired
    private ExpenseListRepository expenseListRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseService expenseService;

    public ExpenseList create(CreateExpenseListDto createExpenseListDto){
        ExpenseList expenseList;
        expenseList = createExpenseList(createExpenseListDto);
        return expenseListRepository.save(expenseList);
    }

    public ExpenseList update(UpdateExpenseListDto updateExpenseListDto){
        ExpenseList expenseList;
        expenseList = updateExpenseList(updateExpenseListDto);
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

    public ExpenseList convertFromDtoToEntity(ListExpenseListDto dto){
        ExpenseList expenseListFromDto = new ExpenseList();
        expenseListFromDto.setName(dto.getName());
        if(dto.getId() != null){
            expenseListFromDto.setId(dto.getId());
        }
        return expenseListFromDto;
    }

    public ListExpenseListDto convertFromEntityToDto(Integer idList, Boolean getLastThreeExpensesOnly){
        Optional<ExpenseList> optionalExpensesList = expenseListRepository.findById(idList);
        if (optionalExpensesList.isPresent()) {
            ExpenseList expenseList = optionalExpensesList.get();
            ListExpenseListDto dto = new ListExpenseListDto();
            dto.setId(expenseList.getId());
            dto.setName(expenseList.getName());
            if(!getLastThreeExpensesOnly) {
                dto.setExpenses(expenseList.getExpensesList());
            } else {
                dto.setExpenses(expenseList.getExpensesList().stream()
                        .sorted(Comparator.comparing(Expense::getExpenseDate).reversed())
                        .limit(NB_DISPLAYED_EXPENSES)
                        .collect(Collectors.toList()));
            }
            return dto;
        } else return null;
    }

    public UpdateExpenseListDto fromEntityToDtoForUpdate(Integer idList){
        Optional<ExpenseList> optionalExpensesList = expenseListRepository.findById(idList);
        if (optionalExpensesList.isPresent()) {
            ExpenseList expenseList = optionalExpensesList.get();
           UpdateExpenseListDto dto = new UpdateExpenseListDto();
            dto.setId(expenseList.getId());
            dto.setName(expenseList.getName());
            for(Contact contact : expenseList.getContacts()) {
                dto.getIdContacts().add(contact.getId());
            }
            return dto;
        } else return null;
    }

    private ExpenseList updateExpenseList(UpdateExpenseListDto expenseListDto) {
        ExpenseList expenseList;
        Optional<ExpenseList> optionalExpenseList = expenseListRepository.findById(expenseListDto.getId());
        if (optionalExpenseList.isPresent()) {
            expenseList = optionalExpenseList.get();
            expenseList.setDate(LocalDate.now());
            expenseList.setName(expenseListDto.getName());
            List<Contact> participants = new ArrayList<>();
            for(Integer id : expenseListDto.getIdContacts()){
                Optional<Contact> OptionalContact = contactRepository.findById(id);
                OptionalContact.ifPresent(participants::add);
            }
            expenseList.setContacts(participants);
        } else {
            throw new RuntimeException("Expense List Id not found.");
        }
        return expenseList;
    }

    private ExpenseList createExpenseList(CreateExpenseListDto expenseListDto) {
        ExpenseList expenseList;
        expenseList = new ExpenseList();
        expenseList.setName(expenseListDto.getName());
        expenseList.setDate(LocalDate.now());
        List<Contact> contactList = new ArrayList<>();
        for(Integer id : expenseListDto.getIdContacts()){
            Optional<Contact> optionalContact = contactRepository.findById(id);
            Contact contact = optionalContact.orElseThrow(RuntimeException::new); // Java 8 so that we get a contact else throws an exception
            contactList.add(contact);
        }
        expenseList.setContacts(contactList);
        return expenseList;
    }
}
