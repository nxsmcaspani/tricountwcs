package com.wildcodeschool.tricount.mappers;

import com.wildcodeschool.tricount.dto.ContactDto;
import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContactMapper {
    @Autowired
    ContactRepository contactRepository;

    public ContactDto convContactToDto(Contact contact) {
        return new ContactDto(contact.getId(), contact.getName(), contact.getEmail());
    }

    public List<ContactDto> getAllAsDtos() {
        List<ContactDto> lstContactsDto = new ArrayList<ContactDto>();
        List<Contact> lstContact = contactRepository.findAll();
        for (Contact contact : lstContact) {
            lstContactsDto.add(convContactToDto(contact));
        }
        return lstContactsDto;
    }

    public Contact convDtoToContact(ContactDto contactDto) {
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
