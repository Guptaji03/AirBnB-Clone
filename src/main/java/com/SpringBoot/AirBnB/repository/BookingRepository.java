package com.SpringBoot.AirBnB.repository;

import com.SpringBoot.AirBnB.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
