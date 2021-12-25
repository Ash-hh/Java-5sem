package com.shabunya.carrent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CarrentApplication {

    public static void main(String[] args) {

      SpringApplication.run(CarrentApplication.class, args);

    }

}
