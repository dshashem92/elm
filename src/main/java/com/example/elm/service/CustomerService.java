package com.example.elm.service;


import com.example.elm.dto.CustomerDto;
import com.example.elm.exception.CustomerException;
import com.example.elm.exception.ExceptionReasonMessageCodes;
import com.example.elm.exception.NotFoundException;
import com.example.elm.model.Customer;
import com.example.elm.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CacheEvict;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private CustomerDto mapToDTO(final Customer customer,final CustomerDto customerDto) {

        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setNationalId(customer.getNationalId());
        customerDto.setMobile(customer.getMobile());
        customerDto.setEmail(customer.getEmail());
        customerDto.setDob(customer.getDob());
        return customerDto;
    }


    private Customer mapToEntity(final CustomerDto customerDto, final Customer customer) {

        customer.setName(customerDto.getName());
        customer.setNationalId(customerDto.getNationalId());
        customer.setMobile(customerDto.getMobile());
        customer.setEmail(customerDto.getEmail());
        customer.setDob(customerDto.getDob());

        return customer;
    }


    private Boolean validateEnteredCustomer (CustomerDto customerDto){

        if (customerRepository.existsByEmail(customerDto.getEmail())) {
            return false;
        }

        if (customerRepository.existsByNationalId(customerDto.getNationalId())) {
            return false;
        }

        if (customerRepository.existsByMobile(customerDto.getMobile())) {
            return false;
        }
        return true;

    }


    @Caching(evict = {
            @CacheEvict(value="Customer", allEntries=true),
            @CacheEvict(value="Customers", allEntries=true)})

    public Integer create(final CustomerDto customerDto) {


        Boolean isValid = validateEnteredCustomer(customerDto);

        if (!isValid) {
            throw new CustomerException("customer with non valid  date",
                    ExceptionReasonMessageCodes.CUSTOMER_INVALID_INPUT_ERROR, HttpStatus.CONFLICT);
        }

        final Customer customer = new Customer();
        mapToEntity(customerDto,customer);
        customerRepository.save(customer);

         return customer.getId();


    }


    @Caching(evict = {
            @CacheEvict(value="Customer", allEntries=true),
            @CacheEvict(value="Customers", allEntries=true)})
    public void delete(final Integer id) {
        customerRepository.deleteById(id);
    }


    @Caching(evict = {
            @CacheEvict(value="Customer", allEntries=true),
            @CacheEvict(value="Customers", allEntries=true)})
    public void update(final Integer id, CustomerDto customerDto) {

        final Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException( String.format("Found no customer with id (%d) ", customerDto.getId() ), ExceptionReasonMessageCodes.NOT_FOUND_ERROR));
        mapToEntity(customerDto,customer);
        customerRepository.save(customer);
    }

    @Cacheable("Customer")
    public CustomerDto get(final Integer id) {
        return customerRepository.findById(id)
                .map(customer -> mapToDTO(customer,new CustomerDto()))
                .orElseThrow(() -> new NotFoundException( String.format("Found no customer with id (%d) ", id ), ExceptionReasonMessageCodes.NOT_FOUND_ERROR));
    }


    @Cacheable("Customers")
    public List<CustomerDto> findAll() {
        final List<Customer> customers = customerRepository.findAll(Sort.by("id"));
        return customers.stream()
                .map((customer) -> mapToDTO(customer,new CustomerDto()))
                .collect(Collectors.toList());
    }


}
