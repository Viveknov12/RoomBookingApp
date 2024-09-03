package com.example.roombooking.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Booking {
    private Long userID;
    private Long roomID;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingID;
    private Date dateOfBooking;
    private String timeFrom;
    private String timeTo;
    private String purpose;
    private String roomName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Booking(Long userID, Long roomID) {
        this.userID = userID;
        this.roomID = roomID;
    }

    public Booking(Long bookingID, User user, Room room, Date dateOfBooking, String timeFrom, String timeTo, String purpose, String roomName) {
        this.bookingID = bookingID;
        this.user = user;
        this.room = room;
        this.dateOfBooking = dateOfBooking;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.purpose = purpose;
        this.roomName = roomName;
    }


    public Booking() { }

    public Long getBookingID() {
        return bookingID;
    }
    public void setBookingID(Long bookingID) {
        this.bookingID = bookingID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }

    public Date getDateOfBooking() {
        return dateOfBooking;
    }

    public void setDateOfBooking(Date dateOfBooking) {
        this.dateOfBooking = dateOfBooking;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}