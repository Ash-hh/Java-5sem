package com.shabunya.carrent.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDTOForRent {
    public String carName;
    public String type;
    public BigDecimal costPerDay;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date dateRentStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date dateRentEnd;
}
