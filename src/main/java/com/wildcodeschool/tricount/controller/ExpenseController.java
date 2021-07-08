package com.wildcodeschool.tricount.controller;

import com.wildcodeschool.tricount.dto.CreateExpenseDTO;
import com.wildcodeschool.tricount.dto.ReadExpenseDTO;
import com.wildcodeschool.tricount.dto.UpdateExpenseDTO;
import com.wildcodeschool.tricount.entity.Expense;
import com.wildcodeschool.tricount.service.ContactService;
import com.wildcodeschool.tricount.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Controller
public class ExpenseController {
    @Autowired
    ExpenseService expenseService;

    @Autowired
    ContactService contactService;

    @GetMapping("/expense/{id}")
    @ResponseBody
    public ReadExpenseDTO getExpense(@PathVariable int id) {
        ReadExpenseDTO dto = expenseService.getById(id);
        return dto;
    }

    @GetMapping("/createexpense/{id}")
    public String getCreateExpensePage(Model model, @PathVariable(name = "id") Integer idList) {
        model.addAttribute("createexpensedto", expenseService.mapGetCreateExpenseToDTO(idList));
//        model.addAttribute("contactsdto", contactService.getAllContactsAsDto());
        return "createexpense";
    }

    @PostMapping("/expense")
    public String postExpense(Model model, @ModelAttribute CreateExpenseDTO dto) {
        Integer idList = dto.getExpenseListId();
        expenseService.create(dto);
        return "redirect:/updatelist/"+idList;
    }

    @PostMapping("/expense/update")
    @ResponseBody
    public ResponseEntity<Expense> postExpense(@ModelAttribute UpdateExpenseDTO dto) {
        Expense exp = expenseService.update(dto);
        return new ResponseEntity<Expense>(exp, HttpStatus.OK);
    }


    @GetMapping("/expense/delete/{id}")
    public String deleteExpense(@PathVariable int id){
        ReadExpenseDTO  readExpenseDTO= expenseService.getById(id);

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
