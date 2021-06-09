package com.wildcodeschool.tricount.repository;

import com.wildcodeschool.tricount.entity.ExpenseList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseListRepository extends JpaRepository<ExpenseList, Integer>{
}
