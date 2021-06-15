package com.wildcodeschool.tricount.controller;

import com.wildcodeschool.tricount.dto.CreateOrUpdateExpenseListDto;
import com.wildcodeschool.tricount.entity.ExpenseList;
import com.wildcodeschool.tricount.service.ExpenseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
public class ExpenseListController {
    @Autowired
    private ExpenseListService expenseListService;

    @GetMapping("/")
    public String getAll(Model model) {
        model.addAttribute("expenseslists", expenseListService.findAll());
        return "index";
    }

    @GetMapping("/expenseslist")
    @ResponseBody
    public ExpenseList getExpensesList(@RequestParam Integer id){
        return expenseListService.getExpenseList(id);
    }

    @GetMapping(value = {"/createorupdatelist", "/createorupdatelist/{id}" })
    public String editExpensesList(Model model, @PathVariable(required = false, name = "id") Integer idList){
        if(idList != null){
            model.addAttribute("expenselistdto", expenseListService.convertFromEntityToDto(idList));
        } else {
            model.addAttribute("expenselistdto", new CreateOrUpdateExpenseListDto());
        }
        return "createorupdatelist";
    }

    @PostMapping("/expenseslist")
    public String postExpensesList(@ModelAttribute CreateOrUpdateExpenseListDto expenseListDto) {
        expenseListService.save(expenseListService.convertFromDtoToEntity(expenseListDto));
        return "redirect:/";
    }

    @PutMapping("/expenseslist")
    public String updateExpensesList(@ModelAttribute CreateOrUpdateExpenseListDto expenseListDto){
        expenseListService.save(expenseListService.convertFromDtoToEntity(expenseListDto));
        return "redirect:/";
    }

//    @DeleteMapping("/expenseslist")
    @GetMapping("/expenseslist/delete/{idList}")
//    public String deleteExpensesList(Model model, @RequestParam Integer idList){
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