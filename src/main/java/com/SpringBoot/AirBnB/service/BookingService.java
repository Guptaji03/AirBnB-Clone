package com.SpringBoot.AirBnB.service;

import com.SpringBoot.AirBnB.dto.BookingDto;
import com.SpringBoot.AirBnB.dto.BookingRequest;
import com.SpringBoot.AirBnB.dto.GuestDto;

import java.util.List;

public interface BookingService {

    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}
