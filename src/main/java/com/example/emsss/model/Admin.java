package com.example.emsss.model;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.emsss.DB.Database;
import com.example.emsss.SuperAdminController;


public class Admin extends User {
    private int StartWorkingHours=10;
    private int EndWorkingHours=13;
    private Role role;

    public enum Role {
        SuperAdmin,
        RoomManager,
        CategoryManager
    }
private ArrayList<String> compobox;
    public Admin(String username, String password, LocalDate dateOfBirth, int startWorkingHours, int endWorkingHours, Role role) {
        super(username, password, dateOfBirth);
        setStartWorkingHours(startWorkingHours);
        setEndWorkingHours(endWorkingHours);
        setRole(role);
    }
    public Admin(){}

    public Admin(String username, String password) {
        super(username, password);

    }

    // --- Getters & Setters ---

    public int getStartWorkingHours() {
        return StartWorkingHours;
    }

    public void setStartWorkingHours(int startWorkingHours) {
        this.StartWorkingHours = startWorkingHours;
    }

    public int getEndWorkingHours() {
        return EndWorkingHours;
    }

    public void setEndWorkingHours(int endWorkingHours) {
        this.EndWorkingHours = endWorkingHours;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null.");
        }
        this.role = role;
    }

    @Override
    public String toString() {
        return "Admin name: " + username;
    }

    // --- Room Management (RoomManager / SuperAdmin) ---
    public boolean addRoom(Room room) {
        if (role == Role.RoomManager || role == Role.SuperAdmin) {
            Database.roomDB.add(room);
            room.setStatus(Room.Status.AVAILABLE);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteRoom(int number) {
        if (role == Role.RoomManager || role == Role.SuperAdmin) {
            Room room = Room.getRoomByNumber(number);
            if (room != null) {
                Database.roomDB.remove(room);
                System.out.println("Room deleted: " + room.getRoomNumber());
                return true;
            }
            System.out.println("Room not found.");
            return false;
        } else {
            System.out.println("You are not authorized to delete a room.");
            return false;
        }
    }
    public boolean updateRoom(int roomNumber, int capacity, String location) {
        if (role == Role.RoomManager || role == Role.SuperAdmin) {
            Room room = Room.getRoomByNumber(roomNumber);
            if (room != null) {
                room.setCapacity(capacity);
                room.setLocation(location);
                System.out.println("Room updated: #" + roomNumber);
                return true;
            } else {
                System.out.println("Room not found.");
                return false;
            }
        } else {
            System.out.println("You are not authorized to update a room.");
            return false;
        }
    }


    // --- Category Management (CategoryManager / SuperAdmin) ---
    public boolean addCategory(String name) {
        if (role == Role.CategoryManager || role == Role.SuperAdmin) {
            if (Category.isNameExists(name)) {
                System.out.println("Category name already exists: " + name);
                return false;
            }
            Category newCategory = new Category(name);
            Database.categoryDB.add(newCategory);
            System.out.println("Category added: " + name);
            return true;
        } else {
            System.out.println("You are not authorized to add categories.");
            return false;
        }
    }

    public boolean updateCategory(int id, String newName) {
        if (role == Role.CategoryManager || role == Role.SuperAdmin) {
            Category cat = Category.getCategoryById(id);
            if (cat != null && !(Category.isNameExists(newName))) {
                cat.setName(newName);
                System.out.println("Category updated: ID " + id + " → " + newName);
                return true;
            }
            System.out.println("Update failed: Duplicate name or invalid ID");
            return false;
        } else {
            System.out.println("You are not authorized to update categories.");
            return false;
        }
    }

    public boolean deleteCategory(int id) {
        if (role == Role.CategoryManager || role == Role.SuperAdmin) {
            Category cat = Category.getCategoryById(id);
            if (cat != null) {
                Database.categoryDB.remove(cat);
                System.out.println("Category deleted: ID " + id);
                return true;
            }
            System.out.println("Category not found.");
            return false;
        } else {
            System.out.println("You are not authorized to delete categories.");
            return false;
        }
    }

    @Override
    public String showDashboard() {
        StringBuilder dashboardContent = new StringBuilder();
        dashboardContent.append("\n==========  Dashboard  ==========\n");

        dashboardContent.append("\n--- Working Hours ---\n");
        dashboardContent.append("Start: ").append(getStartWorkingHours()).append("\n");
        dashboardContent.append("End: ").append(getEndWorkingHours()).append("\n");

        if (role == Role.RoomManager || role == Role.SuperAdmin) {
            dashboardContent.append("\n--- Rooms ---\n");
            for (Room room : Database.roomDB) {
                dashboardContent.append(room).append("\n");
            }
        }

        if (role == Role.SuperAdmin) {
            dashboardContent.append("\n--- Events ---\n");
            for (Event event : Database.eventDB) {
                dashboardContent.append(event).append("\n");
            }

            dashboardContent.append("\n--- Organizers ---\n");
            for (Organizer organizer : Database.organizerDB) {
                dashboardContent.append(organizer).append("\n");
            }

            dashboardContent.append("\n--- Attendees ---\n");
            for (Attendee attendee : Database.attendeeDB) {
                dashboardContent.append(attendee).append("\n");
            }
        }

        if (role == Role.CategoryManager || role == Role.SuperAdmin) {
            dashboardContent.append("\n--- Categories ---\n");
            for (Category category : Database.categoryDB) {
                dashboardContent.append(category).append("  --> id: ").append(category.getId()).append("\n");
            }
        }

        dashboardContent.append("=====================================\n");
        return dashboardContent.toString();
    }

}