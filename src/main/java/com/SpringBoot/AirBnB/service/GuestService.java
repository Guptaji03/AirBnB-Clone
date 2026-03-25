package com.SpringBoot.AirBnB.service;

import com.SpringBoot.AirBnB.dto.GuestDto;
import com.SpringBoot.AirBnB.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface GuestService{
    List<GuestDto> getAllGuests();

    void updateGuest(Long guestId, GuestDto guestDto);

    void deleteGuest(Long guestId);

    GuestDto addNewGuest(GuestDto guestDto);
}
