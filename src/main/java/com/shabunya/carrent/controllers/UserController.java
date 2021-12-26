package com.shabunya.carrent.controllers;


import com.shabunya.carrent.dto.UserOrdersDTO;
import com.shabunya.carrent.dto.UserProfileInfoDTO;
import com.shabunya.carrent.exception.ControllerException;
import com.shabunya.carrent.jwt.JwtProvider;
import com.shabunya.carrent.model.Order;
import com.shabunya.carrent.model.User;
import com.shabunya.carrent.services.OrderService;
import com.shabunya.carrent.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.rowset.serial.SerialException;
import java.util.ArrayList;
import java.util.List;

@Controller

public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final OrderService orderService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    public UserController(UserService userService, OrderService orderService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.orderService = orderService;
        this.jwtProvider = jwtProvider;
    }


    @GetMapping("/profile/{login}")
    public ModelAndView ProfilePage(@PathVariable(name = "login")String login, Model model) throws ControllerException, SerialException {
        ModelAndView modelAndView = new ModelAndView();
        List<Order> orders = orderService.userOrders(login);
        List<UserOrdersDTO> userOrders = new ArrayList<>();
        for(Order order : orders){
            UserOrdersDTO orderToAdd = UserOrdersDTO.builder()
                    .orderId(order.getOrder_id())
                    .carName(order.getCar().getCarName())
                    .carType(order.getCar().getType())
                    .dateRentEnd(order.getDateEnd().toLocalDate())
                    .dateRentStart(order.getDateStart().toLocalDate())
                    .status(order.getStatus())
                    .sumRentCost(order.getSumrentcost())
                    .costPerDay(order.getCar().getCostPerDay())
                    .build();
            userOrders.add(orderToAdd);
        }
        modelAndView.setViewName("profile");
        model.addAttribute("orders",userOrders);
        return  modelAndView;
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<?> getUser(@RequestHeader(name = "Authorization") String jwt) throws ControllerException {

        try {

            String userName = jwtProvider.getLoginFromToken(jwt.substring(7));
            System.out.println(userName);
            User user = null;
            if(userService.existsUserByLogin(userName))
            user = userService.findByLogin(userName);
            System.out.println(user);
            if(user!=null){
                UserProfileInfoDTO userProfileInfoDTO = UserProfileInfoDTO.builder()
                        .login(user.getLogin())
                        .name(user.getName())
                        .surname(user.getSurname())
                        .email(user.getEmail())
                        .userRole(user.getUserRole().getName().name())
                        .build();
                return new ResponseEntity<>(userProfileInfoDTO,HttpStatus.OK);
            } else {

                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {

            throw new ControllerException("getUser", e);
        }
    }




}
