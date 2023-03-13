package com.felixvargas.customer.controller;

import com.felixvargas.customer.model.Customer;
import com.felixvargas.customer.records.CustomerRegReq;
import com.felixvargas.customer.records.CustomerUpdateRequest;
import com.felixvargas.customer.service.CustomerJDBCDataAccessService;
import com.felixvargas.customer.service.CustomerJPADataAccessService;
import com.felixvargas.customer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController  // Spring annotation indicating that this class is a REST controller
@RequestMapping("/api/v1/customer")  // Root URL for all customer-related endpoints
public class CustomerController {

    private final CustomerJPADataAccessService customerJPADataAccessService;  // Field to hold a CustomerJPADataAccessService object
    private final CustomerService customerService;  // Field to hold a CustomerService object
    private final CustomerJDBCDataAccessService customerJDBCDataAccessService;

    // Constructor that injects a CustomerJPADataAccessService and CustomerService object
    public CustomerController(CustomerJPADataAccessService customerJPADataAccessService, CustomerService customerService, CustomerJDBCDataAccessService customerJDBCDataAccessService) {
        this.customerJPADataAccessService = customerJPADataAccessService;
        this.customerService = customerService;
        this.customerJDBCDataAccessService = customerJDBCDataAccessService;
    }

    // Endpoint to retrieve all customers
    @GetMapping
    public List<Customer> getCustomers(){
        return customerJDBCDataAccessService.selectAllCustomer();  // Calls the selectAllCustomer() method of the
        // customerJPADataAccessService object
    }

    // Endpoint to retrieve a customer by ID (returns Optional)
    @GetMapping("{id}")
    public Optional<Customer> getCustomers(@PathVariable("id") Integer id){
        return customerJDBCDataAccessService.selectCustomerById(id);  // Calls the selectCustomerById() method of the
        // customerJPADataAccessService object
    }

    // Endpoint to add a new customer
    @PostMapping
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerRegReq request){
        customerService.addCustomer(request);  // Calls the addCustomer() method of the customerService object
        String message = "Customer %s Successfully Created".formatted(request.name());  // Create success message
        return ResponseEntity.ok(message);  // Returns a success response with the message
    }

    // Endpoint to delete a customer by ID
    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer customerId){
        customerJDBCDataAccessService.deleteCustomerById(customerId);  // Calls the deleteCustomerById() method of the customerService
        // object
    }

    // Endpoint to update a customer by ID
    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer customerId,
                               @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateCustomer( customerId, customerUpdateRequest);  // Calls the updateCustomer() method of the customerService object
    }
}
