package com.SpringBoot.AirBnB.service;

import com.SpringBoot.AirBnB.dto.ProfileUpdateRequestDto;
import com.SpringBoot.AirBnB.dto.UserDto;
import com.SpringBoot.AirBnB.entity.User;

public interface UserService {

    User getUserById(Long id);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();

}
