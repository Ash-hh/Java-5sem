package com.shabunya.carrent.dto;

import com.shabunya.carrent.model.CarTypes;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Setter
@Getter
public class CarUpdateDTO {
    public Long carId;
    public String carName;
    public CarTypes type;
    public BigDecimal costPerDay;
}
