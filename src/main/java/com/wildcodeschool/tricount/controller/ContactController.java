package com.wildcodeschool.tricount.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
        List<Contact> contacts = new ArrayList<Contact>();
        contacts = contactService.getAll();
        model.addAttribute("contacts", contacts);
        return "contacts";
    }

    @GetMapping("/contact")
    public String getContactById(Model model, @RequestParam int idContact) {
        contactService.findById(idContact, model);
        return "contact";
    }

    @PostMapping("/contact")
    public String postContact(@RequestParam (required = false) Integer id, @RequestParam String name, @RequestParam String email) {
        contactService.save(id, name, email);
        return "redirect:/contacts";
    }
    
    @DeleteMapping("/contact")
    public String deleteContact(@RequestParam int idContact) {
        contactService.deleteById(idContact);
        return "redirect:/contacts";
    }
    
}
