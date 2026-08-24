package com.example.rani.stayhub.dto;

import java.time.LocalDate;
import com.example.rani.stayhub.entity.enums.Gender;

import lombok.Data;

@Data
public class ProfileUpdateRequestDto {
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;
}
