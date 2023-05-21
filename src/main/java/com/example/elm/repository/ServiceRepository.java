package com.example.elm.repository;

import com.example.elm.model.Customer;
import com.example.elm.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

    List<Service> findAllByCustomer(Customer customer);
}
