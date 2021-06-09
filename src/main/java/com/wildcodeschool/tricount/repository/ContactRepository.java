package com.wildcodeschool.tricount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wildcodeschool.tricount.entity.Contact;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>{
    
}

