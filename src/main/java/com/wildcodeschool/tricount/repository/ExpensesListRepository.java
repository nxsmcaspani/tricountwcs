package com.wildcodeschool.tricount.repository;

import com.wildcodeschool.tricount.controller.ExpensesList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesListRepository extends JpaRepository<ExpensesList, Long>{
}
