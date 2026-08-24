package com.example.rani.stayhub.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.rani.stayhub.dto.HotelPriceResponseDto;
import com.example.rani.stayhub.dto.HotelSearchRequest;
import com.example.rani.stayhub.dto.InventoryDto;
import com.example.rani.stayhub.dto.UpdateInventoryRequestDto;
import com.example.rani.stayhub.entity.Room;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelPriceResponseDto> searchHotels(HotelSearchRequest hotelSearchRequest);

    List<InventoryDto> getAllInventoryByRoom(Long roomId);

    void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryRequestDto);
}
