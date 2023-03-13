package com.felixvargas.customer.service;

import com.felixvargas.customer.model.Customer;
import com.felixvargas.customer.interfaces.CustomerDAO;
import com.felixvargas.customer.records.CustomerRegReq;
import com.felixvargas.customer.records.CustomerUpdateRequest;
import com.felixvargas.exception.DuplicateResourceException;
import com.felixvargas.exception.RequestValidationException;
import com.felixvargas.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service  // Spring annotation indicating that this class is a service
public class CustomerService {

    private final CustomerDAO customerDAO;  // field to hold a CustomerDAO object

    // Constructor that injects a CustomerDAO object using the @Qualifier annotation
    public CustomerService(@Qualifier("jdbc") CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }


    // Method to add a new customer to the database
    public void addCustomer(CustomerRegReq customerRegReq) {
        // Check if email already exists in the database
        String email = customerRegReq.email();  // Extract email from customerRegReq
        if (customerDAO.existsPersonWithEmail(email)) {
            throw new DuplicateResourceException("email already taken");  // Throws exception if email already exists
        }
        // Create a new customer object using data from customerRegReq and insert it into the database
        Customer newCustomer = new Customer(
                customerRegReq.name(),
                customerRegReq.email(),
                customerRegReq.age()
        );
        customerDAO.insertCustomer(newCustomer);
    }

    // Method to delete a customer by ID from the database
    public void deleteCustomerById(Integer customerId) {
        if (!customerDAO.existsPersonWithId(customerId)) {
            throw new ResourceNotFoundException(
                    "Customer with id %s not found".formatted(customerId)
            );  // Throws exception if customer with the specified ID does not exist
        }
        customerDAO.deleteCustomerById(customerId);  // Deletes the customer from the database
    }

    // Private method to retrieve a customer by ID from the database (used internally by updateCustomer method)
    private Customer getCustomer(Integer customerId) {
        return customerDAO.selectCustomerById(customerId).orElseThrow(() -> new ResourceNotFoundException(
                "Customer with id %s not found".formatted(customerId)
        ));  // Throws exception if customer with the specified ID does not exist
    }

    // Method to update a customer in the database
    public void updateCustomer(Integer customerId, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = getCustomer(customerId);  // Retrieve the customer to be updated

        boolean changes = false;  // Flag to track if any changes were made

        // Check if any fields in customerUpdateRequest are different from the corresponding fields in the customer object
        if (customerUpdateRequest.name() != null && !customerUpdateRequest.name().equals(customer.getName())) {
            customer.setName(customerUpdateRequest.name());  // Update the name field in the customer object
            changes = true;
        }

        if (customerUpdateRequest.age() != null && !customerUpdateRequest.age().equals(customer.getAge())) {
            customer.setAge(customerUpdateRequest.age());  // Update the age field in the customer object
            changes = true;
        }

        if (customerUpdateRequest.email() != null && !customerUpdateRequest.email().equals(customer.getEmail())) {
            if (customerDAO.existsPersonWithEmail(customerUpdateRequest.email())) {
                throw new DuplicateResourceException(
                        "Email already exist"
                );  // Throws exception if the new email already exists in the database
            }
            customer.setEmail(customerUpdateRequest.email());  // Update the email field in the customer object
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No data was changed");  // Throws exception if no changes were made
        }

        customerDAO.updateCustomer(customer);  // Updates the customer in the database
    }
}

