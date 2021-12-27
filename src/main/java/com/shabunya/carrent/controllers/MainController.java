package com.shabunya.carrent.controllers;

import com.shabunya.carrent.dto.*;
import com.shabunya.carrent.exception.ControllerException;
import com.shabunya.carrent.jwt.JwtProvider;
import com.shabunya.carrent.model.*;
import com.shabunya.carrent.services.CarService;
import com.shabunya.carrent.services.MailSender;
import com.shabunya.carrent.services.OrderService;
import com.shabunya.carrent.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
public class MainController {


    @Autowired
    private MailSender mailSender;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private OrderService orderService;
    @Autowired
    private static CarService carService;

    @Autowired
    public MainController(MailSender mailSender, OrderService orderService, UserService userService, CarService carService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.carService = carService;
        this.mailSender =mailSender;
        this.orderService = orderService;
    }



    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) throws ControllerException
    {
        try{

            User user = userService.findByLoginAndPassword(authRequest.getLogin(), authRequest.getPassword());
            if(user != null)
            {
                if(user.isActive()){
                    String token = jwtProvider.generateToken(user.getLogin());
                    AuthResponse response = new AuthResponse(token, user.getUserRole().getName());
                    System.out.println(user.getUserRole().getName());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Verify your account",HttpStatus.FORBIDDEN);
                }

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
            user.setActivationCode(UUID.randomUUID().toString());
            user.setActive(false);
            userService.saveUser(user);

            if(!user.getEmail().isEmpty()){
                String message = String.format("Hello, %s!\n " +
                                "Welcome Radiator Springs! Please, visit next link: http://localhost:8080/activate/%s",
                        user.getLogin(), user.getActivationCode());
                mailSender.sendMail(user.getEmail(), "Activation code", message);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }
    }

    @GetMapping("/activate/{code}")
    public ModelAndView activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated");

        } else {
            model.addAttribute("message", "Activation code is not found!");
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/authorized")
    public ResponseEntity<?> isAuthorized() throws ControllerException {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value={"/",""},produces = MediaType.IMAGE_JPEG_VALUE)
    public ModelAndView indexPage(Model model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        List<Car> cars = carService.getAll();
        List<CarTypes> carTypes = Arrays.asList(CarTypes.values());
        List<CarsToIndexDTO> carsToUpload = new ArrayList<>();
        for(Car car : cars){

            carsToUpload.add(CarsToIndexDTO.builder()
                    .carName(car.getCarName())
                    .costPerDay(car.getCostPerDay())
                    .type(car.getType())
                    .car_id(car.getCar_id())
                    .carImage(Base64.getEncoder().encodeToString(car.getCarImage()))
                    .build());
        }
        model.addAttribute("carTypes",carTypes);
        model.addAttribute("cars",carsToUpload);
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
        List<CarsToIndexDTO> carsToUpload = new ArrayList<>();
        for(Car car : cars){

            carsToUpload.add(CarsToIndexDTO.builder()
                    .carName(car.getCarName())
                    .costPerDay(car.getCostPerDay())
                    .type(car.getType())
                    .car_id(car.getCar_id())
                    .carImage(Base64.getEncoder().encodeToString(car.getCarImage()))
                    .build());
        }
        AdminTablesDTO tables = new AdminTablesDTO();
        tables.setAllCars(carsToUpload);
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
