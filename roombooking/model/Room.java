package com.example.roombooking.model;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomID;
    private String roomName;
    private Integer roomCapacity;

    public Room() {}

    // getters
    public Long getRoomID() {
        return roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public Integer getRoomCapacity() {
        return roomCapacity;
    }

    // setters
    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setRoomCapacity(Integer roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public Long getBookingID() {
        return roomID;
    }
}