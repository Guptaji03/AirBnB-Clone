package com.SpringBoot.AirBnB.service;

import com.SpringBoot.AirBnB.entity.Booking;

public interface CheckoutService {
    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);

}
