package com.example.elm.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerDto {
    private Integer id;
    private String nationalId;
    private String name;
    private LocalDate dob;
    private String email;
    private String mobile;

}
