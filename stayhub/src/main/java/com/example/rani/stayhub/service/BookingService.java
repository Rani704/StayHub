package com.example.rani.stayhub.service;

import java.time.LocalDate;
import java.util.List;

import com.example.rani.stayhub.dto.BookingDto;
import com.example.rani.stayhub.dto.BookingRequest;
import com.example.rani.stayhub.dto.GuestDto;
import com.example.rani.stayhub.dto.HotelReportDto;
import com.example.rani.stayhub.entity.enums.BookingStatus;
import com.stripe.model.Event;
public interface BookingService {

    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<Long> guestIdList);

    String initiatePayments(Long bookingId);

    void capturePayment(Event event);

    void cancelBooking(Long bookingId);

    BookingStatus getBookingStatus(Long bookingId);

    List<BookingDto> getAllBookingsByHotelId(Long hotelId);

    HotelReportDto getHotelReport(Long hotelId, LocalDate startDate, LocalDate endDate);

    List<BookingDto> getMyBookings();
}