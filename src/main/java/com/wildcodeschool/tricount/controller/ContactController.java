package com.wildcodeschool.tricount.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wildcodeschool.tricount.service.ContactService;
import com.wildcodeschool.tricount.dto.ContactDto;

@Controller
public class ContactController {
    
    @Autowired
    ContactService contactService; 
    
    @GetMapping("/contacts")
    public String getAll(Model model) {
        List<ContactDto> contactsDto = new ArrayList<ContactDto>();
        contactsDto = contactService.getAll();
        model.addAttribute("contacts", contactsDto);
        model.addAttribute("contact", new ContactDto());
        model.addAttribute("newcontact", new ContactDto());
        return "contacts";
    }

    @GetMapping("/contact")
    public String getContactById(Model model, @RequestParam int idContact) {
        contactService.findById(idContact, model);
        return "contact";
    }

    @PostMapping("/contact")
    public String postContact(@ModelAttribute ContactDto contactDto) {
        contactService.save(contactDto);
        return "redirect:/contacts";
    }
    
    @DeleteMapping("/contact")
    public String deleteContact(@ModelAttribute ContactDto contactDto) {
        System.out.println("start delete contact");
        //contactService.delete(contactDto);
        return "redirect:/contacts";
    }
    
}
