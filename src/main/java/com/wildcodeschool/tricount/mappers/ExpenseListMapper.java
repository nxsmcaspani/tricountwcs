package com.wildcodeschool.tricount.mappers;

import com.wildcodeschool.tricount.dto.*;
import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.entity.ExpenseList;
import com.wildcodeschool.tricount.repository.ExpenseListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ExpenseListMapper {
    private final int NB_DISPLAYED_EXPENSES = 3;

    @Autowired
    ExpenseListRepository expenseListRepository;

    @Autowired
    ContactMapper contactMapper;

    @Autowired
    ExpenseMapper expenseMapper;

    public ListExpenseListDto convertFromEntityToDto(Integer idList, Boolean getLastThreeExpensesOnly){
        Optional<ExpenseList> optionalExpensesList = expenseListRepository.findById(idList);
        if (optionalExpensesList.isPresent()) {
            ExpenseList expenseList = optionalExpensesList.get();
            ListExpenseListDto dto = new ListExpenseListDto();
            dto.setId(expenseList.getId());
            dto.setName(expenseList.getName());
            if(!getLastThreeExpensesOnly) {
                dto.setExpenses(expenseList.getExpensesList());
            } else {
                dto.setExpenses(expenseList.getExpensesList().stream()
                        .sorted(Comparator.comparing(Expense::getExpenseDate).reversed())
                        .limit(NB_DISPLAYED_EXPENSES)
                        .collect(Collectors.toList()));
            }
            return dto;
        } else return null;
    }

    public UpdateExpenseListDto fromEntityToDtoForUpdate(Integer idList){
        Optional<ExpenseList> optionalExpensesList = expenseListRepository.findById(idList);
        if (optionalExpensesList.isPresent()) {
            ExpenseList expenseList = optionalExpensesList.get();
            UpdateExpenseListDto dto = new UpdateExpenseListDto();
            List<ReadExpenseDTO> readExpenseDTO = new ArrayList<>();
            dto.setId(expenseList.getId());
            dto.setName(expenseList.getName());
            for(Contact contact : expenseList.getContacts()) {
                dto.getIdContacts().add(contact.getId());
            }
            for(Expense expense : expenseList.getExpensesList()){
                readExpenseDTO.add(expenseMapper.mapExpenseToReadExpenseDTO(expense));
            }
            dto.setReadExpenseDTOS(readExpenseDTO.stream()
                    .sorted(Comparator.comparing(ReadExpenseDTO::getExpenseDate).reversed())
                    .collect(Collectors.toList()));
            return dto;
        } else return null;
    }

    public ReadExpenseListDto fromEntityIdToDtoForRead(Integer idList){
        Optional<ExpenseList> optionalExpensesList = expenseListRepository.findById(idList);
        if (optionalExpensesList.isPresent()) {
            ExpenseList expenseList = optionalExpensesList.get();
            ReadExpenseListDto readExpenseListDto = new ReadExpenseListDto();
            List<ContactDto> contactDtoList = new ArrayList<>();
            readExpenseListDto.setId(expenseList.getId());
            readExpenseListDto.setName(expenseList.getName());
            for(Contact contact : expenseList.getContacts()) {
                contactDtoList.add(contactMapper.convContactToDto(contact));
            }
            readExpenseListDto.setContactDtoList(contactDtoList);
            return readExpenseListDto;
        } else return null;
    }

    public ArrayList<ContactDto> getAllContactsAsDto(Integer expenseListId){
        ExpenseList expenseList = expenseListRepository.getById(expenseListId);
        ArrayList<ContactDto> contactsDto = new ArrayList<>();
        for (Contact contact : expenseList.getContacts()){
            contactsDto.add(new ContactDto(contact.getId(), contact.getName(), contact.getEmail()));
        }
        return contactsDto;
    }
}
