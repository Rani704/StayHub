package com.example.rani.stayhub.service;

import org.springframework.data.domain.Page;

import com.example.rani.stayhub.dto.HotelDto;
import com.example.rani.stayhub.dto.HotelSearchRequest;
import com.example.rani.stayhub.entity.Room;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
