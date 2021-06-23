package com.wildcodeschool.tricount.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wildcodeschool.tricount.dto.BalanceExpenseDto;
import com.wildcodeschool.tricount.dto.ContactForBalanceDto;
import com.wildcodeschool.tricount.entity.Contact;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.entity.ExpenseList;

@Service
public class BalanceExpenseListService {

    @Autowired
    private ExpenseListService expenseListService;
    
    
    public BalanceExpenseDto getDtoBalanceExpense(int idList) {
        Optional<ExpenseList> exp = expenseListService.findById(idList);
        if (exp.isPresent()) {
            BalanceExpenseDto balDto = convExpenseListToBalanceDto(exp.get());
            return balDto; // expenseListService.convertFromEntityToDto(idList);
        }
        return null;
    }
    
    
    /**
     * Conversion d'une ExpenseList en BalanceExpenseDto pour page Balance
     * @param ExpenseList
     * @return BalanceExpenseDto
     */
    private BalanceExpenseDto convExpenseListToBalanceDto(ExpenseList exp) {
        BalanceExpenseDto balDto = new BalanceExpenseDto();
        balDto.setName(exp.getName());
        List<ContactForBalanceDto> lstCt = new ArrayList<ContactForBalanceDto>();
        for (Contact ct : exp.getContacts()) {
            lstCt.add(new ContactForBalanceDto(ct.getId(), ct.getName(), ct.getEmail()));
        }
        balDto.setLstContacts(lstCt);
        
        for (Expense dep : exp.getExpensesList()) {
            balDto.setTotal(balDto.getTotal() + dep.getAmount());
            for (ContactForBalanceDto ctDto : balDto.getLstContacts()) {
                if (ctDto.getId()== dep.getOwner().getId()) {
                    ctDto.setAmountSpend(ctDto.getAmountSpend() + dep.getAmount());
                    // TODO: si le owner pas présent dans les bénéficiaires, retirer le commentaire et faire +1 aussi aux bénefs
                    // ctDto.setAmountDue(dep.getAmount() / dep.getBeneficiaries().size() + 1);
                }
                for (Contact ct : dep.getBeneficiaries()) {
                    if (ctDto.getId() == ct.getId()) {
                        ctDto.setAmountDue(ctDto.getAmountDue() + (dep.getAmount() / dep.getBeneficiaries().size()));
                    }
                }
            }
        }
        balDto.setBalanceOk(checkBalanceOk(balDto.getLstContacts()));
        System.out.println("Balance ok ? : " + balDto.isBalanceOk());
        return balDto;
    }

    private boolean checkBalanceOk(List<ContactForBalanceDto> lstContacts) {
        if (lstContacts.size() <2) {
            System.out.println("inf 2, true");
            return true;
        }
        for (int i = 1; i < lstContacts.size(); i++) {
            if (lstContacts.get(i).getAmountDue() != lstContacts.get(i-1).getAmountDue()) {
                System.out.println("val diff : " + lstContacts.get(i).getAmountDue() + " versus " + lstContacts.get(i-1).getAmountDue()); 
                return false;
            }
        }
        return true;
    }
    
    
    
}
