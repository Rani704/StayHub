package com.example.rani.stayhub.dto;

import java.time.LocalDate;

import com.example.rani.stayhub.entity.enums.Gender;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
}
