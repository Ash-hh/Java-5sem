package com.shabunya.carrent.controllers;

import com.shabunya.carrent.dto.MakeOrderDTO;
import com.shabunya.carrent.dto.RentUpdateDTO;
import com.shabunya.carrent.exception.ControllerException;
import com.shabunya.carrent.jwt.JwtFilter;
import com.shabunya.carrent.model.Order;
import com.shabunya.carrent.model.Order_Status;
import com.shabunya.carrent.services.OrderService;
import com.shabunya.carrent.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.serial.SerialException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;

@RestController
public class OrderController {

    @Autowired
    private static OrderService orderService;

    @Autowired
    private static UserService userService;

    public OrderController( UserService userService,OrderService orderService){
        this.orderService = orderService;
        this.userService = userService;
    }

    //TODO:Date Validation
    @PostMapping("/makeorder")
    public ResponseEntity<?> makeOrderForRent(@RequestBody MakeOrderDTO makeOrderDTO) throws ControllerException, SerialException {

        orderService.createOrder(makeOrderDTO, JwtFilter.getCurrentUserLogin());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user/updateOrder")
    public ResponseEntity<?> updateUserOrder(@RequestBody RentUpdateDTO rentUpdateDTO){
        Order order = orderService.findById(rentUpdateDTO.orderId).get();
        if(rentUpdateDTO.newStatus != null && rentUpdateDTO.newStatus != Order_Status.Deleted){
            order.setStatus(rentUpdateDTO.newStatus);
            order.setSumrentcost(rentUpdateDTO.getNewRentCost());
            order.setDateEnd(Date.valueOf(rentUpdateDTO.getNewRentDateEnd()));
            if(rentUpdateDTO.newStatus != Order_Status.Rent_End_Before_Start){
                if(!userService.updateUserAfterRentEnd(order)){
                    return new ResponseEntity<>(HttpStatus.PAYMENT_REQUIRED);
                }
            }
        } else {
            if(rentUpdateDTO.newStatus == Order_Status.Deleted){
                order.setStatus(rentUpdateDTO.newStatus);
            } else {
                order.setDateEnd(Date.valueOf(rentUpdateDTO.newRentDateEnd));
                order.setSumrentcost(rentUpdateDTO.getNewRentCost());
            }
        }

        orderService.updateOrder(order);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/admin/endRent/{orderId}")
    public ResponseEntity<?> endRentByAdmin(@PathVariable(name = "orderId")Long orderId){
        Order order = orderService.findById(orderId).get();
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-mm-dd");

        Date dateToday=new Date(System.currentTimeMillis());

        long today = System.currentTimeMillis();
        long notToday = order.getDateStart().getTime();

        if(dateToday.getTime() > order.getDateStart().getTime() || dateToday.getTime() == order.getDateStart().getTime()){
            long timeDiff =  dateToday.getTime() - order.getDateStart().getTime();
            long dayDiff = timeDiff / (1000 * 3600 * 24);

            order.setSumrentcost(dayDiff == 0 ?  order.getCar().getCostPerDay() : BigDecimal.valueOf(dayDiff).multiply(order.getCar().getCostPerDay()));
            order.setStatus(Order_Status.Rent_End);

            userService.updateUserAfterRentEndAdmin(order);
        } else {
            order.setStatus(Order_Status.Rent_End_Before_Start);
            order.setSumrentcost(BigDecimal.valueOf(0));
        }

        orderService.updateOrder(order);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/admin/deleteOrder/{id}")
    public ResponseEntity<?> deleteUserOrder(@PathVariable(name="id")Long id){
        orderService.deleteOrder(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }






}
