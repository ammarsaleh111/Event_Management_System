package com.example.emsss.model;

import java.util.*;
import com.example.emsss.DB.Database;
public class Category {
    private static int nextId = 1;
    private int id;
    private String name;

    // Constructor
    public Category(String name) {
        this.id = nextId++;
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name ;

    }

    public Category() {
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Category> getAllCategories() {
        return new ArrayList<>(Database.categoryDB);
    }

    public static Category getCategoryById(int id) {
        for (Category cat : Database.categoryDB) {
            if (cat.getId() == id) return cat;
        }
        return null;
    }

    public static Category getCategoryByName(String name) {
        for (Category cat : Database.categoryDB) {
            if (cat.getName().equalsIgnoreCase(name)) return cat;
        }
        return null;
    }

    public static boolean isNameExists(String name) {
        for (Category cat : Database.categoryDB) {
            if (cat.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

}
