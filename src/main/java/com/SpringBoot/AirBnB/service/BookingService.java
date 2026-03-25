package com.SpringBoot.AirBnB.service;

import com.SpringBoot.AirBnB.dto.BookingDto;
import com.SpringBoot.AirBnB.dto.BookingRequest;
import com.SpringBoot.AirBnB.dto.GuestDto;
import com.SpringBoot.AirBnB.dto.HotelReportDto;
import com.SpringBoot.AirBnB.entity.enums.BookingStatus;
import com.stripe.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<Long> guestIdList);

    String initiatePayments(Long bookingId);

    void capturePayment(Event event);

    void cancelBooking(Long bookingId);

    BookingStatus getBookingStatus(Long bookingId);

    List<BookingDto> getAllBookingsByHotelId(Long hotelId);

    HotelReportDto getHotelReport(Long hotelId, LocalDate startDate, LocalDate endDate);

    List<BookingDto> getMyBookings();
}
