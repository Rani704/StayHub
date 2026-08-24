package com.example.rani.stayhub.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.rani.stayhub.dto.HotelDto;
import com.example.rani.stayhub.dto.HotelInfoDto;
import com.example.rani.stayhub.dto.HotelPriceDto;
import com.example.rani.stayhub.dto.HotelPriceResponseDto;
import com.example.rani.stayhub.dto.HotelSearchRequest;
import com.example.rani.stayhub.service.HotelService;
import com.example.rani.stayhub.service.InventoryService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping("/search")
    @Operation(summary = "Search hotels", tags = { "Browse Hotels" })
    public ResponseEntity<Page<HotelPriceResponseDto>> searchHotel(@RequestBody HotelSearchRequest hotelSearchRequest) {

        var page = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}/info")
    @Operation(summary = "Get a hotel info by hotelId", tags = { "Browse Hotels" })
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId) {
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId, null));
    }

}
