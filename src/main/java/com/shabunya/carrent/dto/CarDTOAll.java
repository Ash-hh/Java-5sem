package com.shabunya.carrent.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shabunya.carrent.model.CarTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class CarDTOAll{
    public String carName;
    public CarTypes type;
    public BigDecimal costPerDay;
    public FileDTO CarImage;


}
