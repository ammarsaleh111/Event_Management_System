package com.example.emsss.model;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import com.example.emsss.DB.Database;
import com.example.emsss.OrganizerController;

import java.lang.*;
public class Organizer extends User {
    private List<Event> myEvents = new ArrayList<>();
    private Wallet OrganizerWallet;
    public Organizer(String username, String password, LocalDate dateOfBirth,Wallet wallet) {
        super(username, password, dateOfBirth);
        this.OrganizerWallet = wallet;
    }
    public Organizer(){}
    public Organizer(String username, String password) {
        super(username, password);
    }
    public String toString() {
        return "Organizer name: " + username ;
    }
    public double getBalance() {
        try{
            return OrganizerWallet.getBalance();
        }catch (NullPointerException e){
            System.out.println("Wallet not found");
        }
    return 0.0;
    }

    public Wallet getOrganizerWallet() {
        return OrganizerWallet;
    }

    public void setOrganizerWallet(Wallet organizerWallet) {
        OrganizerWallet = organizerWallet;
    }

    public void addBalance(double ticketprice ) {
        try{
       OrganizerWallet.addfunds(ticketprice);
        }catch (NullPointerException e){
            System.out.println("Wallet not found");
        }
    }
// In Organizer.java

    public void clearMyEvents() {
        if (this.myEvents != null) {
            this.myEvents.clear();
        }
    }

    public boolean isDuplicateTitleOnSameDate(String title, String date) {
        for (Event event : Database.eventDB) {
            if (event.getTitle().equalsIgnoreCase(title) && event.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }
    public boolean addEvent(String name, String description, Category category, Room room, LocalDate date, int price) {
        String dateStr = date.toString();
        LocalDate today = LocalDate.now();

        if (isDuplicateTitleOnSameDate(name, dateStr)) {
            System.out.println("Error: Event with the same title already exists on this date.");
            return false;
        }

        if (date.isBefore(today)) {
            System.out.println("Error: Cannot schedule events in the past.");
            return false;
        }

        if (room == null || room.isDateOccupied(date)) {
            System.out.println("Error: Room is either null or already occupied.");
            return false;
        }

        // All checks passed — create and store event
        Event event = new Event(name, description, dateStr, category, this.getUsername(), Event.Status.UPCOMING, price, room);
        Database.eventDB.add(event);
        myEvents.add(event);

        room.addOccupiedDate(date);
        room.setStatus(Room.Status.UNAVAILABLE);

        System.out.println("Event added successfully: " + name);
        return true;
    }

    public void addEvent(Event e) {
        myEvents.add(e);
    }

    public boolean updateEvent(String name,String newName, String newDesc, Category newCat, String newOrganizerName,Room newRoom, String newDate, Event.Status newState,int newPrice) {
        for (Event event : Database.eventDB) {
            if (event.getTitle().equals(name) ) {
                event.setTitle(newName);
                event.setDescription(newDesc);
                event.setCategory(newCat);
                event.setRoom(newRoom);
                event.setDate(newDate);
                event.setStatus(newState);
                event.setPrice(newPrice);
                event.setOrganizerName(newOrganizerName);
                return true;
            }
        }
        return false;
    }
    public String deleteEvent(String name) {
        for (Event event : Database.eventDB) {
            if (event.getTitle().equals(name)) {
                event.getRoom().removeDate(LocalDate.parse(event.getDate()));
                event.getRoom().setStatus(Room.Status.AVAILABLE);
                myEvents.remove(event);
                Database.eventDB.remove(event);
                return "The Event deleted successfully";
            }
        }
        return "The Event not found";
    }

    public String showDashboard() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n==========  Organizer Dashboard ==========\n");

        // 1. Available Rooms
        sb.append("\nAvailable Rooms:\n");
        for (Room room : Database.roomDB) {
            if (room.getStatus() == Room.Status.AVAILABLE) {
                sb.append(room).append("\n");
            }
        }
        //balance
        sb.append("\nOrganizer Balance: ").append(getBalance()).append("\n");

        // 2. Events Organized
        sb.append("\nYour Events:\n");
        for (Event event : myEvents) {
            sb.append(event).append("\n");
        }

        // 3. Attendees for each Event
        sb.append("\nAttendees for Your Events:\n");
        for (Event event : myEvents) {
            sb.append("Event: ").append(event.getTitle()).append("\n");
            Room room = event.getRoom();
            if (!event.getAttendeeUsernames().isEmpty()) {
                for (String username : event.getAttendeeUsernames()) {
                    sb.append("- ").append(username).append("\n");
                }
            }
            else {
                sb.append("No attendees yet.\n");
            }
        }

        sb.append("=====================================\n");
        return sb.toString();
    }

}
