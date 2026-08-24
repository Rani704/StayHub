package com.example.rani.stayhub.service;

import com.example.rani.stayhub.entity.Booking;

public interface CheckoutService {

    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);
    

}
