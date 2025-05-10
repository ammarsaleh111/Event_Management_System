package com.example.emsss.DB;

import com.example.emsss.model.*;

import java.io.File;
import java.util.ArrayList;

public class Database {

    private static final String DATA_DIR = "F:/gam3a/Materials 1/2nd Semester/EMSss/data/";
    private static final String ADMIN_FILE = DATA_DIR + "admins.txt";
    private static final String ORGANIZER_FILE = DATA_DIR + "organizers.txt";
    private static final String ATTENDEE_FILE = DATA_DIR + "attendees.txt";
    private static final String EVENT_FILE = DATA_DIR + "events.txt";
    private static final String ROOM_FILE = DATA_DIR + "rooms.txt";
    private static final String CATEGORY_FILE = DATA_DIR + "categories.txt";


    public static ArrayList<Admin> adminDB = TxtDataHandler.loadFromFile(ADMIN_FILE, TxtDataHandler::stringToAdmin);
    public static ArrayList<Organizer> organizerDB = TxtDataHandler.loadFromFile(ORGANIZER_FILE, TxtDataHandler::stringToOrganizer);
    public static ArrayList<Attendee> attendeeDB = TxtDataHandler.loadFromFile(ATTENDEE_FILE, TxtDataHandler::stringToAttendee);
    public static ArrayList<Event> eventDB = TxtDataHandler.loadFromFile(EVENT_FILE, TxtDataHandler::stringToEvent);
    public static ArrayList<Room> roomDB = TxtDataHandler.loadFromFile(ROOM_FILE, TxtDataHandler::stringToRoom);
    public static ArrayList<Category> categoryDB = TxtDataHandler.loadFromFile(CATEGORY_FILE, TxtDataHandler::stringToCategory);


    public static void saveAllData() {
        System.out.println("Attempting to save all application data to .txt files...");

        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        TxtDataHandler.saveToFile(ADMIN_FILE, adminDB, TxtDataHandler::adminToString);
        TxtDataHandler.saveToFile(ORGANIZER_FILE, organizerDB, TxtDataHandler::organizerToString);
        TxtDataHandler.saveToFile(ATTENDEE_FILE, attendeeDB, TxtDataHandler::attendeeToString);
        TxtDataHandler.saveToFile(EVENT_FILE, eventDB, TxtDataHandler::eventToString);
        TxtDataHandler.saveToFile(ROOM_FILE, roomDB, TxtDataHandler::roomToString);
        TxtDataHandler.saveToFile(CATEGORY_FILE, categoryDB, TxtDataHandler::categoryToString);

        System.out.println("All application data saving process to .txt files completed.");
    }

    static {
        linkOrganizersToEvents();
    }

    public static void linkOrganizersToEvents() {
        if (organizerDB == null || eventDB == null) {
            System.err.println("Cannot link organizers to events: organizerDB or eventDB is null.");
            return;
        }
        for (Organizer organizer : organizerDB) {
            organizer.clearMyEvents();
            for (Event event : eventDB) {
                if (event.getOrganizerName() != null && event.getOrganizerName().equals(organizer.getUsername())) {
                    organizer.addEvent(event);
                }
            }
        }
    }



}

