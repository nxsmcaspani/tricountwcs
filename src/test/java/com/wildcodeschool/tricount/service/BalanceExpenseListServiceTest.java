package com.wildcodeschool.tricount.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.wildcodeschool.tricount.dto.BalanceExpenseListDto;
import com.wildcodeschool.tricount.dto.ContactDto;
import com.wildcodeschool.tricount.dto.CreateExpenseDTO;
import com.wildcodeschool.tricount.dto.CreateExpenseListDto;
import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.entity.ExpenseList;
import com.wildcodeschool.tricount.mappers.ContactMapper;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class BalanceExpenseListServiceTest {

    @Autowired
    private BalanceExpenseListService balanceSrv;
    
    @Autowired
    private ContactService contactSrv;
    
    @Autowired
    private ContactMapper contactMapper;
    
    @Autowired 
    private ExpenseListService expListSrv;
    
    @Autowired 
    private ExpenseService expenseSrv;

    
    @Test
    void contextLoads() {
        System.out.println("contextLoads");
        
    }

    
    @Test
    public void testBalanceOnThree() {
        
        List<ContactDto> contacts = genereContacts(3);
        ExpenseList expenseLst = createExpenseList(contacts);
        
        // première depense 50 au 1er pour tous :
        createExpense(expenseLst, 50, contacts.get(0), contacts);
        
        BalanceExpenseListDto balDto = balanceSrv.getDtoBalanceExpense(expenseLst.getId());
        
        balDto.isBalanceOk();
        
        balanceSrv.executeBalance(balDto); 
        
        balDto.isBalanceOk();
        
        
        
        
    }
    
    private Expense createExpense(ExpenseList expenseLst, float amount, ContactDto ownerDto, List<ContactDto> beneficiaries) {
        Contact owner = new Contact(ownerDto.getId(), ownerDto.getName(), ownerDto.getEmail());
        CreateExpenseDTO exp = new CreateExpenseDTO("expense test", owner, 50);
        exp.setIdBeneficiaries(convContactDtoToId(beneficiaries));
      //  return expenseSrv.create(exp);
        return null; // TODO: a voir si le temps
    }
    
    private List<ContactDto> genereContacts(int nb) {
        for (int i = 1; i <= nb; i++) {
            contactSrv.save(new ContactDto("testContact"+i, "email" + i + "@test"));
        }
        return contactMapper.getAllContactsAsDto();
    }

    private ArrayList<Integer> convContactDtoToId(List<ContactDto> contacts) {
        ArrayList<Integer> lstIdContacts = new ArrayList<Integer>();
        for (ContactDto cdto : contacts) {
            lstIdContacts.add(cdto.getId());
        }
        return lstIdContacts;
    }
    
    private ExpenseList createExpenseList(List<ContactDto> contacts) {
        CreateExpenseListDto expenseList = new CreateExpenseListDto();
        expenseList.setName("expenseTest");
        expenseList.setIdContacts(convContactDtoToId(contacts));
        return expListSrv.create(expenseList);
    }
    
}
