package com.example.rani.stayhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.rani.stayhub.entity.Hotel;
import com.example.rani.stayhub.entity.User;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
     List<Hotel> findByOwner(User user);
}
