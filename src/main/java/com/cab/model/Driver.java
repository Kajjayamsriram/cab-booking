// Driver.java
package com.cab.model;

import jakarta.persistence.*;

@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String phone;
    private String licenseNumber;
    private double rating;
    @OneToOne
    private Cab assignedCab;
    private boolean available;
    
    // Getters and Setters
}
