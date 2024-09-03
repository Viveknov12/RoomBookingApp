package com.example.roombooking.repository;

import com.example.roombooking.model.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Date;
import java.util.List;

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {
    List<RoomBooking> findByUserID(Long userID);

    @Query("SELECT rb FROM RoomBooking rb WHERE rb.userID = :userId AND rb.dateOfBooking >= :today")
    List<RoomBooking> findUpcomingByUserId(Long userId, Date today);
}
