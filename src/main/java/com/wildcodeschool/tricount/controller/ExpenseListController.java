package com.wildcodeschool.tricount.controller;

import com.wildcodeschool.tricount.dto.ListExpenseListDto;
import com.wildcodeschool.tricount.dto.UpdateExpenseListDto;
import com.wildcodeschool.tricount.dto.CreateExpenseListDto;
import com.wildcodeschool.tricount.entity.ExpenseList;
import com.wildcodeschool.tricount.mappers.ExpenseListMapper;
import com.wildcodeschool.tricount.service.ContactService;
import com.wildcodeschool.tricount.service.ExpenseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ExpenseListController {
    @Autowired
    private ExpenseListMapper expenseListMapper;

    @Autowired
    private ExpenseListService expenseListService;

    @Autowired
    private ContactService contactService;



    @GetMapping("/")
    public String getAll(Model model) {
        List<ListExpenseListDto> expensesListsDto = new ArrayList<>();
        for( ExpenseList expenseList : expenseListService.findAll()){
            expensesListsDto.add(expenseListMapper.convertFromEntityToDto(expenseList.getId(), Boolean.TRUE));
        }
        model.addAttribute("expenseslistsdto", expensesListsDto);
        return "index";
    }

    @GetMapping("/createlist")
    public String createtExpensesList(Model model, HttpServletRequest request){
        CreateExpenseListDto createExpenseListDto = new CreateExpenseListDto();
        model.addAttribute("contactsdto", contactService.getAllContactsAsDto());
        model.addAttribute("createexpenselistdto", createExpenseListDto);
        String referer = request.getHeader("Referer");
        model.addAttribute("previous", referer);
        return "createexpenselist";
    }

    @GetMapping("/expenselistdetails/{id}")
    public String showExpensesListDetails(Model model, @PathVariable(name = "id") Integer idList, HttpServletRequest request){
        UpdateExpenseListDto updateexpenselistdto = expenseListMapper.fromEntityToDtoForUpdate(idList);
        model.addAttribute("contactsdto", contactService.getAllContactsAsDto());
        model.addAttribute("updateexpenselistdto", updateexpenselistdto);
        String referer = request.getHeader("Referer");
        model.addAttribute("previous", referer);
        return "expenselistdetails";
    }

    @PostMapping("/newexpenseslist")
    public String newExpensesList(@ModelAttribute CreateExpenseListDto createExpenseListDto) {
        expenseListService.create(createExpenseListDto);
        return "redirect:/";
    }

    // Using HttpServletRequest to redirect to page the update was done from (either updatelist or pagedetails)
    @PostMapping("/updateexpenseslist")
    public String updateExpensesList(Model model, @ModelAttribute UpdateExpenseListDto UpdateExpenseListDto, HttpServletRequest request) {
        expenseListService.update(UpdateExpenseListDto);
        UpdateExpenseListDto updateexpenselistdto = expenseListMapper.fromEntityToDtoForUpdate(UpdateExpenseListDto.getId());
        model.addAttribute("contactsdto", contactService.getAllContactsAsDto());
        model.addAttribute("updateexpenselistdto", updateexpenselistdto);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
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