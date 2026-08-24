package com.example.rani.stayhub.service;

import java.util.List;
import com.example.rani.stayhub.dto.HotelDto;
import com.example.rani.stayhub.dto.HotelInfoDto;
import com.example.rani.stayhub.dto.HotelInfoRequestDto;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long id, HotelDto hotelDto);

    void deleteHotelById(Long id);

    void activateHotel(Long hotelId);

    HotelInfoDto getHotelInfoById(Long hotelId, HotelInfoRequestDto hotelInfoRequestDto);

    List<HotelDto> getAllHotels();
}
