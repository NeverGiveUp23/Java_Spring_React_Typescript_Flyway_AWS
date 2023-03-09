package com.felixvargas.customer.service;

import com.felixvargas.customer.model.Customer;
import com.felixvargas.customer.interfaces.CustomerDAO;
import com.felixvargas.customer.repository.CustomerRepository;
import com.felixvargas.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository("jpa")
public class CustomerJPADataAccessService implements CustomerDAO {
    // inject Repository
    private final CustomerRepository customerRepository;
    // Use constructor injection
    public CustomerJPADataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    // getting findAll() -> customerRepository
    @Override
    public List<Customer> selectAllCustomer() {
        return customerRepository.findAll();
    }
    // getting findById() -> customerRepository -> Has to be Optional<Customer> , we want to error handle if a
    // customer is null -> <Customer> is the class we want to grab the id from -> .orElseThrow is what will throw is
    // the customer is null
    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return Optional.ofNullable(customerRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer %s not found".formatted(id))
                ));
    }


    // person exist by id -> calling from the Repository
    @Override
    public boolean existsPersonWithId(Integer id) {
        return customerRepository.existsCustomerById(id);
    }
    // Updating customer based off of id -> using Repository and .save() method.
    @Override
    public void updateCustomer(Customer update) {
        customerRepository.save(update);
    }
    // deleting customer by id -> calling from Repository
    @Override
    public void deleteCustomerById(Integer customerId) {
        customerRepository.deleteById(customerId);
    }
    // Adding customer based off of id -> using Repository and .save() method.
    @Override
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }
    // Checking if the user exist via email
    @Override
    public boolean existsPersonWithEmail(String email) {
       return customerRepository.existsCustomerByEmail(email);
    }


}
