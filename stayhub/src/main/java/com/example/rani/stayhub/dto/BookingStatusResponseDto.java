package com.example.rani.stayhub.dto;

import com.example.rani.stayhub.entity.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingStatusResponseDto {
 private BookingStatus bookingStatus;
}
