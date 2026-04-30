// DriverController.java
package com.cab.controller;

import com.cab.model.Driver;
import com.cab.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/driver")
public class DriverController {
    
    @Autowired
    private DriverService driverService;
    
    @GetMapping("/dashboard")
    public String driverDashboard(Model model) {
        model.addAttribute("rides", driverService.getTodayRides());
        model.addAttribute("earnings", driverService.getTodayEarnings());
        return "driver-dashboard";
    }
    
    @PostMapping("/toggle-availability")
    public String toggleAvailability(@RequestParam Long driverId) {
        driverService.toggleAvailability(driverId);
        return "redirect:/driver/dashboard";
    }
    
    @GetMapping("/ride-requests")
    public String getRideRequests(Model model) {
        model.addAttribute("requests", driverService.getNearbyRideRequests());
        return "ride-requests";
    }
    
    @PostMapping("/accept-ride/{bookingId}")
    public String acceptRide(@PathVariable Long bookingId) {
        driverService.acceptRide(bookingId);
        return "redirect:/driver/dashboard";
    }
    
    @PostMapping("/complete-ride/{bookingId}")
    public String completeRide(@PathVariable Long bookingId) {
        driverService.completeRide(bookingId);
        return "redirect:/driver/dashboard";
    }
}
