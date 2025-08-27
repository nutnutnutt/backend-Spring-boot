package com.itsci.project65.service;

import com.itsci.project65.model.Booking;

public interface BookingService {
    public Booking createBooking(Booking booking);
    public Booking updateBooking(Booking booking);
    public Booking getBookingById(int bookingId);
    public void deleteBooking(int bookingId);
}

