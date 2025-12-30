package com.SpringBoot.AirBnB.service;

import com.SpringBoot.AirBnB.dto.BookingDto;
import com.SpringBoot.AirBnB.dto.BookingRequest;
import com.SpringBoot.AirBnB.dto.GuestDto;
import com.SpringBoot.AirBnB.entity.*;
import com.SpringBoot.AirBnB.entity.enums.BookingStatus;
import com.SpringBoot.AirBnB.exception.ResourceNotFoundException;
import com.SpringBoot.AirBnB.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class BookingServiceImpl  implements  BookingService{
      private final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
      private final HotelRepository hotelRepository;
      private final RoomRepository roomRepository;
      private final BookingRepository bookingRepository;
      private final InventoryRepository inventoryRepository;
      private final ModelMapper modelMapper;
      private final GuestRepository guestRepository;
      private final UserRepository userRepository;


    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequest bookingRequest) {
        log.info("Initialize booking for  hotel with id : {}, room : {}, date : {}- {} ",
                bookingRequest.getHotelId(),bookingRequest.getRoomId(), bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate());

        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId())
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found for id :- " + bookingRequest.getHotelId()));

        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(()-> new ResourceNotFoundException("Room not found with id :- " + bookingRequest.getRoomId()));

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory
                (bookingRequest.getRoomId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate(),bookingRequest.getRoomsCount());

        long numberOfdays = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate());

        if (inventoryList.size() != numberOfdays) {
            throw new IllegalStateException("Room is not available anymore");
        }

        for(Inventory inventory: inventoryList){
          inventory.setReservedCount(inventory.getReservedCount() + bookingRequest.getRoomsCount());
        }

        inventoryRepository.saveAll(inventoryList);

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .user(getCurrentUser())
                .roomsCount(bookingRequest.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();

        bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);


    }

    @Override
    @Transactional
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList) {
        log.info("Adding guest for booking id :- {}", bookingId );
     Booking booking = bookingRepository.findById(bookingId).orElseThrow(()->
             new IllegalStateException("Booking not found for id :- " + bookingId));

        if(booking.getBookingStatus() != BookingStatus.RESERVED) {
            throw new IllegalStateException("Booking is not under reserved state, cannot add guests");
        }
        if (hasBookingExpired(booking)) {
            throw new IllegalStateException("Booking has already expired");
        }

        for (GuestDto guestDto: guestDtoList) {
            Guest guest = modelMapper.map(guestDto, Guest.class);
            guest.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuest().add(guest);
        }

        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    public boolean hasBookingExpired(Booking booking) {
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser() {
        return userRepository.findById(1L).orElseGet(() -> {
            User user = new User();
            user.setEmail("test@example.com");
            user.setPassword("password");
            user.setName("Test User");
            return userRepository.save(user);
        });
    }
}
