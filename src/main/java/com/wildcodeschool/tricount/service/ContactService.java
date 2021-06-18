package com.wildcodeschool.tricount.service;
import com.wildcodeschool.tricount.dto.ContactDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.repository.ContactRepository;

@Service
public class ContactService {
    
    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    public Model findById(int idContact, Model model) {
        Optional<Contact> optionalContact = contactRepository.findById(idContact);
        Contact contact = new Contact();
        if (optionalContact.isPresent()) {
            contact = optionalContact.get();
        }
        model.addAttribute("contact", contact);
        return model;
    }

    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }
    
    public Optional<Contact> findById(int idContact) {
        return contactRepository.findById(idContact);
    }

    public void deleteById(int aIdContact) {
        contactRepository.deleteById(aIdContact);
        
    }

    public Contact save(Integer id, String name, String email) {
        Contact contact;
        if (id == null) {
            contact = new Contact(name, email); 
        } else {
            contact = new Contact(id, name, email);
        }
        return contactRepository.save(contact);
    }

    public ArrayList<ContactDto> getAllContactsAsDto(){
        List<Contact> allContacts = contactRepository.findAll();
        ArrayList<ContactDto> contactsDto = new ArrayList<>();
        for (Contact contact : allContacts){
            contactsDto.add(new ContactDto(contact.getId(), contact.getName(), contact.getEmail()));
        }
        return contactsDto;
    }
    
}
