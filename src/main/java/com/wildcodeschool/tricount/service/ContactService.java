package com.wildcodeschool.tricount.service;

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
    
    
    
    
}
