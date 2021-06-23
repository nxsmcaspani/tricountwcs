package com.wildcodeschool.tricount.controller;

import com.wildcodeschool.tricount.dto.ListExpenseListDto;
import com.wildcodeschool.tricount.dto.UpdateExpenseListDto;
import com.wildcodeschool.tricount.dto.CreateExpenseListDto;
import com.wildcodeschool.tricount.entity.ExpenseList;
import com.wildcodeschool.tricount.service.ContactService;
import com.wildcodeschool.tricount.service.ExpenseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ExpenseListController {
    @Autowired
    private ExpenseListService expenseListService;

    @Autowired
    private ContactService contactService;

    @GetMapping("/")
    public String getAll(Model model) {
        List<ListExpenseListDto> expensesListsDto = new ArrayList<>();
        for( ExpenseList expenseList : expenseListService.findAll()){
            expensesListsDto.add(expenseListService.convertFromEntityToDto(expenseList.getId(), Boolean.TRUE));
        }
        model.addAttribute("expenseslistsdto", expensesListsDto);
        return "index";
    }

    @GetMapping("/createlist")
    public String editExpensesList(Model model){
        CreateExpenseListDto createExpenseListDto = new CreateExpenseListDto();
        model.addAttribute("contactsdto", contactService.getAllContactsAsDto());
        model.addAttribute("createexpenselistdto", createExpenseListDto);
        return "createexpenselist";
    }

    @GetMapping("/updatelist/{id}")
    public String showExpensesList(Model model, @PathVariable(name = "id") Integer idList){
        UpdateExpenseListDto updateexpenselistdto = expenseListService.fromEntityToDtoForUpdate(idList);
        model.addAttribute("contactsdto", contactService.getAllContactsAsDto());
        model.addAttribute("updateexpenselistdto", updateexpenselistdto);
        return "updateexpenselist";
    }

    @PostMapping("/newexpenseslist")
    public String newExpensesList(@ModelAttribute CreateExpenseListDto createExpenseListDto) {
        expenseListService.create(createExpenseListDto);
        return "redirect:/";
    }

    @PostMapping("/updateexpenseslist")
    public String updateExpensesList(Model model, @ModelAttribute UpdateExpenseListDto UpdateExpenseListDto) {
        expenseListService.update(UpdateExpenseListDto);
        UpdateExpenseListDto updateexpenselistdto = expenseListService.fromEntityToDtoForUpdate(UpdateExpenseListDto.getId());
        model.addAttribute("contactsdto", contactService.getAllContactsAsDto());
        model.addAttribute("updateexpenselistdto", updateexpenselistdto);
        return "updateexpenselist";
    }

    @GetMapping("/expenseslist/delete/{idList}")
    public String deleteExpensesList(@PathVariable Integer idList){
        Optional<ExpenseList> optionalExpenseList = expenseListService.findById(idList);
        if(optionalExpenseList.isPresent()) {
            expenseListService.delete(idList);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense List ID not found "+idList);
        }
        return "redirect:/";
    }

}