package com.cab.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private User user;
    
    @ManyToOne
    private Driver driver;
    
    private String pickupLocation;
    private String dropLocation;
    private double distance;
    private double fare;
    private String cabType;
    private LocalDateTime bookingTime;
    private LocalDateTime pickupTime;
    private String status;
    
    // Default constructor
    public Booking() {}
    
    // Parameterized constructor
    public Booking(User user, String pickupLocation, String dropLocation, String cabType) {
        this.user = user;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.cabType = cabType;
        this.status = "PENDING";
        this.bookingTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Driver getDriver() {
        return driver;
    }
    
    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    
    public String getPickupLocation() {
        return pickupLocation;
    }
    
    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
    
    public String getDropLocation() {
        return dropLocation;
    }
    
    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }
    
    public double getDistance() {
        return distance;
    }
    
    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    public double getFare() {
        return fare;
    }
    
    public void setFare(double fare) {
        this.fare = fare;
    }
    
    public String getCabType() {
        return cabType;
    }
    
    public void setCabType(String cabType) {
        this.cabType = cabType;
    }
    
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
    
    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
    
    public LocalDateTime getPickupTime() {
        return pickupTime;
    }
    
    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
