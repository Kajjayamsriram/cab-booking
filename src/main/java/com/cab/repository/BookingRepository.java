package com.cab.repository;

import com.cab.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId ORDER BY b.bookingTime DESC")
    List<Booking> findByUserIdOrderByBookingTimeDesc(@Param("userId") Long userId);
    
    List<Booking> findByStatus(String status);
}
