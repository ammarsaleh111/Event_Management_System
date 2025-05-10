package com.example.emsss.model;


import java.time.LocalDate;

public abstract class User {
    protected String username;
    protected String password;
    protected LocalDate dateOfBirth;

    public User(String username, String password, LocalDate dateOfBirth) {
        setUsername(username);
        setPassword(password);
        setDateOfBirth(dateOfBirth);
    }
    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }
public User(){}
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        this.username = username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        // You can also add complexity rules here if needed
        this.password = password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth cannot be null.");
        }
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future.");
        }
        this.dateOfBirth = dateOfBirth;
    }
    abstract public String showDashboard();
}
