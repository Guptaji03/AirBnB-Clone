package com.SpringBoot.AirBnB.dto;

import com.SpringBoot.AirBnB.entity.User;
import com.SpringBoot.AirBnB.entity.enums.Gender;
import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
