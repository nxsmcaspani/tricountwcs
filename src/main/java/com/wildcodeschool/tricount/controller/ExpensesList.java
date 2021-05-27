package com.wildcodeschool.tricount.controller;

import com.wildcodeschool.tricount.repository.ExpensesListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// Below for future use
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExpensesList {
        @Autowired
        private ExpensesListRepository expensesListRepository;

        @GetMapping("/")
        public String getAll(Model model) {
            model.addAttribute("expenseslists", expensesListRepository.findAll());
            return "expenseslists";
        }

}
