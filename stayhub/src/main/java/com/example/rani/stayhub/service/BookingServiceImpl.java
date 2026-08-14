package com.example.rani.stayhub.service;

import com.example.rani.stayhub.repository.GuestRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.rani.stayhub.dto.BookingDto;
import com.example.rani.stayhub.dto.BookingRequest;
import com.example.rani.stayhub.dto.GuestDto;
import com.example.rani.stayhub.entity.Booking;
import com.example.rani.stayhub.entity.Guest;
import com.example.rani.stayhub.entity.Hotel;
import com.example.rani.stayhub.entity.Inventory;
import com.example.rani.stayhub.entity.Room;
import com.example.rani.stayhub.entity.User;
import com.example.rani.stayhub.entity.enums.BookingStatus;
import com.example.rani.stayhub.exception.ResourceNotFoundException;
import com.example.rani.stayhub.exception.UnAuthorisedException;
import com.example.rani.stayhub.repository.BookingRepository;
import com.example.rani.stayhub.repository.HotelRepository;
import com.example.rani.stayhub.repository.InventoryRepository;
import com.example.rani.stayhub.repository.RoomRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

        private final GuestRepository guestRepository;
        private final BookingRepository bookingRepository;
        private final HotelRepository hotelRepository;
        private final RoomRepository roomRepository;
        private final InventoryRepository inventoryRepository;
        private final ModelMapper modelMapper;

        @Override
        @Transactional
        public BookingDto initialiseBooking(BookingRequest bookingRequest) {

                log.info("Initialising booking for hotel : {} , room: {}, date : {}-{}", bookingRequest.getHotelId(),
                                bookingRequest.getRoomId(), bookingRequest.getCheckInDate(),
                                bookingRequest.getCheckOutDate());
                Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId()).orElseThrow(
                                () -> new ResourceNotFoundException(
                                                "Hotel not found with id: " + bookingRequest.getHotelId()));

                Room room = roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(
                                () -> new ResourceNotFoundException(
                                                "Room not found with id: " + bookingRequest.getRoomId()));

                List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(room.getId(),
                                bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(),
                                bookingRequest.getRoomsCount());

                long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),
                                bookingRequest.getCheckOutDate()) + 1;

                if (inventoryList.size() != daysCount) {
                        throw new IllegalStateException("Room is not available anymore");
                }
                // Reserved the room/ update the booked count of inventories

                for (Inventory inventory : inventoryList) {
                        inventory.setReservedCount(inventory.getReservedCount() + bookingRequest.getRoomsCount());
                }

                inventoryRepository.saveAll(inventoryList);

                // Create the Booking


                // TODO: calculate the dynamic amount

                Booking booking = Booking.builder()
                                .bookingStatus(BookingStatus.RESERVED)
                                .hotel(hotel)
                                .room(room)
                                .checkInDate(bookingRequest.getCheckInDate())
                                .checkOutDate(bookingRequest.getCheckOutDate())
                                .user(getCurrentUser())
                                .roomsCount(bookingRequest.getRoomsCount())
                                .amount(BigDecimal.TEN)
                                .build();

                booking = bookingRepository.save(booking);
                return modelMapper.map(booking, BookingDto.class);
        }

        @Override
        @Transactional
        public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtos) {
                log.info("Adding guests for booking with id: ", bookingId);
                Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                                () -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
                User user = getCurrentUser();
                if(user.equals(booking.getUser())){
                    throw new UnAuthorisedException("Booking does not belong to this user with id:" + user.getId());  
                }
                if (hasBookingExpire(booking)) {
                        throw new IllegalStateException("Booking has already expired");
                }

                if (booking.getBookingStatus() != BookingStatus.RESERVED) {
                        throw new IllegalStateException("Booking is not under reserved state, cannot add guests");
                }

                for (GuestDto guestDto : guestDtos) {
                        Guest guest = modelMapper.map(guestDto,Guest.class);
                        guest.setUser(user);
                        guest = guestRepository.save(guest);
                        booking.getGuest().add(guest);
                }
                booking.setBookingStatus(BookingStatus.GUEST_ADDED);
                booking = bookingRepository.save(booking);
                return modelMapper.map(booking, BookingDto.class);
        }

        public boolean hasBookingExpire(Booking booking) {
                return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
        }
         
        public User getCurrentUser(){
                return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
}
