package com.wildcodeschool.tricount.controller;

import com.wildcodeschool.tricount.dto.CreateExpenseDTO;
import com.wildcodeschool.tricount.dto.UpdateExpenseDTO;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.mappers.ExpenseListMapper;
import com.wildcodeschool.tricount.mappers.ExpenseMapper;
import com.wildcodeschool.tricount.service.ContactService;
import com.wildcodeschool.tricount.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpServletRequest;


@Controller
public class ExpenseController {
    @Autowired
    ExpenseService expenseService;

    @Autowired
    ContactService contactService;

    @Autowired
    ExpenseListMapper expenseListMapper;

    @Autowired
    ExpenseMapper expenseMapper;

    @GetMapping("/createexpense/{id}")
    public String getCreateExpensePage(Model model, @PathVariable(name = "id") Integer idList, HttpServletRequest request) {
        model.addAttribute("createexpensedto", expenseMapper.mapGetCreateExpenseToDTO(idList));
        String referer = request.getHeader("Referer");
        model.addAttribute("previouspage", referer);
        return "createexpense";
    }
    
    @GetMapping("/updateexpense/{id}")
    public String getUpdateExpensePage(Model model, @PathVariable(name = "id") Integer idExpense, HttpServletRequest request) {
        UpdateExpenseDTO dto = expenseMapper.mapGetUpdateExpenseDTO(idExpense);
        model.addAttribute("updateexpensedto", dto);
        model.addAttribute("contactsdto", expenseListMapper.getAllContactsAsDto(dto.getExpenseListId()));
        String referer = request.getHeader("Referer");
        model.addAttribute("previous", referer);
        return "updateexpense";
    }

    @PostMapping("/expense")
    public String postExpense(@ModelAttribute CreateExpenseDTO dto) {
        Integer idList = dto.getExpenseListId();
        expenseService.create(dto);
        return "redirect:/expenselistdetails/"+idList;
    }

    @PostMapping("/expense/update")
    public String postExpense(@ModelAttribute UpdateExpenseDTO dto) {
        expenseService.update(dto);
        return "redirect:/expenselistdetails/" + dto.getExpenseListId();
    }

    @GetMapping("/expense/delete/{id}")
    public String deleteExpense(@PathVariable int id){
        Expense readExpenseDTO= expenseService.getById(id);
        if (readExpenseDTO !=null){
            expenseService.delete(id);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense  not found "+id);
        }
        return "redirect:/";
    }
}
