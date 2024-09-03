package com.example.roombooking.service;

import com.example.roombooking.model.Booking;
import com.example.roombooking.model.RoomBooking;
import com.example.roombooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Booking findByBookingID(Long bookingID) {
        return
                bookingRepository.findByBookingID(bookingID);
    }

    public Booking findByUserIDAndRoomIDAndDateOfBooking(Long userID, Long roomID, Date dateOfBooking) {
        return bookingRepository.findByUserIDAndRoomIDAndDateOfBooking(userID, roomID, dateOfBooking);
    }

    public Booking save(Booking booking) {
        return
                bookingRepository.save(booking);
    }

    public void deleteById(Long bookingID) {

        bookingRepository.deleteById(bookingID);
    }
    public Booking bookRoom(RoomBooking roomBooking) {
        Booking existingBooking = bookingRepository.findByUserIDAndRoomIDAndDateOfBooking(
                roomBooking.getUserID(), roomBooking.getRoomID(), roomBooking.getDateOfBooking());
        if (existingBooking == null) {
            Booking newBooking = new Booking();
            newBooking.setUserID(roomBooking.getUserID());
            newBooking.setRoomID(roomBooking.getRoomID());
            newBooking.setDateOfBooking(roomBooking.getDateOfBooking());
            newBooking.setTimeFrom(roomBooking.getTimeFrom());
            newBooking.setTimeTo(roomBooking.getTimeTo());
            newBooking.setPurpose(roomBooking.getPurpose());
            return bookingRepository.save(newBooking);
        }
        return null;
    }
}