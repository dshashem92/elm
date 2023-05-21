package com.example.elm.controller;


import com.example.elm.dto.CustomerDto;
import com.example.elm.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/customer", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", maxAge = 3600)

public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(
            @PathVariable final Integer id) {
        return ResponseEntity.ok(customerService.get(id));
    }



    @PostMapping
    public ResponseEntity<Integer> createCustomer(
            @RequestBody final CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.create(customerDto), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable final Integer id,
                                                         @RequestBody final CustomerDto customerDto) {
        customerService.update(id, customerDto);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable final Integer id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
