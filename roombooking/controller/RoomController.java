package com.example.roombooking.controller;

import com.example.roombooking.model.Booking;
import com.example.roombooking.model.Room;
import com.example.roombooking.repository.BookingRepository;
import com.example.roombooking.service.RoomService;
import com.example.roombooking.service.UserService;
import com.example.roombooking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping
    public ResponseEntity<Object> getRooms(@RequestParam(required = false) Integer capacity) {
        try {
            List<Room> rooms;

            if (capacity != null && capacity <= 0) {
                return new ResponseEntity<>("Invalid parameters", HttpStatus.BAD_REQUEST);
            } else if (capacity != null) {
                rooms = roomService.filterByCapacity(roomService.findAll(), capacity);
            } else {
                rooms = roomService.findAll();
            }
            List<Map<String, Object>> response = new ArrayList<>();
            for (Room room : rooms) {
                Map<String, Object> roomObject = new HashMap<>();
                roomObject.put("roomID", room.getRoomID());
                roomObject.put("roomName", room.getRoomName());
                roomObject.put("capacity", room.getRoomCapacity());

                Long bookingID = room.getBookingID();
                Booking booking = RoomService.getBooking(bookingID, bookingRepository);
                if (booking != null) {
                    Map<String, Object> booked = new HashMap<>();
                    booked.put("bookingID", booking.getBookingID());
                    booked.put("dateOfBooking", booking.getDateOfBooking());
                    booked.put("timeFrom", booking.getTimeFrom());
                    booked.put("timeTo", booking.getTimeTo());
                    booked.put("purpose", booking.getPurpose());

                    User user = UserService.findById(booking.getUserID());
                    if (user != null) {
                        Map<String, Object> userObject = new HashMap<>();
                        userObject.put("userID", user.getUserID());
                        booked.put("user", userObject);
                    }

                    roomObject.put("booked", booked);
                }

                response.add(roomObject);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> addRoom(@RequestBody Room room) {
        try {
            if (room.getRoomCapacity() <= 0) {
                return new ResponseEntity<>("Invalid capacity", HttpStatus.BAD_REQUEST);
            }

            Room existingRoom = roomService.findByRoomName(room.getRoomName());
            if (existingRoom != null) {
                return new ResponseEntity<>("Room already exists", HttpStatus.BAD_REQUEST);
            }

            roomService.save(room);
            return new ResponseEntity<>("Room created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping
    public ResponseEntity<String> editRoom(@RequestBody Room room) {
        try {
            Room existingRoom = roomService.findById(room.getRoomID());
            if (existingRoom == null) {
                return new ResponseEntity<>("Room does not exist", HttpStatus.NOT_FOUND);
            }

            Room roomWithSameName = roomService.findByRoomName(room.getRoomName());
            if (roomWithSameName != null && !roomWithSameName.getRoomID().equals(room.getRoomID())) {
                return new ResponseEntity<>("Room with given name already exists", HttpStatus.BAD_REQUEST);
            }

            if (room.getRoomCapacity() != null && room.getRoomCapacity() <= 0) {
                return new ResponseEntity<>("Invalid capacity", HttpStatus.BAD_REQUEST);
            }

            existingRoom.setRoomName(room.getRoomName());
            existingRoom.setRoomCapacity(room.getRoomCapacity());
            roomService.save(existingRoom);

            return new ResponseEntity<>("Room edited successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteRoom(@RequestParam Long roomID) {
        try {
            Room existingRoom = roomService.findById(roomID);
            if (existingRoom == null) {
                return new ResponseEntity<>("Room does not exist", HttpStatus.NOT_FOUND);
            }

            roomService.deleteById(roomID);
            return new ResponseEntity<>("Room deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}