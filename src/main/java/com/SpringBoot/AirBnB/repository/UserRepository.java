package com.SpringBoot.AirBnB.repository;

import com.SpringBoot.AirBnB.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}