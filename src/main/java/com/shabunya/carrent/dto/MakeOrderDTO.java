package com.shabunya.carrent.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class MakeOrderDTO {

    public Long carId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date dateRentStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date dateRentEnd;

}
