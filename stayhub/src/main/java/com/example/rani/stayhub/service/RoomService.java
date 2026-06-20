package com.example.rani.stayhub.service;

import java.util.List;

import com.example.rani.stayhub.dto.RoomDto;

public interface RoomService {

    RoomDto createNewRoom(Long hotelId ,RoomDto roomDto);

    List<RoomDto> getAllRoomsInHotel(Long hotelId);

    RoomDto getRoomById(Long roomId);

    void deleteRoomById(Long roomId);
}
