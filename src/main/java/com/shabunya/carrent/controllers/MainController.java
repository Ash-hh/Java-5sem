package com.shabunya.carrent.controllers;

import com.shabunya.carrent.dto.*;
import com.shabunya.carrent.exception.ControllerException;
import com.shabunya.carrent.jwt.JwtProvider;
import com.shabunya.carrent.model.*;
import com.shabunya.carrent.services.CarService;
import com.shabunya.carrent.services.OrderService;
import com.shabunya.carrent.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private OrderService orderService;
    @Autowired
    private static CarService carService;

    @Autowired
    public MainController(OrderService orderService, UserService userService, CarService carService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.carService = carService;
        this.orderService = orderService;
    }



    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) throws ControllerException
    {
        try{

            User user = userService.findByLoginAndPassword(authRequest.getLogin(), authRequest.getPassword());
            if(user != null)
            {
                String token = jwtProvider.generateToken(user.getLogin());
                AuthResponse response = new AuthResponse(token, user.getUserRole().getName());
                System.out.println(user.getUserRole().getName());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else
            {
                throw new ControllerException("not such user");
            }
        } catch (ControllerException e) {


            throw new ControllerException("auth", e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest)
    {

        if(!userService.existsUserByLogin(registrationRequest.getLogin()))
        {
            User user = new User();
            user.setPassword(registrationRequest.getPassword());
            user.setLogin(registrationRequest.getLogin());
            user.setEmail(registrationRequest.getEmail());
            user.setSurname(registrationRequest.getSurname());
            user.setName(registrationRequest.getName());

            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
    }

    @PostMapping("/authorized")
    public ResponseEntity<?> isAuthorized() throws ControllerException {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping({"/",""})
    public ModelAndView indexPage(Model model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        model.addAttribute("cars",carService.getAll());
        return modelAndView;
    }


    @GetMapping("/admin/adminpage/{tableName}")
    public ModelAndView adminPage(Model model, @PathVariable String tableName){
        ModelAndView modelAndView = new ModelAndView("adminpage");
        List<CarTypes> types = Arrays.asList(CarTypes.values());
        List<User> users = null;
        List<Car> cars = null;
        List<Order> orders = null;
        List<Order_Status> statuses = Arrays.asList(Order_Status.values());
        users = userService.findAll();
        cars = carService.getAll();
        orders = orderService.getAllOrders();
        AdminTablesDTO tables = new AdminTablesDTO();
        tables.setAllCars(cars);
        tables.setAllOrder(orders);
        tables.setAllUsers(users);
        tables.setType(types);
        tables.setStatuses(statuses);
        tables.setTableName(tableName);
        model.addAttribute("tables",tables);
        return modelAndView;
    }
    //TODO: Bill system
    //TODO: Search and Filter
    //TODO: Email send

    //TODO:Log4j
    //TODO:JUnit



}
