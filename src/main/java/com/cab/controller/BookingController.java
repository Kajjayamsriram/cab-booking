// BookingController.java
package com.cab.controller;

import com.cab.model.Booking;
import com.cab.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/booking")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;
    
    @GetMapping("/new")
    public String showBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("cabTypes", getCabTypes());
        return "booking-form";
    }
    
    @PostMapping("/calculate-fare")
    @ResponseBody
    public Map<String, Object> calculateFare(@RequestParam String pickup, 
                                              @RequestParam String drop,
                                              @RequestParam String cabType) {
        Map<String, Object> response = new HashMap<>();
        double distance = bookingService.calculateDistance(pickup, drop);
        double fare = bookingService.calculateFare(distance, cabType);
        response.put("distance", distance);
        response.put("fare", fare);
        return response;
    }
    
    @PostMapping("/create")
    public String createBooking(@ModelAttribute Booking booking, Model model) {
        Booking savedBooking = bookingService.createBooking(booking);
        model.addAttribute("booking", savedBooking);
        return "booking-success";
    }
    
    @GetMapping("/my-bookings")
    public String getUserBookings(Model model) {
        List<Booking> bookings = bookingService.getUserBookings();
        model.addAttribute("bookings", bookings);
        return "my-bookings";
    }
    
    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return "redirect:/booking/my-bookings";
    }
    
    @GetMapping("/track/{id}")
    public String trackBooking(@PathVariable Long id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        return "track-booking";
    }
    
    private Map<String, Object> getCabTypes() {
        Map<String, Object> cabTypes = new HashMap<>();
        Map<String, Double> rates = new HashMap<>();
        rates.put("Mini", 8.0);
        rates.put("Sedan", 12.0);
        rates.put("SUV", 15.0);
        rates.put("Luxury", 20.0);
        cabTypes.put("rates", rates);
        return cabTypes;
    }
}
