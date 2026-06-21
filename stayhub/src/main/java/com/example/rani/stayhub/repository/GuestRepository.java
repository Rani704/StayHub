package com.example.rani.stayhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.rani.stayhub.entity.Guest;

public interface GuestRepository extends JpaRepository<Guest, Long> {

}
