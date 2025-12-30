package com.SpringBoot.AirBnB.service;

import com.SpringBoot.AirBnB.dto.HotelDto;
import com.SpringBoot.AirBnB.dto.HotelInfoDto;
import com.SpringBoot.AirBnB.dto.RoomDto;
import com.SpringBoot.AirBnB.entity.Hotel;
import com.SpringBoot.AirBnB.entity.Room;
import com.SpringBoot.AirBnB.exception.ResourceNotFoundException;
import com.SpringBoot.AirBnB.repository.HotelRepository;
import com.SpringBoot.AirBnB.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HotelServiceImpl implements HotelService{
    Logger log = LoggerFactory.getLogger(HotelService.class);

    private final ModelMapper modelMapper;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
     log.info("Creating new Hotel with id :- {} and name :- {}", hotelDto.getId(), hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto,Hotel.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Hotel is created with id :- {}" , hotel.getId());
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
       log.info("Getting the hotel with id :- {} " , id);
       Hotel hotel = hotelRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id :- "+ id));
       return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
     log.info("Updating the Hotel with id :- {}", id);
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found while trying to  update hotel with id :- "+ id));
        modelMapper.map(hotelDto,hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel,HotelDto.class);

    }
    @Transactional
    @Override
    public void deleteHotelById(Long id) {
        log.info("Deleting the Hotel with id :- {}", id);
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found while trying to delete hotel with id :- "+ id));

        for(Room room: hotel.getRooms()){
            inventoryService.deleteFutureInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(id);
    }
    @Transactional
    @Override
    public void activateHotel(Long hotelId) {
        log.info("Activating the hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found while trying to active the hotel  with ID: "+hotelId));
        hotel.setActive(true);
        for(Room room: hotel.getRooms()){
             inventoryService.initializeRoomForAYear(room);
        }
    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        List<RoomDto> rooms = hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .toList();

        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class), rooms);
    }
}
