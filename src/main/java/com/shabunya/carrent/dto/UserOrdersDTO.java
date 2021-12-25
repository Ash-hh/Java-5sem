package com.shabunya.carrent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrdersDTO {
    public String carName;
    public String carType;
    public LocalDate dateRentStart;
    public LocalDate dateRentEnd;
    public BigDecimal sumRentCost;
}
