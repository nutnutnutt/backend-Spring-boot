package com.itsci.project65.service.impl;

import com.itsci.project65.model.Booking;
import com.itsci.project65.repository.BookingRepository;
import com.itsci.project65.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public Booking updateBooking(Booking booking) {
        Optional<Booking> optionalBooking = bookingRepository.findById(booking.getBookingId());
        if (optionalBooking.isPresent()) {
            Booking existingBooking = optionalBooking.get();
            existingBooking.setBookingstartDate(booking.getBookingstartDate());
            existingBooking.setBookingendDate(booking.getBookingendDate());
            existingBooking.setBookingchangeAddress(booking.getBookingchangeAddress());
            existingBooking.setBookingstatus(booking.getBookingstatus());
            existingBooking.setBookingList(booking.getBookingList());
            existingBooking.setFarmer(booking.getFarmer());
            existingBooking.setEquipmentList(booking.getEquipmentList());
            return bookingRepository.save(existingBooking);
        } else {
            throw new RuntimeException("ไม่พบข้อมูลการจองที่ต้องการแก้ไข (ID: " + booking.getBookingId() + ")");
        }
    }

    @Override
    public Booking getBookingById(int bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("ไม่พบข้อมูลการจองที่ต้องการค้นหา (ID: " + bookingId + ")"));
    }

    @Override
    public void deleteBooking(int bookingId) {
        Optional<Booking> existingBooking = bookingRepository.findById(bookingId);
        if (existingBooking.isPresent()) {
            bookingRepository.delete(existingBooking.get());
        } else {
            throw new RuntimeException("ไม่พบข้อมูลการจองที่ต้องการลบ (ID: " + bookingId + ")");
        }
    }
}

