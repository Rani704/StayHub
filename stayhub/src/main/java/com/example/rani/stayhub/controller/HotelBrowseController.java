package com.example.rani.stayhub.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.rani.stayhub.dto.HotelDto;
import com.example.rani.stayhub.dto.HotelSearchRequest;
import com.example.rani.stayhub.service.InventoryService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelDto>> searchHotel(@RequestBody HotelSearchRequest hotelSearchRequest) {

       Page<HotelDto> page = inventoryService.searchHotels(hotelSearchRequest);
       return ResponseEntity.ok(page);
    }
    
}
