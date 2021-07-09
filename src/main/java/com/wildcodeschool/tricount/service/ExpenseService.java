package com.wildcodeschool.tricount.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildcodeschool.tricount.dto.ContactDto;
import com.wildcodeschool.tricount.dto.ContactForUpdateExpenseDto;
import com.wildcodeschool.tricount.dto.CreateExpenseDTO;
import com.wildcodeschool.tricount.dto.ReadExpenseDTO;
import com.wildcodeschool.tricount.dto.UpdateExpenseDTO;
import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repo;

    @Autowired
    private ExpenseListService expenseListService;

    @Autowired
    private ContactService contactService;

    public List<Expense> getAll() {
        return repo.findAll();
    }
    
    public Expense getById(int id) {
        Expense expense = repo.getById(id);
        return expense;
    }

    public ReadExpenseDTO getReadExpenseDTOById(int id) {
        Expense expense = this.getById(id);
        return mapExpenseToReadExpenseDTO(expense);
    }

    public Expense create(CreateExpenseDTO expenseDTO) {
        Expense expense = mapCreateExpenseDTOToExpense(expenseDTO);
        return repo.save(expense);
    }

    public Expense update(UpdateExpenseDTO dto) {
        Expense expense = repo.getById(dto.getId());
        expense.setAmount(dto.getAmount());
        expense.setName(dto.getName());
        Contact owner = contactService.findById(dto.getOwnerId());
        expense.setOwner(owner);
//        List<Contact> benef = expense.getBeneficiaries();
//        expense = mapUpdatExpenseDTOToExpense(dto, expense);
        return repo.save(expense);
    }
    
    public void delete(int id) {
        repo.deleteById(id);
    }

    // Method called when accessing the expense creation form
    public CreateExpenseDTO mapGetCreateExpenseToDTO(Integer idList){
        CreateExpenseDTO createExpenseDTO = new CreateExpenseDTO();
        createExpenseDTO.setExpenseListId(idList);
        createExpenseDTO.setReadExpenseListDto(expenseListService.fromEntityIdToDtoForRead(idList));
        return createExpenseDTO;
    }

    public static ReadExpenseDTO mapExpenseToReadExpenseDTO(Expense expense) {
        if (expense == null) {
            return null;
        }
        ReadExpenseDTO dto = new ReadExpenseDTO(
                expense.getId(),
                expense.getName(),
                expense.getOwner(),
                expense.getExpenseDate(),
                expense.getAmount());
        return dto;
    }

    private Expense mapCreateExpenseDTOToExpense(CreateExpenseDTO expenseDTO) {
        Expense exp = new Expense();
        exp.setAmount(expenseDTO.getAmount());
        exp.setExpenseDate(expenseDTO.getExpenseDate());
        exp.setName(expenseDTO.getName());
        exp.setOwner(expenseDTO.getOwner());
        exp.setExpenseList(expenseListService.getExpenseList(expenseDTO.getExpenseListId()));
        
        ArrayList<Contact> beneficiaries = new ArrayList<Contact>();
        for (Integer id : expenseDTO.getIdBeneficiaries()) {
            beneficiaries.add(contactService.findById(id));
        }
        exp.setBeneficiaries(beneficiaries);
        return exp;
    }

    private static Expense mapUpdatExpenseDTOToExpense(UpdateExpenseDTO dto, Expense current) {
        current.setName(dto.getName());
        current.setAmount(dto.getAmount());
        current.setOwner(mapToContact(dto.getOwner()));
        current.setBeneficiaries(mapToListOfContact(dto.getBeneficiaries()));
        return current;
    }

    public static UpdateExpenseDTO mapGetUpdateExpenseDTO(Expense expense) {
        UpdateExpenseDTO dto = new UpdateExpenseDTO(
                expense.getId(),
                expense.getName(),
                mapToContactDto(expense.getOwner()),
                expense.getAmount(),
                expense.getExpenseDate());
        
        List<Contact> beneficiaries = expense.getBeneficiaries();
        List<ContactDto> ownerListOfContactDto = mapToListOfContactDto(beneficiaries);
        List<ContactForUpdateExpenseDto> ownerList = mapToContactForUpdateExpenseDto(ownerListOfContactDto);
        selectContact(ownerList, mapToContactDto(expense.getOwner()));
        dto.setOwnerList(ownerList);
        
        List<ContactDto> listOfBeneficiaries = mapToListOfContactDto(expense.getBeneficiaries());
        dto.setBeneficiaries(listOfBeneficiaries);
        
        Integer expenseListId = expense.getExpenseList().getId();
        dto.setExpenseListId(expenseListId);
        return dto;
    }
    


    public static Contact mapToContact(ContactDto dto) {
        Contact contact = new Contact();
        contact.setId(dto.getId());
        contact.setEmail(dto.getEmail());
        contact.setName(dto.getName());
        return contact;
    }
    
    public static ContactDto mapToContactDto(Contact contact) {
        ContactDto dto =  new ContactDto();
        dto.setId(contact.getId());
        dto.setName(contact.getName());
        dto.setEmail(contact.getEmail());
        return dto;
    }
    
    public static List<ContactDto> mapToListOfContactDto(List<Contact> contacts) {
        List<ContactDto> dtos = new ArrayList<ContactDto>();
        for (Iterator<Contact> contact = contacts.iterator(); contact.hasNext();) {
            ContactDto contactDto = mapToContactDto(contact.next());
            dtos.add(contactDto);
        }
        return dtos;
    }

    public static List<Contact> mapToListOfContact(List<ContactDto> dto) {
        List<Contact> contacts = new ArrayList<>();
        for (Iterator<ContactDto> contactDto = dto.iterator(); contactDto.hasNext();) {
            Contact contact = mapToContact(contactDto.next());
            contacts.add(contact);
        }
        return contacts;
    }
    
    public static List<ContactForUpdateExpenseDto> mapToContactForUpdateExpenseDto(List<ContactDto> contactsDto) {
        List<ContactForUpdateExpenseDto> list = new ArrayList<>();
        for (Iterator<ContactDto> iterator = contactsDto.iterator(); iterator.hasNext();) {
            ContactDto contactDto = iterator.next();
            ContactForUpdateExpenseDto c = new ContactForUpdateExpenseDto(
                    contactDto.getId(),
                    contactDto.getName(), 
                    contactDto.getEmail());
            c.setSelected(false);
            list.add(c);
        }
        return list;
    }
    
    private static void selectContact(List<ContactForUpdateExpenseDto> ownerList, ContactDto contact) {
        // TODO Auto-generated method stub
        for (Iterator<ContactForUpdateExpenseDto> iterator = ownerList.iterator(); iterator.hasNext();) {
            ContactForUpdateExpenseDto contactForUpdateExpenseDto = iterator.next();
            if (contactForUpdateExpenseDto.getId() == contact.getId()) {
                contactForUpdateExpenseDto.setSelected(true);
            }
        }
    }
}
