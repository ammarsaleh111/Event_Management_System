package com.example.emsss.model;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


import com.example.emsss.DB.Database;
public class Attendee extends User {
    private String address;
    private Gender gender;
    private Wallet wallet;
    private List<String> interests = new ArrayList<>();

public Attendee(){}
    public Attendee(String username, String password, LocalDate dateOfBirth,
                    double balance, String address, Gender gender) {
        super(username, password, dateOfBirth);
        this.wallet = new Wallet(balance);
        this.address = address;
        this.gender = gender;
    }public Attendee(String username, String password) {
        super(username, password);
    }
    public enum Gender {
        MALE, FEMALE, OTHER
    }
    public void addInterest(String interest) {
        if (!interests.contains(interest.toLowerCase())) {
            interests.add(interest.toLowerCase());
        }
    }
    public String toString() {
        return "Attendee name: " + username ;
    }

    public void removeInterest(String interest) {
        interests.remove(interest.toLowerCase());
    }

    public List<String> getInterests() {
        return interests;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public double getBalance() {
        if (wallet != null) {
            return wallet.getBalance();
        } else {
            return 0.0;
        }
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }


    public void buyTicket(Event event) {
        Room room = event.getRoom();

        if (room == null) {
            System.out.println("This event does not have a room assigned.");
            return;
        }
// ... inside buyTicket(Event event) ...
        System.out.println("[Attendee.buyTicket] For Attendee: " + this.getUsername()); // Current attendee
        System.out.println("[Attendee.buyTicket] Attempting to buy ticket for Event: " + event.getTitle());

        if (this.wallet == null) {
            System.out.println("[Attendee.buyTicket] ERROR: Attendee's wallet is null!");
            return;
        }
        System.out.println("[Attendee.buyTicket] Attendee " + this.getUsername() + " wallet balance BEFORE deduction: " + this.wallet.getBalance());

        if (this.wallet.getBalance() < event.getPrice()) {
            System.out.println("Insufficient balance to buy the ticket.");
            return;
        }
// ... (other checks for room capacity) ...

        this.wallet.deductFunds(event.getPrice());
        System.out.println("[Attendee.buyTicket] Attendee " + this.getUsername() + " wallet balance AFTER deduction: " + this.wallet.getBalance());

        for(Organizer organizer : Database.organizerDB){
            if(organizer.getUsername().equals(event.getOrganizerName())){
                System.out.println("[Attendee.buyTicket] Found Organizer: " + organizer.getUsername());
                if (organizer.getOrganizerWallet() == null) {
                    System.out.println("[Attendee.buyTicket] ERROR: Organizer's wallet is null!");
                    continue; // or break, depending on logic
                }
                // DEBUG: Check Organizer and its Wallet instance
                System.out.println("[Attendee.buyTicket] Current Organizer instance hash: " + System.identityHashCode(organizer));
                System.out.println("[Attendee.buyTicket] Current Organizer's Wallet instance hash: " + System.identityHashCode(organizer.getOrganizerWallet()));

                // DEBUG: Compare with the instance in Database.organizerDB (optional, but good for sanity check)
                for (Organizer dbOrganizer : Database.organizerDB) {
                    if (dbOrganizer.getUsername().equals(organizer.getUsername())) {
                        System.out.println("[Attendee.buyTicket] Database Organizer instance hash: " + System.identityHashCode(dbOrganizer));
                        if (dbOrganizer.getOrganizerWallet() != null) {
                            System.out.println("[Attendee.buyTicket] Database Organizer's Wallet instance hash: " + System.identityHashCode(dbOrganizer.getOrganizerWallet()));
                        }
                        break;
                    }
                }

                System.out.println("[Attendee.buyTicket] Organizer " + organizer.getUsername() + " wallet balance BEFORE addition: " + organizer.getBalance());
                organizer.addBalance(event.getPrice()); // Assuming addBalance uses the organizer's wallet
                System.out.println("[Attendee.buyTicket] Organizer " + organizer.getUsername() + " wallet balance AFTER addition: " + organizer.getBalance());
                break;
            }
        }
        event.addAttendee(this.getUsername());
        System.out.println("Ticket purchased successfully! (Console message from Attendee.buyTicket)");

    }


    @Override
    public String showDashboard() {
        StringBuilder dashboard = new StringBuilder();

        // Basic Info
        dashboard.append("====== Attendee Dashboard ======\n");
        dashboard.append("Username: ").append(getUsername()).append("\n");
        dashboard.append("Balance: $").append(getBalance()).append("\n");
        dashboard.append("Address: ").append(getAddress() != null ? getAddress() : "N/A").append("\n");
        dashboard.append("Interests: ").append(getInterests()).append("\n");

        // Categories
        dashboard.append("\n--- Available Categories ---\n");
        if (Database.categoryDB.isEmpty()) {
            dashboard.append("No categories available.\n");
        } else {
            for (Category category : Database.categoryDB) {
                dashboard.append("- ").append(category.getName()).append("\n");
            }
        }

        // Events
        dashboard.append("\n--- Available Events ---\n");
        if (Database.eventDB.isEmpty()) {
            dashboard.append("No events available.\n");
        } else {
            for (Event event : Database.eventDB) {
                dashboard.append(event).append("\n");
            }
        }

        // Recommendations
        dashboard.append("\n- Recommended Events Based on Your Interests -\n");
        for (Event event : Database.eventDB) {
            if (getInterests().contains(event.getCategory().getName().toLowerCase())) {
                dashboard.append(event).append("\n");
            }
        }

        return dashboard.toString();
    }


}
