package com.example.rani.stayhub.dto;

import com.example.rani.stayhub.entity.Hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelPriceDto {
    private Hotel hotel;
    private Double price;
}
