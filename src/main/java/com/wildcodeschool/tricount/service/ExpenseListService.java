package com.wildcodeschool.tricount.service;

import com.wildcodeschool.tricount.dto.CreateOrUpdateExpenseListDto;
import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.entity.ExpenseList;
import com.wildcodeschool.tricount.repository.ContactRepository;
import com.wildcodeschool.tricount.repository.ExpenseListRepository;
import com.wildcodeschool.tricount.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseListService {
    @Autowired
    private ExpenseListRepository expenseListRepository;
    @Autowired
    private ContactRepository contactRepository;

    public ExpenseList save(CreateOrUpdateExpenseListDto expenseListDto){
        ExpenseList expenseList;
        if(expenseListDto.getId() != null) {
            expenseList = updateExpenseList(expenseListDto);
        } else {
            expenseList = createExpenseList(expenseListDto);
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
            for(Contact contact : expenseList.getContacts()) {
                dto.getIdContacts().add(contact.getId());
            }
            return dto;
        } else return null;
    }

    private ExpenseList updateExpenseList(CreateOrUpdateExpenseListDto expenseListDto) {
        ExpenseList expenseList;
        System.out.println(expenseListDto.getId());
        Optional<ExpenseList> optionalExpenseList = expenseListRepository.findById(expenseListDto.getId());
        if (optionalExpenseList.isPresent()) {
            expenseList = optionalExpenseList.get();
            expenseList.setDate(new Date());
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

    private ExpenseList createExpenseList(CreateOrUpdateExpenseListDto expenseListDto) {
        ExpenseList expenseList;
        expenseList = new ExpenseList();
        expenseList.setName(expenseListDto.getName());
        expenseList.setDate(new Date());
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
