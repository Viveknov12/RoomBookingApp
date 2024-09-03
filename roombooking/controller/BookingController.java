package com.example.roombooking.controller;
import com.example.roombooking.model.Booking;
import com.example.roombooking.model.Room;
import com.example.roombooking.model.User;
import com.example.roombooking.service.BookingService;
import com.example.roombooking.service.RoomService;
import com.example.roombooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
        try {
            // Check if user exists
            User user = userService.findById(booking.getUserID());
            if (user == null) {
                return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
            }

            // Check if room exists
            Room room = roomService.findById(booking.getRoomID());
            if (room == null) {
                return new ResponseEntity<>("Room does not exist", HttpStatus.BAD_REQUEST);
            }
            // Check if the date/time is valid
            if (booking.getDateOfBooking() == null || booking.getTimeFrom() == null || booking.getTimeTo() == null) {
                return new ResponseEntity<>("Invalid date/time", HttpStatus.BAD_REQUEST);
            }

            // Check if the room is available
            if (!roomService.isRoomAvailable(booking.getRoomID(), booking.getDateOfBooking())) {
                return new ResponseEntity<>("Room unavailable", HttpStatus.BAD_REQUEST);
            }



            // Create the booking
            bookingService.save(booking);

            return new ResponseEntity<>("Booking created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping
    public ResponseEntity<String> editBooking(@RequestBody Booking booking) {
        try {
            // Check if user exists
            User user = userService.findById(booking.getUserID());
            if (user == null) {
                return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
            }

            // Check if room exists
            Room room = roomService.findById(booking.getRoomID());
            if (room == null) {
                return new ResponseEntity<>("Room does not exist", HttpStatus.BAD_REQUEST);
            }

            // Check if booking exists
            Booking existingBooking = bookingService.findByBookingID(booking.getBookingID());
            if (existingBooking == null) {
                return new ResponseEntity<>("Booking does not exist", HttpStatus.NOT_FOUND);
            }

            // Check if the date/time is valid
            if (booking.getDateOfBooking() == null || booking.getTimeFrom() == null || booking.getTimeTo() == null) {
                return new ResponseEntity<>("Invalid date/time", HttpStatus.BAD_REQUEST);
            }

            // Check if the room is available
            if (!roomService.isRoomAvailable(booking.getRoomID(), booking.getDateOfBooking())) {
                return new ResponseEntity<>("Room unavailable", HttpStatus.BAD_REQUEST);
            }

            // Update the booking
            existingBooking.setUserID(booking.getUserID());
            existingBooking.setRoomID(booking.getRoomID());
            existingBooking.setDateOfBooking(booking.getDateOfBooking());
            existingBooking.setTimeFrom(booking.getTimeFrom());
            existingBooking.setTimeTo(booking.getTimeTo());
            existingBooking.setPurpose(booking.getPurpose());
            bookingService.save(existingBooking);

            return new ResponseEntity<>("Booking modified successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteBooking(@RequestParam Long bookingID) {
        try {
            Booking existingBooking = bookingService.findByBookingID(bookingID);
            if (existingBooking == null) {
                return new ResponseEntity<>("Booking does not exist", HttpStatus.NOT_FOUND);
            }

            bookingService.deleteById(bookingID);
            return new ResponseEntity<>("Booking deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
