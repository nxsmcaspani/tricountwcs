package com.wildcodeschool.tricount.service;

import com.wildcodeschool.tricount.dto.*;
import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.entity.ExpenseList;
import com.wildcodeschool.tricount.repository.ContactRepository;
import com.wildcodeschool.tricount.repository.ExpenseListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpenseListService {
    private final int NB_DISPLAYED_EXPENSES = 3;

    @Autowired
    private ExpenseListRepository expenseListRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ContactService contactService;

    public ExpenseList create(CreateExpenseListDto createExpenseListDto){
        ExpenseList expenseList;
        expenseList = createExpenseList(createExpenseListDto);
        return expenseListRepository.save(expenseList);
    }

    public ExpenseList update(UpdateExpenseListDto updateExpenseListDto){
        ExpenseList expenseList;
        expenseList = updateExpenseList(updateExpenseListDto);
        return expenseListRepository.save(expenseList);
    }

    public void delete(Integer idList){
        expenseListRepository.deleteById(idList);
    }

    public Optional<ExpenseList> findById(Integer idList){ return expenseListRepository.findById(idList); }

    public List<ExpenseList> findAll(){
        return expenseListRepository.findAll();
    }

    public ExpenseList getExpenseList(Integer id) {
        Optional<ExpenseList> optionalExpensesList = expenseListRepository.findById(id);
        if (optionalExpensesList.isPresent()) {
            return optionalExpensesList.get();            
        }
        return null;
    }

    public ExpenseList convertFromDtoToEntity(ListExpenseListDto dto){
        ExpenseList expenseListFromDto = new ExpenseList();
        expenseListFromDto.setName(dto.getName());
        if(dto.getId() != null){
            expenseListFromDto.setId(dto.getId());
        }
        return expenseListFromDto;
    }

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
                readExpenseDTO.add(expenseService.mapExpenseToReadExpenseDTO(expense));
            }
            dto.setReadExpenseDTOS(readExpenseDTO);
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
                contactDtoList.add(contactService.convContactToDto(contact));
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

    private ExpenseList updateExpenseList(UpdateExpenseListDto expenseListDto) {
        ExpenseList expenseList;
        Optional<ExpenseList> optionalExpenseList = expenseListRepository.findById(expenseListDto.getId());
        if (optionalExpenseList.isPresent()) {
            expenseList = optionalExpenseList.get();
            expenseList.setDate(LocalDate.now());
            expenseList.setName(expenseListDto.getName());
            List<Integer> newParticipantsIds = expenseListDto.getIdContacts();
            List<Integer> oldParticipantsIds = expenseList.getContactsIds();
            Collections.sort(newParticipantsIds);
            Collections.sort(oldParticipantsIds);
            if(!newParticipantsIds.equals(oldParticipantsIds)){
                oldParticipantsIds.removeAll(newParticipantsIds);
                /**
                 * Case when a participant has been removed
                 * we delete expenses done by him
                 * and remove him from the beneficiaries of other expenses
                 */
                if(oldParticipantsIds.size() > 0){
                    System.out.println("#DEBUG: Some contacts were removed from the list, impacting expenses...");
                    for (Integer idContact : oldParticipantsIds) {
                        for(Expense expense : expenseList.getExpensesList()) {
                            List<Contact> expenseBeneficiaries= expense.getBeneficiaries();
                            System.out.println("#DEBUG: For expense Id ["+expense.getId()+"] owner is: ["+expense.getOwner().getName()+"] with contact Id ["+expense.getOwner().getId()+"] ");
                            Contact beneficiaryToRemove = contactService.findById(idContact);
                            if(expense.getOwner().getId() == idContact){
                                System.out.println("#DEBUG: Expense owner ID is : ["+expense.getOwner().getId()+"] same as removed Contact ID ["+idContact+"], deleting expense...");
                                expenseService.delete(expense.getId());
                            } else {
                                if (expenseBeneficiaries.contains(beneficiaryToRemove)) {
                                    expenseBeneficiaries = expenseBeneficiaries.stream().filter(c -> c.getId() != beneficiaryToRemove.getId()).collect(Collectors.toList());
                                    expense.setBeneficiaries(expenseBeneficiaries);
                                }
                            }
                        }
                    }
                }
                List<Contact> newParticipants = new ArrayList<>();
                for(Integer id : newParticipantsIds){
                    Optional<Contact> OptionalContact = contactRepository.findById(id);
                    OptionalContact.ifPresent(newParticipants::add);
                }
                expenseList.setContacts(newParticipants);
            }
        } else {
            throw new RuntimeException("Expense List Id not found.");
        }
        return expenseList;
    }

    private ExpenseList createExpenseList(CreateExpenseListDto expenseListDto) {
        ExpenseList expenseList;
        expenseList = new ExpenseList();
        expenseList.setName(expenseListDto.getName());
        expenseList.setDate(LocalDate.now());
        List<Contact> contactList = new ArrayList<>();
        for(Integer id : expenseListDto.getIdContacts()){
            Optional<Contact> optionalContact = contactRepository.findById(id);
            Contact contact = optionalContact.orElseThrow(RuntimeException::new); // Java 8 so that we get a contact else throws an exception
            contactList.add(contact);
        }
        expenseList.setContacts(contactList);
        return expenseList;
    }
}
