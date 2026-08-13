package com.example.rani.stayhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.rani.stayhub.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel,Long> {

}
