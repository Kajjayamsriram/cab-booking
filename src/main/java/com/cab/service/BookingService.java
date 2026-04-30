package com.cab.service;

import com.cab.model.Booking;
import com.cab.model.User;
import com.cab.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookingService {
    
    @Autowired
    private BookingRepository bookingRepository;
    
    private Map<String, double[]> locationCoordinates = new HashMap<>();
    
    public BookingService() {
        locationCoordinates.put("Airport", new double[]{28.5562, 77.1000});
        locationCoordinates.put("Railway Station", new double[]{28.6611, 77.2273});
        locationCoordinates.put("City Center", new double[]{28.6139, 77.2090});
    }
    
    public double calculateDistance(String pickup, String drop) {
        return Math.random() * 30 + 5;
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
        
        return fare;
    }
    
    public Booking createBooking(Booking booking) {
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus("PENDING");
        // Create a dummy user for now
        User dummyUser = new User();
        dummyUser.setId(1L);
        dummyUser.setName("John Doe");
        dummyUser.setEmail("john@example.com");
        dummyUser.setPhone("1234567890");
        booking.setUser(dummyUser);
        return bookingRepository.save(booking);
    }
    
    public List<Booking> getUserBookings() {
        return bookingRepository.findByUserIdOrderByBookingTimeDesc(1L);
    }
    
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }
    
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow();
    }
}
