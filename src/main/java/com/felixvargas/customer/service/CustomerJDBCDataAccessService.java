package com.felixvargas.customer.service;

import com.felixvargas.customer.customerMapper.CustomerRowMapper;
import com.felixvargas.customer.interfaces.CustomerDAO;
import com.felixvargas.customer.model.Customer;
import com.felixvargas.exception.CustomerSuccessException;
import com.felixvargas.exception.ResourceNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDAO {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomer() {
        var sql = """     
                SELECT id, name, email, age
                FROM customer
                """;  // <- SQL method for selecting all customers
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        var sql = """
                SELECT id, name, email, age FROM customer WHERE id = ?
                """;
        return Optional.ofNullable(jdbcTemplate.query(sql, customerRowMapper, id)  // <- pass in arguments -> in this case it is id
                .stream()  // <- we stream here because it will give us a list
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Customer Not Found WIth Id: " + id)));
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
               INSERT INTO customer(name, email, age) VALUES (?, ?, ?)
                """;

         var result = jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()           // <- passing in all arguments you expect to return
        );
        System.out.println("jdbcTemplate.update = " + result);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        var sql = """
                SELECT count(id) FROM customer WHERE email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public void deleteCustomerById(Integer customerId) {
        var sql = """
                DELETE FROM customer WHERE id = ?
                """;
       int rowsAffected = jdbcTemplate.update(sql, customerId);

       if (rowsAffected == 0){
           throw new ResourceNotFoundException("No User Found With Id: " + customerId);
       }
       else {
           throw new CustomerSuccessException("Congrats, User #%s You're Gone From Our History!".formatted(customerId));
       }
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        var sql = """
                SELECT count(id) FROM customer WHERE id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void updateCustomer(Customer update) {
        if(update.getName() != null){
            String sql = "UPDATE customer SET name = ? WHERE id = ?";
            int result = jdbcTemplate.update(sql, update.getName(), update.getId());
            System.out.println("Update Customer Name Result = " + result);
        }

        if(update.getEmail() != null){
            String sql = "UPDATE customer SET email= ? WHERE id = ?";
            int result = jdbcTemplate.update(sql, update.getEmail(), update.getId());
            System.out.println("Update Customer email Result = " + result);
        }

        if(update.getAge() != null){
            String sql = "UPDATE customer SET age = ? WHERE id = ?";
            int result = jdbcTemplate.update(sql, update.getAge(), update.getId());
            System.out.println("Update Customer Name Result = " + result);
        }


    }
}
