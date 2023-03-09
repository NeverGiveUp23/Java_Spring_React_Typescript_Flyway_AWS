//package com.felixvargas;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//@SpringBootApplication
//@RestController
//public class Main2 {
//    private static List<Customer> customers;
//    static {
//        customers = new ArrayList<>();
//        Customer felix = new Customer(
//                1,
//                "Felix",
//                "Felix@gmail.com",
//                29
//        );
//        customers.add(felix);
//
//        Customer jane = new Customer(
//                2,
//                "Jane",
//                "Jane@gmail.com",
//                45
//        );
//        customers.add(jane);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(Main2.class, args);
//    }
//    @GetMapping("/")
//    public List<Customer> getCustomers(){
//        return customers;
//    }
//
//    @GetMapping("/get/customer/{id}")
//    public Customer getCustomers(@PathVariable("id") Integer id){
//        Customer customer = customers.stream()
//                .filter(c -> c.id.equals(id))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("Customer  [%s] Does not exist".formatted(id)));
//        return customer;
//    }
//
//
//    static class Customer{
//        private Integer id;
//        private String name;
//        private String email;
//        private Integer age;
//
//        public Customer(){}
//
//        public Customer(Integer id, String name, String email, Integer age) {
//            this.id = id;
//            this.name = name;
//            this.email = email;
//            this.age = age;
//        }
//
//        // Getter and Setters
//
//        public Integer getId() {
//            return id;
//        }
//
//        public void setId(Integer id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getEmail() {
//            return email;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public Integer getAge() {
//            return age;
//        }
//
//        public void setAge(Integer age) {
//            this.age = age;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            Customer customer = (Customer) o;
//            return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && Objects.equals(age, customer.age);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(id, name, email, age);
//        }
//
//        @Override
//        public String toString() {
//            return "Customer{" +
//                    "id=" + id +
//                    ", name='" + name + '\'' +
//                    ", email='" + email + '\'' +
//                    ", age=" + age +
//                    '}';
//        }
//    }
//}
