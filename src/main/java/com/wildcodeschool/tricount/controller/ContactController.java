package com.wildcodeschool.tricount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wildcodeschool.tricount.service.ContactService;
import com.wildcodeschool.tricount.entity.Contact;

@Controller
public class ContactController {
    
    @Autowired
    ContactService contactService; 
    
    @GetMapping("/contacts")
    public String getAll(Model model) {
        contactService.getAll(model);
        return "contacts";
    }

    @GetMapping("/contact")
    public String getContactById(Model model, @RequestParam int idContact) {
        contactService.findById(idContact, model);
        return "contact";
    }

    @PostMapping("/contact")
    public String postContact(@RequestParam Contact contact) {
        contact =contactService.save(contact);
        return "redirect:/contact?id=" + contact.getId();
    }
    
}
