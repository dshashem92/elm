package com.example.elm.controller;

import com.example.elm.dto.ServiceDto;
import com.example.elm.service.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/service", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", maxAge = 3600)
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceDto>> getAllServices() {
        return ResponseEntity.ok(serviceService.findAll());
    }


    @PostMapping
    public ResponseEntity<Integer> createService(
            @RequestBody final ServiceDto serviceDto) {
        return new ResponseEntity<>(serviceService.create(serviceDto), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable final Integer id) {
        serviceService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("{customerId}")
    public ResponseEntity<List<ServiceDto>> getAllServicesByCustomerId(@PathVariable(value = "customerId") Integer customerId) {

        return new ResponseEntity<>(serviceService.findAllByCustomer(customerId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateService(@PathVariable final Integer id,
                                                         @RequestBody final ServiceDto serviceDto) {
        serviceService.update(id, serviceDto);
        return ResponseEntity.ok().build();
    }

}
