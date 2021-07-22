package com.wildcodeschool.tricount.service;
import com.wildcodeschool.tricount.dto.ContactDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.wildcodeschool.tricount.mappers.ContactMapper;
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
        Optional<Contact> contact = contactRepository.findById(dtoContact.getId());
        if (contact.isPresent()) {
            contactRepository.delete(contactMapper.convDtoToContact(dtoContact));
        } else {
            System.out.println("Echec delete dtoContact ");
        }
    }
}
