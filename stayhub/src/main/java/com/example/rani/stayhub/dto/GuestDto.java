package com.example.rani.stayhub.dto;

import com.example.rani.stayhub.entity.User;
import com.example.rani.stayhub.entity.enums.Gender;
import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
