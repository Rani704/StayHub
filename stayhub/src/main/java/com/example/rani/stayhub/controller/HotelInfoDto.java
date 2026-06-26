package com.example.rani.stayhub.controller;

import java.util.List;
import com.example.rani.stayhub.dto.HotelDto;
import com.example.rani.stayhub.dto.RoomDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelInfoDto {
    private HotelDto hotel;
    private List<RoomDto> rooms;
}
