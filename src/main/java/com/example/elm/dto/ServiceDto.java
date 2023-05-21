package com.example.elm.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceDto {

    private Integer id;

    private String title;

    private String description;

    private Integer duration;

    private Double cost;

    private Integer customerId;


}
