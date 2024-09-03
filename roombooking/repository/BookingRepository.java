package com.example.roombooking.repository;

import com.example.roombooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findByBookingID(Long bookingID);
    Booking findByUserIDAndRoomIDAndDateOfBooking(Long userID, Long roomID, Date dateOfBooking);
    void deleteById(Long bookingID);
    List<Booking> findByRoomID(Long roomID);
}
