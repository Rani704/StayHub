package com.example.rani.stayhub.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.rani.stayhub.entity.Guest;
import com.example.rani.stayhub.entity.User;

public interface GuestRepository extends JpaRepository<Guest, Long> {
       List<Guest> findByUser(User user);
}
