package com.wildcodeschool.tricount.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.wildcodeschool.tricount.dto.CreateExpenseDTO;
import com.wildcodeschool.tricount.dto.ReadExpenseDTO;
import com.wildcodeschool.tricount.entity.Expense;

public class Utils {

    public static LocalDate convertDateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
    }
}
