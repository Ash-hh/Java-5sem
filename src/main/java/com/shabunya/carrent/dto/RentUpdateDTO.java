package com.shabunya.carrent.dto;

import com.shabunya.carrent.model.Order_Status;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Getter
@Setter
public class RentUpdateDTO {
    public Long orderId;
    public LocalDate newRentDateEnd;
    public BigDecimal newRentCost;
    public Order_Status newStatus;
}
