package com.shabunya.carrent.dto;

import com.shabunya.carrent.model.*;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
public class AdminTablesDTO {
    public List<Order> allOrder;
    public List<CarsToIndexDTO> allCars;
    public List<User> allUsers;
    public List<CarTypes> type;
    public List<Order_Status> statuses;
    public String tableName;
}
