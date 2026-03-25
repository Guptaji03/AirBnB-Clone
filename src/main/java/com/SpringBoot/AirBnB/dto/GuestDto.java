package com.SpringBoot.AirBnB.dto;

import com.SpringBoot.AirBnB.entity.User;
import com.SpringBoot.AirBnB.entity.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GuestDto {
    private Long id;
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
}
