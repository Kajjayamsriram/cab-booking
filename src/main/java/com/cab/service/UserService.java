package com.cab.service;

import com.cab.model.User;
import com.cab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    private User currentUser;
    
    private User getCurrentUser() {
        if (currentUser == null) {
            currentUser = userRepository.findByEmail("guest@example.com");
            if (currentUser == null) {
                currentUser = new User();
                currentUser.setName("Guest User");
                currentUser.setEmail("guest@example.com");
                currentUser.setPhone("+91 9876543210");
                currentUser.setPassword("guest123");
                currentUser.setRole("USER");
                currentUser.setCreatedAt(java.time.LocalDateTime.now());
                currentUser = userRepository.save(currentUser);
                System.out.println("✅ Default user created with ID: " + currentUser.getId());
            }
        }
        return currentUser;
    }
    
    public Map<String, Object> updateProfile(String name, String phone, String email) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = getCurrentUser();
            
            if (name != null && !name.trim().isEmpty()) {
                user.setName(name);
            }
            if (phone != null && !phone.trim().isEmpty()) {
                user.setPhone(phone);
            }
            if (email != null && !email.trim().isEmpty()) {
                user.setEmail(email);
            }
            
            userRepository.save(user);
            
            response.put("success", true);
            response.put("message", "Profile updated successfully!");
            response.put("name", user.getName());
            response.put("email", user.getEmail());
            response.put("phone", user.getPhone());
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to update profile: " + e.getMessage());
        }
        return response;
    }
    
    public Map<String, Object> getUserProfile() {
        Map<String, Object> response = new HashMap<>();
        User user = getCurrentUser();
        response.put("success", true);
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("phone", user.getPhone());
        response.put("role", user.getRole());
        return response;
    }
}
