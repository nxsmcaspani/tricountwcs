package com.wildcodeschool.tricount.service;
import com.wildcodeschool.tricount.dto.ContactDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.wildcodeschool.tricount.dto.ReadExpenseListDto;
import com.wildcodeschool.tricount.dto.UpdateExpenseListDto;
import com.wildcodeschool.tricount.mappers.ContactMapper;
import com.wildcodeschool.tricount.mappers.ExpenseListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.repository.ContactRepository;

@Service
public class ContactService {
    
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private ExpenseListService expenseListService;

    @Autowired
    private ExpenseListMapper expenseListMapper;

    public Contact findById(int idContact) {
        Optional<Contact> optionalContact = contactRepository.findById(idContact);
        if (optionalContact.isPresent()) {
            return optionalContact.get();
        }
        return null;
    }

    public void save(ContactDto contactDto) {
        contactRepository.save(contactMapper.convDtoToContact(contactDto));
    }

    public void delete(ContactDto dtoContact) {
        System.out.println("delete dtoContact " + dtoContact.getId());
        Optional<Contact> optionalContact = contactRepository.findById(dtoContact.getId());
        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();
            for(Integer idList : expenseListService.findExpensesListsIdsByContact(contact.getId())){
                UpdateExpenseListDto updateExpenseListDto = expenseListMapper.fromEntityToDtoForUpdate(idList);
                List<Integer> newContactIdsList = new ArrayList<>();
                for(Integer idContact : updateExpenseListDto.getIdContacts()){
                    if(idContact != contact.getId()) newContactIdsList.add(idContact);
                }
                updateExpenseListDto.setIdContacts(newContactIdsList);
                expenseListService.update(updateExpenseListDto);
            }
            contactRepository.delete(contactMapper.convDtoToContact(dtoContact));
        } else throw new RuntimeException("Failed to delete contact");
    }
}
