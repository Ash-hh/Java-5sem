package com.shabunya.carrent.dto;

import com.shabunya.carrent.model.CarTypes;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import java.math.BigDecimal;
import java.util.Base64;

@Builder
@Data
@Getter
@Setter
public class CarsToIndexDTO {
    private Long car_id;
    private String carName;
    private BigDecimal costPerDay;
    private CarTypes type;
    private String carImage;
}
