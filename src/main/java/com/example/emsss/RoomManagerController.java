package com.example.emsss;
import com.example.emsss.DB.Database;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.emsss.model.*;
public class RoomManagerController {
    private Admin admin1;
    @FXML
    private TextField capacityField;
    
    @FXML 
    private TextField startWorkingHoursField;
    
    @FXML 
    private TextField endWorkingHoursField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField roomNumberField;

    @FXML
    private TextArea dashboardTextArea;

    public void initialize() {
    }
    public void setAdmin1(Admin admin1) {
        this.admin1 = admin1;
        showDashboard();
    }



    @FXML
    public void setStartWorkingHoursAction() {
        try {
            String input = startWorkingHoursField.getText().trim();

            int start = Integer.parseInt(input);

            if (start < 0 || start > 24) {
                throw new IllegalArgumentException("Hour out of range");
            }
            admin1.setStartWorkingHours(start);
            showDashboard();
            showAlert("Start working hour set to " + start + ":00");
            startWorkingHoursField.clear();

        } catch (IllegalArgumentException e) {
            showAlert("Invalid input for start working hours. Please enter a number between 0 and 24.");
        }
    }

    @FXML
    public void setEndWorkingHoursAction() {
        try {
            String input = endWorkingHoursField.getText().trim();

            int end = Integer.parseInt(input);
            if (end < 0 || end > 24) {
                throw new IllegalArgumentException("Hour out of range");
            }
            admin1.setEndWorkingHours(end);
            showDashboard();
            showAlert("End working hour set to " + end + ":00");
            endWorkingHoursField.clear();
        } catch (IllegalArgumentException e) {
            showAlert("Invalid input for end working hours. Please enter a number between 0 and 24.");
        }
    }

    @FXML
    private void addRoom() {
        try {
            int capacity = Integer.parseInt(capacityField.getText());
            String location = locationField.getText();

            if (location.isEmpty()) {
                showAlert("Location cannot be empty.");
                return;
            }

            Room newRoom = new Room(capacity, location);
            newRoom.setStatus(Room.Status.AVAILABLE); // Optional if backend does this

            if (admin1.addRoom(newRoom)) {
                showAlert("Room added successfully!");
                clearFields();
                showDashboard();
            } else {
                showAlert("You are not authorized to add rooms.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid input. Please enter correct numeric values.");
        }
    }

    @FXML
    private void updateRoom() {
        try {
            int roomNumber = Integer.parseInt(roomNumberField.getText());
            int capacity = Integer.parseInt(capacityField.getText());
            String location = locationField.getText();

            if (location.isEmpty()) {
                showAlert("Location cannot be empty.");
                return;
            }

            if (admin1.updateRoom(roomNumber, capacity, location)) {
                showAlert("Room updated successfully!");
            } else {
                showAlert("Room not found or you are not authorized.");
            }

            clearFields();
            showDashboard();
        } catch (NumberFormatException e) {
            showAlert("Invalid input. Please enter correct numeric values.");
        }
    }

    @FXML
    private void deleteRoom() {
        try {
            int roomNumber = Integer.parseInt(roomNumberField.getText());

            if (admin1.deleteRoom(roomNumber)) {
                showAlert("Room deleted successfully!");
            } else {
                showAlert("Room not found or you are not authorized.");
            }

            clearFields();
            showDashboard();
        } catch (NumberFormatException e) {
            showAlert("Invalid room number.");
        }
    }

    @FXML
    public void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            root.getStylesheets().add(getClass().getResource("/login.css").toExternalForm());
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setMaximized(false);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to return to Admin Choosing screen.");
        }
    }


    @FXML
    private void showDashboard() {
        StringBuilder dashboardContent = new StringBuilder();
        dashboardContent.append("\n==========  Room Dashboard ==========\n");
        dashboardContent.append("\n--- Working Hours ---\n");
        dashboardContent.append("Start: ").append(admin1.getStartWorkingHours()).append(":00\n");
        dashboardContent.append("End: ").append(admin1.getEndWorkingHours()).append(":00\n");
        
        for (Room room : Database.roomDB) {
            dashboardContent.append(room).append("\n");
        }

        dashboardContent.append("=====================================\n");

        dashboardTextArea.setText(dashboardContent.toString());
    }

    private void clearFields() {
        capacityField.clear();
        locationField.clear();
        roomNumberField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Room Manager");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
