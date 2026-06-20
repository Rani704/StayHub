package com.example.rani.stayhub.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RoomDto {
    private Long id;
    private String type;
    private BigDecimal baseprice;
    private String[] photos;
    private String[] amenities;
    private Integer capacity;
    private Integer totalCount;
}
