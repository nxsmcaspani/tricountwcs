package com.wildcodeschool.tricount.service;

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

    public void getAll(Model aModel) {
        // TODO Auto-generated method stub
        
    }

    public Model findById(int aIdContact, Model model) {
        // TODO Auto-generated method stub
        Optional<Contact> optionalContact = contactRepository.findById(aIdContact);
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
