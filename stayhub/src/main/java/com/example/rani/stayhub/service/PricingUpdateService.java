package com.example.rani.stayhub.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.rani.stayhub.entity.Hotel;
import com.example.rani.stayhub.entity.HotelMinPrice;
import com.example.rani.stayhub.entity.Inventory;
import com.example.rani.stayhub.repository.HotelMinPriceRepository;
import com.example.rani.stayhub.repository.HotelRepository;
import com.example.rani.stayhub.repository.InventoryRepository;
import com.example.rani.stayhub.strategy.PricingService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PricingUpdateService {

    // Scheduler to update the inventory and HotelMinPrice tables every hour

    private final HotelRepository hotelRepository;
    private final InventoryRepository inventoryRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final PricingService pricingService;

    @Scheduled(cron = "0 0  * * * *") // Every hour
    public void updatePrices() {
        int page = 0;
        int batchSize = 100;

        while(true) {
            Page<Hotel> hotelPage = hotelRepository.findAll(PageRequest.of(page,batchSize));
            if (hotelPage.isEmpty()) {
                break;
            }
            hotelPage.getContent().forEach(hotel -> updateHotelPrice(hotel));
            
            page++;
        }
    }

    private void updateHotelPrice(Hotel hotel) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusYears(1);

        List<Inventory> InventoryList = inventoryRepository.findByHotelAndDateBetween(hotel, startDate, endDate);
    
        updateInventoryPrice(InventoryList);

        updateHotelMinPrice(hotel, InventoryList,startDate, endDate);
    }

    private void updateHotelMinPrice(Hotel hotel, List<Inventory> inventoryList, LocalDate startDate,
            LocalDate endDate) {
     //Compute minimum price per day for the hotel
     Map<LocalDate, BigDecimal> dailyMinPrices = inventoryList.stream()
             .collect(Collectors.groupingBy(
                    Inventory::getDate,
                    Collectors.mapping(Inventory::getPrice, Collectors.minBy(Comparator.naturalOrder()))
             ))
             .entrySet().stream()
             .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().orElse(BigDecimal.ZERO)));

    //Prepare HotelPrice entities fin bulk
    List<HotelMinPrice> hotelPrices = new ArrayList<>();
    dailyMinPrices.forEach((date, price) -> {
        HotelMinPrice hotelPrice = hotelMinPriceRepository.findByHotelAndDate(hotel, date)
                .orElse(new HotelMinPrice(hotel,date));
        hotelPrice.setPrice(price);
        hotelPrices.add(hotelPrice);     
    });

      //Save all the HotelPrice entities in bulk
      hotelMinPriceRepository.saveAll(hotelPrices);     
    }
    private void updateInventoryPrice(List<Inventory> inventoryList) {
        inventoryList.forEach(inventory -> {
           BigDecimal dynamicPrice = pricingService.calculateDynamicPricing(inventory);
           inventory.setPrice(dynamicPrice);
        });
        inventoryRepository.saveAll(inventoryList);
    }
}
