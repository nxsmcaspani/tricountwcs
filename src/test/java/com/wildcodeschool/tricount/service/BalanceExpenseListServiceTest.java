package com.wildcodeschool.tricount.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class BalanceExpenseListServiceTest {

    @Test
    void contextLoads() {
        System.out.println("contextLoads");
    }

    @Autowired
    private BalanceExpenseListService balExp;
    
    
    
}
