package com.example.emsss.model;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import com.example.emsss.DB.Database;
public class Room {




    public enum Status {
        AVAILABLE, UNAVAILABLE
    }
    private int currentAttendeeNo;
    private static int nextRoomNumber = 1; // Static counter for assigning unique IDs
    private int roomNumber;    private int capacity;
    private String location;
    private Status status;

    private List<String> attendeeUsernames = new ArrayList<>();
    private List<String> occupiedDates = new ArrayList<>();


    // getter for attendeeUsernames
    public List<String> getAttendeeUsernames() {
        return attendeeUsernames;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void addAttendee(String username) {
        attendeeUsernames.add(username);
        setCurrentAttendeeNo(getCurrentAttendeeNo()+1);
    }
    public void removeDate(LocalDate date) {
        occupiedDates.remove(date);
        if (occupiedDates.isEmpty()) {
            this.status = Status.AVAILABLE;
        }
    }
    public int getCurrentAttendeeNo() {
        return currentAttendeeNo;
    }

    public void setCurrentAttendeeNo(int currentAttendeeNo) {
        this.currentAttendeeNo = currentAttendeeNo;
    }

    public Room(int capacity, String location) {
        this.roomNumber = nextRoomNumber++;
        this.capacity = capacity;
        this.location = location;
        this.status = Status.AVAILABLE;
    }

    public Room() {
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", capacity=" + capacity +
                ", location='" + location + '\'' +
                ", status=" + status +
                '}';
    }

    // Getters & Setters
    public int getRoomNumber() {
        return roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isDateOccupied(LocalDate date) {
        return occupiedDates.contains(date.toString());
    }

    public void addOccupiedDate(LocalDate date) {
        String dateStr = date.toString();
        if (!occupiedDates.contains(dateStr)) {
            occupiedDates.add(dateStr);
        }
    }
    public static Room getRoomByNumber(int number) {
        for (Room room : Database.roomDB) {
            if (room.getRoomNumber() == number) return room;
        }
        return null;
    }




}

