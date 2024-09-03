package com.example.roombooking.controller;

import com.example.roombooking.model.Booking;
import com.example.roombooking.model.Room;
import com.example.roombooking.model.RoomBooking;
import com.example.roombooking.model.User;
import com.example.roombooking.repository.RoomBookingRepository;
import com.example.roombooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/rooms")
public class UserController {
    private final UserService userService;

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    String name;
    String email;
    String password;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User newUser) {
        try {
            email = newUser.getEmail();
            name = newUser.getName();
            password = newUser.getPassword();

            User existingUser = userService.findByEmail(email);
            if (existingUser != null) {
                return new ResponseEntity<>("Forbidden, Account already exists", HttpStatus.FORBIDDEN);
            }

            newUser.setPassword(password);
            userService.save(newUser);

            return new ResponseEntity<>("Account Creation Successful", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User credentials) {
        try {
            String email = credentials.getEmail();
            String password = credentials.getPassword();

            User user = userService.findByEmail(email);
            if (user == null) {
                return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
            }

            if (!user.getPassword().equals(password)) {
                return new ResponseEntity<>("Username/Password Incorrect", HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>("Login Successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<User> userList = userService.findAll();

            List<Map<String, Object>> userDetailsList = new ArrayList<>();
            for (User user : userList) {
                Map<String, Object> userDetails = new HashMap<>();
                userDetails.put("name", user.getName());
                userDetails.put("userID", user.getUserID());
                userDetails.put("email", user.getEmail());
                userDetailsList.add(userDetails);
            }

            return new ResponseEntity<>(userDetailsList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUser(@RequestParam Long userID) {
        try {
            User user = UserService.findById(userID);
            if (user == null) {
                return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
            }

            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("name", user.getName());
            userDetails.put("userID", user.getUserID());
            userDetails.put("email", user.getEmail());

            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(Long userID) {
        try {
            User user = UserService.findById(userID);
            if (user == null) {
                Map<String , String> errorResponse = new LinkedHashMap<>();
                errorResponse.put("Error" , "User does not exist");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            } else {
                Optional<RoomBooking> bookings = roomBookingRepository.findById(userID);
                List<Map<String, Object>> response = bookings.stream()
                        .filter(booking -> booking.getDateOfBooking().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(LocalDate.now()) || booking.getDateOfBooking().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(LocalDate.now()))
                        .map(booking -> {
                            Map<String, Object> bookingMap = new HashMap<>();
                            Booking Room = new Booking();
                            bookingMap.put("roomName", Room.getRoomName());
                            bookingMap.put("roomID", Room.getRoomID());
                            bookingMap.put("bookingID", booking.getBookingID());
                            bookingMap.put("dateOfBooking", booking.getDateOfBooking());
                            bookingMap.put("timeFrom", booking.getTimeFrom());
                            bookingMap.put("timeTo", booking.getTimeTo());
                            bookingMap.put("purpose", booking.getPurpose());
                            return bookingMap;
                        }).collect(Collectors.toList());
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/upcoming")
    public ResponseEntity<Object> getUpcomingBookings(@RequestParam Long userID) {
        try {
            User user = UserService.findById(userID);
            if (user == null) {
                return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
            }

            List<RoomBooking> bookings = roomBookingRepository.findUpcomingByUserId(userID, new Date());
            if (bookings.isEmpty()) {
                return null;
            }

            List<Map<String, Object>> response = new ArrayList<>();
            for (RoomBooking booking : bookings) {
                Map<String, Object> bookingDetails = new HashMap<>();
                bookingDetails.put("roomName", booking.getRoomName());
                bookingDetails.put("roomID", booking.getRoomID());
                bookingDetails.put("bookingID", booking.getBookingID());
                bookingDetails.put("dateOfBooking", booking.getDateOfBooking());
                bookingDetails.put("timeFrom", booking.getTimeFrom());
                bookingDetails.put("timeTo", booking.getTimeTo());
                bookingDetails.put("purpose", booking.getPurpose());
                response.add(bookingDetails);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
