package com.felixvargas.customer.model;

import jakarta.persistence.*;

import java.util.Objects;
// Entity to create the table
@Entity
@Table(
        name = "customer",
        uniqueConstraints = {
        @UniqueConstraint( name = "customer_email_key",  // <- needs to match constraint key name in DB
                columnNames = "email"  // <= passing in the name of the column we want to make unique
          )
        }
)
public class Customer {
    // the id will generate automatically
    @Id
    @SequenceGenerator(
            name = "customer_id_seq",   // <- needs to match flyway seq key name
            sequenceName = "customer_id_seq",
            allocationSize = 1  // <- change default alloc from [50] to [1]
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_seq"
    )
    private Integer id;
    @Column(
            nullable = false
    )
    private String name;
    @Column(
            nullable = false
    )
    private String email;
    @Column(
            nullable = false
    )
    private Integer age;

    public Customer(){}

    public Customer(Integer id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public Customer( String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // Getter and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && Objects.equals(age, customer.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }


}
