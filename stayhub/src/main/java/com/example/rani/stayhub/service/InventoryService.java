package com.example.rani.stayhub.service;

import com.example.rani.stayhub.entity.Room;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteFutureInventories(Room room);
}
