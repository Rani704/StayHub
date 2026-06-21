package com.example.rani.stayhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rani.stayhub.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
