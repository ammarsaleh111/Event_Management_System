package com.example.emsss.DB;

import com.example.emsss.model.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TxtDataHandler {

    private static final String DELIMITER = "|";
    private static final String DELIMITER_REGEX = "\\|";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private static String formatString(String s) {
        return (s == null || s.trim().isEmpty()) ? "" : s.replace(DELIMITER, " ").replace("\n", " ").replace("\r", " ");
    }

    private static double parseDouble(String s) {
        try {
            return (s == null || s.isEmpty()) ? 0.0 : Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private static int parseInt(String s) {
        try {
            return (s == null || s.isEmpty()) ? 0 : Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static LocalDate parseDate(String s) {
        try {
            return (s == null || s.isEmpty()) ? null : LocalDate.parse(s, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // --- Admin ---
    public static String adminToString(Admin admin) {
        return String.join(DELIMITER,
                formatString(admin.getUsername()),
                formatString(admin.getPassword()),
                String.valueOf(admin.getDateOfBirth()),
                String.valueOf(admin.getStartWorkingHours()),
                String.valueOf(admin.getEndWorkingHours()),
                String.valueOf(admin.getRole())
        );
    }

    public static Admin stringToAdmin(String line) {
        String[] p = line.split(DELIMITER_REGEX, -1);
        return new Admin(
                p[0],
                p[1],
                parseDate(p[2]),
                parseInt(p[3]),
                parseInt(p[4]),
                Admin.Role.valueOf(p[5])
        );
    }

    // --- Attendee ---
    public static String attendeeToString(Attendee a) {
        return String.join(DELIMITER,
                formatString(a.getUsername()),
                formatString(a.getPassword()),
                String.valueOf(a.getDateOfBirth()),
                String.valueOf(a.getWallet().getBalance()),
                formatString(a.getAddress()),
                String.valueOf(a.getGender())
        );
    }

    public static Attendee stringToAttendee(String line) {
        String[] p = line.split(DELIMITER_REGEX, -1);
        return new Attendee(
                p[0],
                p[1],
                parseDate(p[2]),
                parseDouble(p[3]),
                p[4],
                Attendee.Gender.valueOf(p[5])
        );
    }

    // --- Organizer ---
    public static String organizerToString(Organizer o) {
        return String.join(DELIMITER,
                formatString(o.getUsername()),
                formatString(o.getPassword()),
                String.valueOf(o.getDateOfBirth()),
                String.valueOf(o.getOrganizerWallet().getBalance())
        );
    }

    public static Organizer stringToOrganizer(String line) {
        String[] p = line.split(DELIMITER_REGEX, -1);
        return new Organizer(
                p[0],
                p[1],
                parseDate(p[2]),
                new Wallet(parseDouble(p[3]))
        );
    }

    // --- Category ---
    public static String categoryToString(Category c) {
        return formatString(c.getName());
    }

    public static Category stringToCategory(String line) {
        return new Category(line.trim());
    }

    // --- Room ---
    public static String roomToString(Room r) {
        return String.join(DELIMITER,
                String.valueOf(r.getCapacity()),
                formatString(r.getLocation())
                // Status is set to AVAILABLE in constructor, no need to save
        );
    }

    public static Room stringToRoom(String line) {
        String[] p = line.split(DELIMITER_REGEX, -1);
        return new Room(parseInt(p[0]), p[1]);
    }

    // --- Event ---
    public static String eventToString(Event e) {
        return String.join(DELIMITER,
                formatString(e.getTitle()),
                formatString(e.getDescription()),
                formatString(e.getDate()),
                e.getCategory().getName(),
                formatString(e.getOrganizerName()),
                String.valueOf(e.getStatus()),
                String.valueOf(e.getPrice()),
                e.getRoom().getLocation()
        );
    }

    public static Event stringToEvent(String line) {
        String[] p = line.split(DELIMITER_REGEX, -1);
        Category category = new Category(p[3]);
        Room room = new Room(0, p[7]);
        return new Event(
                p[0],
                p[1],
                p[2],
                category,
                p[4],
                Event.Status.valueOf(p[5]),
                parseDouble(p[6]),
                room
        );
    }

    // file operations
    public static <T> ArrayList<T> loadFromFile(String filePath, LineParser<T> parser) {
        ArrayList<T> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    T obj = parser.parse(line);
                    if (obj != null) list.add(obj);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from " + filePath + ": " + e.getMessage());
        }
        return list;
    }

    public static <T> void saveToFile(String filePath, List<T> list, ObjectToStringConverter<T> converter) {
        try {
            List<String> lines = list.stream().map(converter::convert).collect(Collectors.toList());
            Files.write(Paths.get(filePath), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error writing to " + filePath + ": " + e.getMessage());
        }
    }

    // Functional Interfaces
    @FunctionalInterface
    public interface LineParser<T> {
        T parse(String line);
    }

    @FunctionalInterface
    public interface ObjectToStringConverter<T> {
        String convert(T obj);
    }
}
