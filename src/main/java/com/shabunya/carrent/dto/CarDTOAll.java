package com.shabunya.carrent.dto;

import com.shabunya.carrent.model.CarTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDTOAll{
    public String carName;
    public CarTypes type;
    public BigDecimal costPerDay;
}
