package com.yh.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String name;
    private Date birthDay;
    private BigDecimal payment;
    private BigDecimal bonus;
}
