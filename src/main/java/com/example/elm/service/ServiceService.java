package com.example.elm.service;

import com.example.elm.dto.ServiceDto;
import com.example.elm.exception.ExceptionReasonMessageCodes;
import com.example.elm.exception.NotFoundException;
import com.example.elm.model.Customer;
import com.example.elm.model.Service;
import com.example.elm.repository.CustomerRepository;
import com.example.elm.repository.ServiceRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;


import java.util.List;

import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceService {
    private final CustomerRepository customerRepository;
    private final ServiceRepository serviceRepository;

    public ServiceService(CustomerRepository customerRepository,
                          ServiceRepository serviceRepository) {
        this.customerRepository = customerRepository;
        this.serviceRepository = serviceRepository;
    }


    private Service mapToEntity(final ServiceDto serviceDto, final Service service) {

        service.setCost(serviceDto.getCost());
        service.setTitle(serviceDto.getTitle());
        service.setDuration(serviceDto.getDuration());
        service.setTitle(serviceDto.getTitle());
        service.setDescription(serviceDto.getDescription());
        final Customer customer = serviceDto.getCustomerId() == null ? null : customerRepository.findById(serviceDto.getCustomerId())
                .orElseThrow(() -> new NotFoundException( String.format("Found no customer with id (%d) ", serviceDto.getCustomerId() ), ExceptionReasonMessageCodes.NOT_FOUND_ERROR));
        service.setCustomer(customer);
        return service;
    }


    @Caching(evict = {
            @CacheEvict(value="CustomerServices", allEntries=true),
            @CacheEvict(value="Services", allEntries=true)})
    public Integer create(final ServiceDto serviceDto) {

        final Service service = new Service();
        mapToEntity(serviceDto,service);
        serviceRepository.save(service);
        return service.getId();
    }

    @Caching(evict = {
            @CacheEvict(value="CustomerServices", allEntries=true),
            @CacheEvict(value="Services", allEntries=true)})
    public void delete(final Integer id) {
        serviceRepository.deleteById(id);
    }


    private ServiceDto mapToDto(final Service service,final ServiceDto serviceDto) {
        serviceDto.setId(service.getId());
        serviceDto.setCost(service.getCost());
        serviceDto.setTitle(service.getTitle());
        serviceDto.setDuration(service.getDuration());
        serviceDto.setDescription(service.getDescription());

        serviceDto.setCustomerId(service.getCustomer() == null ? null : service.getCustomer().getId());

        return serviceDto;
    }


    @Cacheable("Services")

    public List<ServiceDto> findAll() {
        final List<Service> services = serviceRepository.findAll(Sort.by("id"));
        return services.stream()
                .map((service) -> mapToDto(service,new ServiceDto()))
                .collect(Collectors.toList());
    }

    @Cacheable("CustomerServices")
    public List<ServiceDto> findAllByCustomer(final Integer customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException( String.format("Found no customer with id (%d) ", customerId ), ExceptionReasonMessageCodes.NOT_FOUND_ERROR));
        final List<Service> services = serviceRepository.findAllByCustomer(customer);
        return services.stream()
                .map((service) -> mapToDto(service,new ServiceDto()))
                .collect(Collectors.toList());
    }


    @Caching(evict = {
            @CacheEvict(value="CustomerServices", allEntries=true),
            @CacheEvict(value="Services", allEntries=true)})

    public void update(final Integer id, final ServiceDto serviceDto) {
        final Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException( String.format("Found no service with id (%d) ", serviceDto.getCustomerId() ), ExceptionReasonMessageCodes.NOT_FOUND_ERROR));
        mapToEntity(serviceDto,service);
        serviceRepository.save(service);
    }


}
