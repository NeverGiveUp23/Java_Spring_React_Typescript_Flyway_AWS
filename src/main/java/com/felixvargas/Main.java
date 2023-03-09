package com.felixvargas;

import com.felixvargas.customer.model.Customer;
import com.felixvargas.customer.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Main {



    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args -> {
            Customer felix = new Customer(
                    "Felix",
                    "Felix@gmail.com",
                    29
            );

            Customer jane = new Customer(
                    "Jane",
                    "Jane@gmail.com",
                    45
            );

           List<Customer> customers = List.of(felix, jane);
           //customerRepository.saveAll(customers);
        };
    }

}
