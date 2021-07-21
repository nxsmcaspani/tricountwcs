package com.wildcodeschool.tricount.controller;

import java.util.ArrayList;
import java.util.List;

import com.wildcodeschool.tricount.mappers.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.wildcodeschool.tricount.service.ContactService;
import com.wildcodeschool.tricount.dto.ContactDto;

@Controller
public class ContactController {
    
    @Autowired
    ContactService contactService;

    @Autowired
    ContactMapper contactMapper;
    
    @GetMapping("/contacts")
    public String getAll(Model model) {
        List<ContactDto> contactsDto = new ArrayList<ContactDto>();
        contactsDto = contactMapper.getAllAsDtos();
        model.addAttribute("contacts", contactsDto);
        model.addAttribute("contact", new ContactDto());
        model.addAttribute("newcontact", new ContactDto());
        return "contacts";
    }

    @PostMapping("/contact")
    public String postContact(@ModelAttribute ContactDto contactDto) {
        System.out.println("start post Contact");
        contactService.save(contactDto);
        return "redirect:/contacts";
    }
    
    @PostMapping("/del_contact")
    public String deleteContact(@ModelAttribute ContactDto contactDto) {
        System.out.println("start delete contact");
        contactService.delete(contactDto);
        return "redirect:/contacts";
    }
    
}
