package com.SpringBoot.AirBnB.service;

import com.SpringBoot.AirBnB.dto.HotelDto;
import com.SpringBoot.AirBnB.entity.Room;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteFutureInventories(Room room);

}
