package com.example.rani.stayhub.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.rani.stayhub.entity.Inventory;
import com.example.rani.stayhub.entity.Room;


public interface InventoryRepository extends JpaRepository<Inventory,Long>{

    void deleteByDateAfterAndRoom(LocalDate date,Room room);
}
