package com.shabunya.carrent.controllers;


import com.shabunya.carrent.dto.AdminUserUpdateDTO;
import com.shabunya.carrent.dto.UserOrdersDTO;
import com.shabunya.carrent.dto.UserProfileInfoDTO;
import com.shabunya.carrent.dto.UserUpdateDTO;
import com.shabunya.carrent.exception.ControllerException;
import com.shabunya.carrent.jwt.JwtFilter;
import com.shabunya.carrent.jwt.JwtProvider;
import com.shabunya.carrent.model.Order;
import com.shabunya.carrent.model.Role;
import com.shabunya.carrent.model.User;
import com.shabunya.carrent.services.OrderService;
import com.shabunya.carrent.services.UserService;
import org.apache.log4j.Logger;
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

@RestController
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final OrderService orderService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    public UserController(JwtFilter jwtFilter, UserService userService, OrderService orderService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.orderService = orderService;
        this.jwtProvider = jwtProvider;
        this.jwtFilter = jwtFilter;
    }

    private static final Logger logger = Logger.getLogger(UserController.class);

    @GetMapping("/profile/{login}")
    public ModelAndView ProfilePage(@PathVariable(name = "login")String login, Model model) throws ControllerException, SerialException {
        try{
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
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(),e);
        }
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
                        .balance(user.getBalance())
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


    @PutMapping("/admin/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody AdminUserUpdateDTO adminUserUpdateDTO) throws ControllerException {
        try{
            User user = userService.findById(adminUserUpdateDTO.getUserId());

            if(adminUserUpdateDTO.getNewRole() != null){
                user.setUserRole(userService.getRoleByName(adminUserUpdateDTO.getNewRole()));
            } else {
                user.setActive(adminUserUpdateDTO.isNewActivity());
            }

            userService.saveUser(user);

            logger.debug("update user"+user.getLogin());

            return  new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(),e);
        }
    }

    @DeleteMapping("/admin/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name="id")Long id) throws ControllerException {
        try{
            userService.deleteUser(id);
            logger.debug("delete user id:"+id.toString());


            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(),e);
        }

    }

    @PutMapping("/user/userUpdate")
    public ResponseEntity<?> userUpdateUser(@RequestBody UserUpdateDTO userUpdateDTO) throws ControllerException {
        try{
            User user = userService.findByLogin(jwtFilter.getCurrentUserLogin());

            user.setBalance(user.getBalance().add(userUpdateDTO.getNewBalance()));

            userService.saveUser(user);

            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(),e);
        }
    }

}
