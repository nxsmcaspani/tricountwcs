package com.wildcodeschool.tricount.controller;

import com.wildcodeschool.tricount.dto.CreateExpenseDTO;
import com.wildcodeschool.tricount.dto.ReadExpenseDTO;
import com.wildcodeschool.tricount.dto.UpdateExpenseDTO;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.service.ContactService;
import com.wildcodeschool.tricount.service.ExpenseListService;
import com.wildcodeschool.tricount.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ExpenseController {
    @Autowired
    ExpenseService expenseService;

    @Autowired
    ContactService contactService;

    @Autowired
    ExpenseListService expenseListService;

    @GetMapping("/createexpense/{id}")
    public String getCreateExpensePage(Model model, @PathVariable(name = "id") Integer idList, HttpServletRequest request) {
        model.addAttribute("createexpensedto", expenseService.mapGetCreateExpenseToDTO(idList));
        String referer = request.getHeader("Referer");
        model.addAttribute("previouspage", referer);
        return "createexpense";
    }
    
    @GetMapping("/updateexpense/{id}")
    public String getUpdateExpensePage(Model model, @PathVariable(name = "id") Integer idExpense) {
        UpdateExpenseDTO dto = expenseService.mapGetUpdateExpenseDTO(idExpense);
        model.addAttribute("updateexpensedto", dto);
        model.addAttribute("contactsdto", expenseListService.getAllContactsAsDto(dto.getExpenseListId()));
        return "updateexpense";
    }

    @PostMapping("/expense")
    public String postExpense(Model model, @ModelAttribute CreateExpenseDTO dto) {
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


   // @DeleteMapping("/expense/{id}")
    //@ResponseBody
    //public ResponseEntity<Integer> deleteExpense(@PathVariable int id) {
      //  Boolean isRemoved = expenseService.delete(id);
        //if (!isRemoved) {
         //   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       // }
        //return new ResponseEntity<>(HttpStatus.OK);

    //
}
