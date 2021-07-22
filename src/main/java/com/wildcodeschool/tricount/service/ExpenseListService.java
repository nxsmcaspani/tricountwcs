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

    private ExpenseList updateExpenseList(UpdateExpenseListDto expenseListDto) {
        ExpenseList expenseList;
        Optional<ExpenseList> optionalExpenseList = expenseListRepository.findById(expenseListDto.getId());
        if (optionalExpenseList.isPresent()) {
            expenseList = optionalExpenseList.get();
            List<Integer> newParticipantsIds = expenseListDto.getIdContacts();
            List<Integer> oldParticipantsIds = expenseList.getContactsIds();
            if(hasParticipantListBeenChanged(oldParticipantsIds, newParticipantsIds)){
                oldParticipantsIds.removeAll(newParticipantsIds);
                if(oldParticipantsIds.size() > 0) {
                    removeParticipantsDeletingExpensesAndBeneficiary(oldParticipantsIds, expenseList);
                }
                updateParticipantsFromContactIdsList(newParticipantsIds, expenseList);
            }
            expenseList.setDate(LocalDate.now());
            expenseList.setName(expenseListDto.getName());
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
            System.out.println("Contact trouvé : " + contact.getId() + " - " + contact.getName());
            contactList.add(contact);
        }
        expenseList.setContacts(contactList);
        return expenseList;
    }

    private Boolean hasParticipantListBeenChanged(List<Integer> oldParticipantsIds, List<Integer> newParticipantsIds){
        Collections.sort(newParticipantsIds);
        Collections.sort(oldParticipantsIds);
        if(!newParticipantsIds.equals(oldParticipantsIds)){
            return Boolean.TRUE;
        } else return Boolean.FALSE;
    }

    private void removeParticipantsDeletingExpensesAndBeneficiary(List<Integer> participantsId, ExpenseList expenseList){
        for (Integer idContact : participantsId) {
            Contact beneficiaryToRemove = contactService.findById(idContact);
            List<Expense> expensesList = expenseList.getExpensesList();
            for(Expense expense : expensesList) {
                List<Contact> expenseBeneficiaries= expense.getBeneficiaries();
                if(expense.getOwner().getId() == idContact){
                    expenseList.setExpensesList(expenseList.getExpensesList().stream().filter(e -> e.getId() != expense.getId()).collect(Collectors.toList()));
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

    private void updateParticipantsFromContactIdsList(List<Integer> newParticipantsIds, ExpenseList expenseList){
        List<Contact> newParticipants = new ArrayList<>();
        for(Integer id : newParticipantsIds){
            Optional<Contact> OptionalContact = contactRepository.findById(id);
            OptionalContact.ifPresent(newParticipants::add);
        }
        expenseList.setContacts(newParticipants);
    }
}
