package com.wildcodeschool.tricount.repository;
import java.util.List;
import com.wildcodeschool.tricount.entity.ExpenseList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseListRepository extends JpaRepository<ExpenseList, Integer>{
    List<ExpenseList> findByContactsId(Integer contactsId);
}
