package com.felixvargas.customer.interfaces;

import com.felixvargas.customer.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {

    List<Customer> selectAllCustomer();

    Optional<Customer> selectCustomerById(Integer id);

    void insertCustomer(Customer customer);

    boolean existsPersonWithEmail(String email);

    void deleteCustomerById(Integer customerId);
    boolean existsPersonWithId(Integer Id);

    void updateCustomer(Customer update);
}
