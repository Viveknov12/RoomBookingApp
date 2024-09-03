package com.example.roombooking.service;

import com.example.roombooking.model.Booking;
import com.example.roombooking.model.Room;
import com.example.roombooking.repository.BookingRepository;
import com.example.roombooking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public Room findByRoomName(String roomName) {
        return roomRepository.findByRoomName(roomName);
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public  Room findById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    public boolean isRoomAvailable(Long roomId, Date date) {
        Booking booking = bookingRepository.findByUserIDAndRoomIDAndDateOfBooking(null, roomId, date);
        return booking == null;
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public List<Room> filterByCapacity(List<Room> rooms, int capacity) {
        return rooms.stream()
                .filter(room -> room.getRoomCapacity() >= capacity)
                .collect(Collectors.toList());
    }
    public static Booking getBooking(Long bookingId, BookingRepository bookingRepository) {
        return bookingRepository.findById(bookingId).orElse(null);
    }






}