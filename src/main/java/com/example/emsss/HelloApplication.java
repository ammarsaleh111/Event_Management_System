package com.example.emsss;
import com.example.emsss.model.Organizer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.*;
import com.example.emsss.model.*;
import com.example.emsss.DB.*;


public class HelloApplication extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage Pstage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage =new Stage();
        stage.setTitle("EMS");
        stage.setResizable(false);
        stage.setWidth(600);
        stage.setHeight(800);
        stage.centerOnScreen();
        if (Database.adminDB.isEmpty()) {
            System.out.println("AdminDB is empty, adding dummy admins...");
            Admin admin = new Admin("admin", "admin", java.time.LocalDate.parse("2006-05-12"), 12, 13, Admin.Role.SuperAdmin);
            Admin admin1 = new Admin("a", "s", java.time.LocalDate.parse("2006-04-12"), 12, 13, Admin.Role.CategoryManager);
            Admin admin2 = new Admin("x", "z", java.time.LocalDate.parse("2006-04-12"), 12, 13, Admin.Role.RoomManager);
            Database.adminDB.add(admin);
            Database.adminDB.add(admin1);
            Database.adminDB.add(admin2);
        }

        if (Database.organizerDB.isEmpty()) {
            System.out.println("OrganizerDB is empty, adding dummy organizers...");
            Organizer organizer = new Organizer("org", "org", java.time.LocalDate.parse("2006-05-12"), new Wallet(1100));
            Organizer organizer2 = new Organizer("org2", "org2", java.time.LocalDate.parse("2006-05-12"), new Wallet(1000));
            Database.organizerDB.add(organizer);
            Database.organizerDB.add(organizer2);
        }

        if (Database.attendeeDB.isEmpty()) {
            System.out.println("AttendeeDB is empty, adding dummy attendees...");
            Attendee attendee = new Attendee("attendee", "attendee", java.time.LocalDate.parse("2006-05-12"), 10000, "address", Attendee.Gender.MALE);
            Attendee attendee2 = new Attendee("attendee2", "attendee2", java.time.LocalDate.parse("2006-05-12"), 1000, "address", Attendee.Gender.FEMALE);
            Attendee attendee3 = new Attendee("attendee3", "attendee3", java.time.LocalDate.parse("2006-05-12"), 1000, "address", Attendee.Gender.FEMALE);
            Attendee attendee4 = new Attendee("attendee4", "attendee4", java.time.LocalDate.parse("2006-05-12"), 1000, "address", Attendee.Gender.FEMALE);
            Database.attendeeDB.add(attendee);
            Database.attendeeDB.add(attendee2);
            Database.attendeeDB.add(attendee3);
            Database.attendeeDB.add(attendee4);
        }

        if (Database.roomDB.isEmpty()) {
            System.out.println("RoomDB is empty, adding dummy rooms...");
            Room room1 = new Room(20, "A");
            Database.roomDB.add(room1);
        }

        if (Database.categoryDB.isEmpty()) {
            System.out.println("CategoryDB is empty, adding dummy categories...");
            Category c1 = new Category("Food");
            Database.categoryDB.add(c1);
        }


        scene.getStylesheets().add(getClass().getResource("/login.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void stop() throws Exception {
        // This 'stop()' method is automatically called by JavaFX when the application is closing.
        System.out.println("Application is shutting down. Attempting to save all data...");
        try {
            Database.saveAllData(); // This is where you call your method to save all data to .txt files
        } catch (Exception e) {
            System.err.println("An error occurred while saving data during application shutdown:");
            e.printStackTrace();
            // You might want to log this error more formally in a real application
        }
        super.stop(); // It's good practice to call the superclass's stop method
        System.out.println("Application shutdown process complete.");
    }
}

