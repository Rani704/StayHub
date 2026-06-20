package com.example.rani.stayhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rani.stayhub.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {

}
