package com.felixvargas;

import com.felixvargas.customer.model.Customer;
import com.felixvargas.customer.repository.CustomerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;

@SpringBootApplication
public class Main {



    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args -> {

            Faker faker = new Faker();
            Name name = faker.name();
            String firstName = name.firstName();
            String lastName = name.lastName();
            Random random = new Random();
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@felixvargas.com";

            Customer customer = new Customer(
                    firstName + " " + lastName,
                    email,
                    random.nextInt(16, 99)
            );
           customerRepository.save(customer);
        };
    }

}
