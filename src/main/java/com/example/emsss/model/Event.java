package com.example.emsss.model;
import com.example.emsss.DB.*;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private double price;
    private String title;
    private String description;
    private String date;
    private Room room;
    private Category category;
    private String organizerName;
    private Status status;

    private List<String> attendeeUsernames = new ArrayList<>();

    public List<String> getAttendeeUsernames() {
        return attendeeUsernames;
    }

    public void addAttendee(String username) {
        attendeeUsernames.add(username);
    }

    public enum Status {
        UPCOMING, COMPLETED, CANCELLED
    }

    public Event(String title, String description, String date, Category category,
                 String organizerName, Status state,double price, Room room) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.category = category;
        this.organizerName = organizerName;
        this.status = state;
        this.price =price;
        this.room = room;
    }

    public Event() {
    }

    @Override
    public String toString() {
        return "Event Title: " + title +
                "\nDescription: " + description +
                "\nDate: " + date +
                "\nPrice: " + price +
                "\nRoom: " + (room != null ? room.getRoomNumber() : "None") +
                "\nCategory: " + (category != null ? category.getName() : "None") +
                "\nOrganizer: " + organizerName +
                "\nStatus: " + status+"\n-----------------------------------------\n";
    }

    // Getters & Setters

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static Event getEventByName(String name) {
        for (Event event : Database.eventDB) {
            if (event.getTitle() == name) return event;
        }
        return null;
    }

}
