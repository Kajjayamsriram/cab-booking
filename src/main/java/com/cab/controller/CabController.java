package com.cab.controller;

import com.cab.model.Booking;
import com.cab.service.BookingService;
import com.cab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CabController {
    
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("bookings", bookingService.getUserBookings());
        return "dashboard";
    }
    
    @PostMapping("/api/book")
    @ResponseBody
    public Map<String, Object> createBooking(@RequestBody Booking booking) {
        Map<String, Object> response = new HashMap<>();
        try {
            Booking saved = bookingService.createBooking(booking);
            response.put("success", true);
            response.put("booking", saved);
            response.put("message", "Booking confirmed!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }
    
    @PostMapping("/api/calculate-fare")
    @ResponseBody
    public Map<String, Object> calculateFare(@RequestParam String pickup, 
                                              @RequestParam String drop,
                                              @RequestParam String cabType) {
        Map<String, Object> response = new HashMap<>();
        double distance = bookingService.calculateDistance(pickup, drop);
        double fare = bookingService.calculateFare(distance, cabType);
        response.put("distance", Math.round(distance * 10) / 10.0);
        response.put("fare", fare);
        return response;
    }
    
    @GetMapping("/api/bookings")
    @ResponseBody
    public List<Booking> getBookings() {
        return bookingService.getUserBookings();
    }
    
    @PostMapping("/api/cancel/{id}")
    @ResponseBody
    public Map<String, Object> cancelBooking(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        bookingService.cancelBooking(id);
        response.put("success", true);
        response.put("message", "Booking cancelled");
        return response;
    }
    
    @PostMapping("/api/complete/{id}")
    @ResponseBody
    public Map<String, Object> completeRide(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            bookingService.completeRide(id);
            response.put("success", true);
            response.put("message", "Ride completed successfully!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to complete ride: " + e.getMessage());
        }
        return response;
    }
    
    @GetMapping("/api/profile")
    @ResponseBody
    public Map<String, Object> getProfile() {
        return userService.getUserProfile();
    }
    
    @PostMapping("/api/profile/update")
    @ResponseBody
    public Map<String, Object> updateProfile(@RequestParam(required = false) String name,
                                               @RequestParam(required = false) String phone,
                                               @RequestParam(required = false) String email) {
        return userService.updateProfile(name, phone, email);
    }
}
