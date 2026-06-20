package com.example.rani.stayhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rani.stayhub.entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long>{

}
