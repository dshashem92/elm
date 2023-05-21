package com.example.elm.repository;

import com.example.elm.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    boolean existsByNationalId (String nationalId);


}
