package com.example.emsss;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import com.example.emsss.DB.Database;
import com.example.emsss.model.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class OrganizerController {
    @FXML private TextField EventNameField;
    @FXML private TextField BalanceField;
    @FXML private TextField EventDescriptionField;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private TextField EventRoomNumber;
    @FXML private DatePicker EventDate;
    @FXML private TextField EventPrice;
    @FXML private TextField EventNameForDelete;
    @FXML private TextArea dashboardTextArea;
    @FXML private TextField EventNewNameField;
    private Organizer organizer;

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public void initialize() {
        organizer = new Organizer("org1", "1234");
        loadCategories();
    }
    private void loadCategories() {
        categoryComboBox.getItems().clear(); // Clear any previous data

        // Add each category separately
        for (Category category : Database.categoryDB) {
            categoryComboBox.getItems().add(String.valueOf(category));
        }
    }



    @FXML
    public void addBalance() {
        try {
            String balanceText = BalanceField.getText().trim();
            if (balanceText.isEmpty()) {
                showAlert("Balance field cannot be empty.", Alert.AlertType.ERROR);
                return;
            }

            int Balance = Integer.parseInt(balanceText);
            organizer.setOrganizerWallet(new Wallet(Balance));
            showAlert("Balance successfully updated to $" + Balance, Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Error: Please enter a valid number for the balance.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("An unexpected error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void addEvent() {
        try {
            String name = EventNameField.getText();
            String desc = EventDescriptionField.getText();
            int roomNum = Integer.parseInt(EventRoomNumber.getText());
            LocalDate eventDate = EventDate.getValue();
            int price = Integer.parseInt(EventPrice.getText());

            Category category = Category.getCategoryByName(categoryComboBox.getValue());
            if (category == null) {
                showAlert("Please select a category.", Alert.AlertType.ERROR);
                return;
            }

            Room room = Room.getRoomByNumber(roomNum);
            if (room == null) {
                showAlert("Room not found.", Alert.AlertType.ERROR);
                return;
            }

            boolean success = organizer.addEvent(name, desc, category, room, eventDate, price);
            if (success) {
                showAlert("Event added successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Failed to add event. Check for duplicates, room availability, or past date.", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            showAlert("Error while adding event: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }



    @FXML
    private void updateEvent() {
        try {

            String oldName = EventNameField.getText();
            String newName = EventNewNameField.getText();
            String desc = EventDescriptionField.getText();
            int roomStr = Integer.parseInt(EventRoomNumber.getText());
            String date = EventDate.getValue().toString();
            int price = Integer.parseInt(EventPrice.getText());
            Category category = Category.getCategoryByName(categoryComboBox.getValue());
            if (category == null) {
                showAlert("Please select a category.", Alert.AlertType.ERROR);
                return;
            }
            if (EventDate.getValue() == null || EventRoomNumber.getText().isEmpty() || EventNewNameField.getText().isEmpty()) {
                showAlert("Please fill all required fields.", Alert.AlertType.ERROR);
                return;
            }

            Room room = Room.getRoomByNumber(roomStr);
            Event.Status status = Event.Status.UPCOMING;

            if (room == null) {
                showAlert("Room not found." , Alert.AlertType.ERROR);
                return;
            }

            boolean updated = organizer.updateEvent(oldName, newName, desc, category, organizer.getUsername(), room, date, status, price);
            if (updated) {
                showAlert("Event updated successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Event not found for update." , Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error while updating event: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void DeleteEvent(ActionEvent event) {
        try {
            String eventName = EventNameForDelete.getText().trim();
            if (eventName.isEmpty()) {
                showAlert("Please enter an event name.", Alert.AlertType.ERROR);
                return;
            }

            boolean flag = false;
            for (Event e : Database.eventDB) {
                if (e.getTitle().equalsIgnoreCase(eventName)) {
                    organizer.deleteEvent(eventName);
                    e.getRoom().setStatus(Room.Status.AVAILABLE);
                    showAlert("Event Deleted Successfully", Alert.AlertType.INFORMATION);
                    EventNameForDelete.clear();
                    flag = true;
                    break;
                }
            }

            if (!flag) showAlert("Event Not Found", Alert.AlertType.ERROR);


        } catch (Exception e) {
            showAlert("An error occurred while deleting the event: " + e.getMessage(), Alert.AlertType.ERROR);
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
            showAlert("Failed to return to Admin Choosing screen.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void viewDashboard() {
        dashboardTextArea.setText(organizer.showDashboard());
    }
    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Organizer Panel");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
