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

    public List<ContactDto> getAll() {
        List<ContactDto> lstContactsDto = new ArrayList<ContactDto>();
        List<Contact> lstContact = contactRepository.findAll();
        for (Contact contact : lstContact) {
            lstContactsDto.add(convContactToDto(contact));
        }
        return lstContactsDto;
    }

    public Model findById(int idContact, Model model) {
        Optional<Contact> optionalContact = contactRepository.findById(idContact);
        ContactDto contactDto = new ContactDto();
        if (optionalContact.isPresent()) {
            contactDto = convContactToDto(optionalContact.get());
        }
        model.addAttribute("contact", contactDto);
        return model;
    }

    public void save(ContactDto contactDto) {
        contactRepository.save(convDtoToContact(contactDto));
    }

    public void delete(ContactDto dtoContact) {
        System.out.println("delete dtoContact " + dtoContact.getId());
        Optional<Contact> contact = contactRepository.findById(dtoContact.getId());
        if (contact.isPresent()) {
            contactRepository.delete(convDtoToContact(dtoContact));
        } else {
            System.out.println("Echec delete dtoContact ");
        }
    }
    
    private ContactDto convContactToDto(Contact contact) {
        return new ContactDto(contact.getId(), contact.getName(), contact.getEmail());
    }
    
    private Contact convDtoToContact(ContactDto contactDto) {
        Contact contact;
        if (contactDto.getId() != null) {
            System.out.println("contactDto Id not null " + contactDto.getId());
            contact = new Contact(contactDto.getId(), contactDto.getName(), contactDto.getEmail());
        } else {
            System.out.println("contactDto Id not null " + contactDto.getId());
            contact = new Contact(contactDto.getName(), contactDto.getEmail());
        }
        return contact;
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
