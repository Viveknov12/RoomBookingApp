package com.example.roombooking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class RoomBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookingID;
    private Long roomID;
    private String room;
    private Date dateOfBooking;
    private String timeFrom;
    private String timeTo;
    private String purpose;
    private Long userID;
    private String roomName;
    public RoomBooking() {}
    // getters
    public Long getBookingID() {
        return bookingID;
    }

    public Long getRoomID() {
        return roomID;
    }

    public String getRoom() {
        return room;
    }

    public Date getDateOfBooking() {
        return dateOfBooking;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public String getPurpose() {
        return purpose;
    }

    public Long getUserID() {
        return userID;
    }

    // setters
    public void setBookingID(Long bookingID) {
        this.bookingID = bookingID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setDateOfBooking(Date dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Object getRoomName() {
        return roomName;
    }
}