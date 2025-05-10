package com.example.emsss;
import com.example.emsss.DB.Database;
import java.io.IOException;
import java.util.ArrayList;

import com.example.emsss.model.Admin;
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

public class SuperAdminController {

    @FXML private TextArea dashboardTextArea;
    @FXML private TextField startWorkingHoursField;
    @FXML private TextField endWorkingHoursField;
    @FXML private TextField roomCapacityField;
    @FXML private TextField roomLocationField;
    @FXML private TextField roomIdField;
    @FXML private TextField categoryNameField;
    @FXML private TextField categoryIdField;
    @FXML private TextField newCategoryNameField;
    @FXML private TextField deleteCategoryIdField;

    private Admin admin;

    public void initialize() {
    }


    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    @FXML
    public void setStartWorkingHoursAction() {
        try {
            String input = startWorkingHoursField.getText().trim();

            int start = Integer.parseInt(input);

            if (start < 0 || start > 24) {
                throw new IllegalArgumentException("Hour out of range");
            }
            admin.setStartWorkingHours(start);
            showAlert("Start working hour set to " + start + ":00" , Alert.AlertType.INFORMATION);
            startWorkingHoursField.clear();

        } catch (IllegalArgumentException e) {
            showAlert("Invalid input for start working hours. Please enter a number between 0 and 24." , Alert.AlertType.ERROR);
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
            admin.setEndWorkingHours(end);
            showAlert("End working hour set to " + end + ":00" , Alert.AlertType.INFORMATION);
            endWorkingHoursField.clear();

        } catch (IllegalArgumentException e) {
            showAlert("Invalid input for End working hours. Please enter a number between 0 and 24." , Alert.AlertType.ERROR);
        }
    }
    @FXML
    public void addRoomButtonAction() {
        try {
            int capacity = Integer.parseInt(roomCapacityField.getText());
            String location = roomLocationField.getText();
            if (location.isEmpty()) {
                showAlert("Location field cannot be empty.", Alert.AlertType.ERROR);
                return;
            }

            Room room = new Room(capacity, location);
            room.setStatus(Room.Status.AVAILABLE);
            if (admin.addRoom(room)) {
                showAlert("Room added successfully.", Alert.AlertType.INFORMATION);
                clearRoomFields();
            } else {
                showAlert("You are not authorized to add rooms.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid room capacity. It must be a number.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void deleteRoomButtonAction() {
        try {
            int id = Integer.parseInt(roomIdField.getText());

            for (Event event : new ArrayList<>(Database.eventDB)) {
                if (event.getRoom().getRoomNumber() == id) {
                    Database.eventDB.remove(event);
                }
            }

            if (admin.deleteRoom(id)) {
                showAlert("Room deleted successfully.", Alert.AlertType.INFORMATION);
                roomIdField.clear();
            } else {
                showAlert("Room not found or unauthorized.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Room ID. It must be a number.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void updateRoomButtonAction() {
        try {
            int roomNumber = Integer.parseInt(roomIdField.getText());
            int capacity = Integer.parseInt(roomCapacityField.getText());
            String location = roomLocationField.getText();

            if (admin.updateRoom(roomNumber, capacity, location)) {
                showAlert("Room updated successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Room not found or unauthorized!", Alert.AlertType.ERROR);
            }

            clearRoomFields();
            roomIdField.clear();
            admin.showDashboard();

        } catch (NumberFormatException e) {
            showAlert("Invalid input. Please enter correct numeric values.", Alert.AlertType.ERROR);
        }
    }

    private void clearRoomFields() {
        roomCapacityField.clear();
        roomLocationField.clear();
    }

    @FXML
    public void addCategoryButtonAction() {
        String name = categoryNameField.getText();
        if (name.isEmpty()) {
            showAlert("Category name cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        if (admin.addCategory(name)) {
            showAlert("Category added successfully.", Alert.AlertType.INFORMATION);
            categoryNameField.clear();
        } else {
            showAlert("Category name already exists or permission denied.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void updateCategoryButtonAction() {
        try {
            int id = Integer.parseInt(categoryIdField.getText());
            String newName = newCategoryNameField.getText();
            if (newName.isEmpty()) {
                showAlert("New category name cannot be empty.", Alert.AlertType.ERROR);
                return;
            }

            if (admin.updateCategory(id, newName)) {
                showAlert("Category updated successfully.", Alert.AlertType.INFORMATION);
                categoryIdField.clear();
                newCategoryNameField.clear();
            } else {
                showAlert("Update failed: Duplicate name, invalid ID, or permission denied.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid category ID. It must be a number.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void deleteCategoryButtonAction() {
        try {
            int id = Integer.parseInt(deleteCategoryIdField.getText());
            if (admin.deleteCategory(id)) {
                showAlert("Category deleted successfully.", Alert.AlertType.INFORMATION);
                deleteCategoryIdField.clear();
            } else {
                showAlert("Category not found or permission denied.", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid category ID. It must be a number.", Alert.AlertType.ERROR);
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
            currentStage.centerOnScreen();
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to return to Admin Choosing screen.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void viewDashboard() {
        if (admin == null) {
            showAlert("Admin not loaded properly. Please login again.", Alert.AlertType.ERROR);
            return;
        }
        String dashboardText = admin.showDashboard();  // Or dynamic reference based on login
        dashboardTextArea.setText(dashboardText);
    }


    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Super Admin Panel");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
