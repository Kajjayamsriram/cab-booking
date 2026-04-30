package com.cab.service;

import com.cab.model.Booking;
import com.cab.model.User;
import com.cab.repository.BookingRepository;
import com.cab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private Random random = new Random();
    private User defaultUser;
    
    private User getDefaultUser() {
        if (defaultUser == null) {
            defaultUser = userRepository.findByEmail("guest@example.com");
            if (defaultUser == null) {
                defaultUser = new User();
                defaultUser.setName("Guest User");
                defaultUser.setEmail("guest@example.com");
                defaultUser.setPhone("9999999999");
                defaultUser.setPassword("guest123");
                defaultUser.setRole("USER");
                defaultUser.setCreatedAt(LocalDateTime.now());
                defaultUser = userRepository.save(defaultUser);
                System.out.println("✅ Default user created with ID: " + defaultUser.getId());
            }
        }
        return defaultUser;
    }
    
    public double calculateDistance(String pickup, String drop) {
        return 5 + random.nextDouble() * 30;
    }
    
    public double calculateFare(double distance, String cabType) {
        Map<String, Double> rates = Map.of(
            "Mini", 8.0,
            "Sedan", 12.0,
            "SUV", 15.0,
            "Luxury", 20.0
        );
        
        double baseFare = 50;
        double perKmRate = rates.getOrDefault(cabType, 10.0);
        double fare = baseFare + (distance * perKmRate);
        
        int hour = LocalDateTime.now().getHour();
        if ((hour >= 8 && hour <= 10) || (hour >= 17 && hour <= 19)) {
            fare *= 1.2;
        }
        
        return Math.round(fare);
    }
    
    public Booking createBooking(Booking booking) {
        User user = getDefaultUser();
        
        double distance = calculateDistance(booking.getPickupLocation(), booking.getDropLocation());
        double fare = calculateFare(distance, booking.getCabType());
        
        booking.setDistance(distance);
        booking.setFare(fare);
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus("PENDING");
        booking.setUser(user);
        
        return bookingRepository.save(booking);
    }
    
    public List<Booking> getUserBookings() {
        User user = getDefaultUser();
        return bookingRepository.findByUserIdOrderByBookingTimeDesc(user.getId());
    }
    
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null && booking.getStatus().equals("PENDING")) {
            booking.setStatus("CANCELLED");
            bookingRepository.save(booking);
            System.out.println("❌ Ride " + id + " cancelled");
        }
    }
    
    public void completeRide(Long id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null && (booking.getStatus().equals("CONFIRMED") || booking.getStatus().equals("IN_PROGRESS") || booking.getStatus().equals("PENDING"))) {
            booking.setStatus("COMPLETED");
            bookingRepository.save(booking);
            System.out.println("✅ Ride " + id + " completed!");
        }
    }
    
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }
}
