package com.example.rani.stayhub.service;

import com.example.rani.stayhub.dto.ProfileUpdateRequestDto;
import com.example.rani.stayhub.dto.UserDto;
import com.example.rani.stayhub.entity.User;

public interface UserService {

    User getUserById(Long id);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();
}
