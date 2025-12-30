package com.SpringBoot.AirBnB.service;

import com.SpringBoot.AirBnB.dto.HotelDto;
import com.SpringBoot.AirBnB.dto.HotelSearchRequest;
import com.SpringBoot.AirBnB.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteFutureInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);

}
