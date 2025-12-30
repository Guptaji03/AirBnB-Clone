package com.SpringBoot.AirBnB.repository;

import com.SpringBoot.AirBnB.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest,Long> {
}
