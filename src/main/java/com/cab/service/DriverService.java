package com.cab.service;

import com.cab.model.Booking;
import com.cab.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DriverService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    public List<Booking> getTodayRides() {
        return bookingRepository.findByStatus("CONFIRMED");
    }
    
    public double getTodayEarnings() {
        List<Booking> completedRides = bookingRepository.findByStatus("COMPLETED");
        return completedRides.stream().mapToDouble(Booking::getFare).sum();
    }
    
    public void toggleAvailability(Long driverId) {
        System.out.println("Driver " + driverId + " availability toggled");
    }
    
    public List<Booking> getNearbyRideRequests() {
        return bookingRepository.findByStatus("PENDING");
    }
    
    public void acceptRide(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            booking.setStatus("CONFIRMED");
            bookingRepository.save(booking);
        }
    }
    
    public void completeRide(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            booking.setStatus("COMPLETED");
            bookingRepository.save(booking);
        }
    }
}
