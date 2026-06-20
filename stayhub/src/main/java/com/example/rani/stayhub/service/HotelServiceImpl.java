package com.example.rani.stayhub.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.rani.stayhub.dto.HotelDto;
import com.example.rani.stayhub.entity.Hotel;
import com.example.rani.stayhub.exception.ResourceNotFoundException;
import com.example.rani.stayhub.repository.HotelRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    
    @Override
    public HotelDto createNewHotel(HotelDto hotelDto){
        log.info("Creating a new hotel with name:{}",hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto , Hotel.class);
        hotel.setActive(false);
        log.info("Created a new hotel with ID: {}",hotelDto.getId());
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id){
       log.info("Getting the hotel with ID: {}",id);
       Hotel hotel = hotelRepository
       .findById(id)
       .orElseThrow(()->new ResourceNotFoundException("Hotel not found with ID: "+id));
       return modelMapper.map(hotel,HotelDto.class);
    }
    
    @Override
    public HotelDto updateHotelById(Long id , HotelDto hotelDto){
        log.info("Updating the hotel with ID: {}",id);
        Hotel hotel = hotelRepository
       .findById(id)
       .orElseThrow(()->new ResourceNotFoundException("Hotel not found with ID: "+id));
       modelMapper.map(hotelDto,hotel);
       hotel.setId(id);
       hotel = hotelRepository.save(hotel);
       return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public void deleteHotelById(Long id){
        boolean exists = hotelRepository.existsById(id);
        if(!exists) throw new ResourceNotFoundException("Hotel not found with ID: "+id);

        hotelRepository.deleteById(id);
        //delete the future inventories for this hotel
        
    }
}
