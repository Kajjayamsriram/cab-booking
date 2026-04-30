// Cab.java
package com.cab.model;

import jakarta.persistence.*;

@Entity
public class Cab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String cabNumber;
    private String model;
    private String type; // Sedan, SUV, Hatchback
    private int capacity;
    private double baseFare;
    private double perKmRate;
    private boolean available;
    
    // Getters and Setters
}
