package com.wildcodeschool.tricount.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wildcodeschool.tricount.repository.ContactRepository;
import com.wildcodeschool.tricount.entity.Contact;

@Controller
public class ContactController {
    
    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/contacts")
    public String getAll(Model model) {
        model.addAttribute("contacts", contactRepository.findAll());
        return "contacts";
    }

    @GetMapping("/contact")
    public String getContactById(Model model, @RequestParam int idContact) {
        Optional<Contact> optionalContact = contactRepository.findById(idContact);
        Contact contact = new Contact();
        if (optionalContact.isPresent()) {
            contact = optionalContact.get();
        }
        model.addAttribute("contact", contact);
        return "contact";
    }

    @PostMapping("/contact")
    public String postContact(@RequestParam Contact contact) {
        contact = contactRepository.save(contact);
        return "redirect:/contact?id=" + contact.getId();
    }
    
}
