package com.example.rani.stayhub.service;

import java.util.List;

import com.example.rani.stayhub.dto.BookingDto;
import com.example.rani.stayhub.dto.BookingRequest;
import com.example.rani.stayhub.dto.GuestDto;

public interface BookingService {
   BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}
