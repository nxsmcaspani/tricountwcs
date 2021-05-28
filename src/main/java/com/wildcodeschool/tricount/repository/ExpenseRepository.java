package com.wildcodeschool.tricount.repository;

import com.wildcodeschool.tricount.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer>{
}
