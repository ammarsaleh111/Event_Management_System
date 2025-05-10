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
public class AttendeeController {
    @FXML private TextField interestField;
    @FXML private TextField addressField;
    @FXML private TextField eventIdField;
    @FXML private TextField balance;
    @FXML private TextArea dashboardTextArea;

    private Attendee attendee;



    @FXML
    public void addInterestButtonAction() {
        String interest = interestField.getText().trim();
        if (!interest.isEmpty()) {
            attendee.addInterest(interest);
            showAlert("Interest added: " + interest, Alert.AlertType.INFORMATION);
            interestField.clear();
        } else {
            showAlert("Interest field cannot be empty.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void addBalance() {
        try {
            String balanceText = balance.getText().trim();
            if (balanceText.isEmpty()) {
                showAlert("Balance field cannot be empty.", Alert.AlertType.ERROR);
                return;
            }

            int Balance = Integer.parseInt(balanceText);
            attendee.getWallet().setBalance(Balance);
            showAlert("Balance successfully updated to $" + Balance, Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Error: Please enter a valid number for the balance.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("An unexpected error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }



    @FXML
    public void removeInterestButtonAction() {
        String interest = interestField.getText().trim();
        if (attendee.getInterests().contains(interest.toLowerCase())) {
            attendee.removeInterest(interest);
            showAlert("Interest removed: " + interest, Alert.AlertType.INFORMATION);
            interestField.clear();
        } else {
            showAlert("Interest not found in your list.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void updateAddressButtonAction() {
        String address = addressField.getText().trim();
        if (!address.isEmpty()) {
            attendee.setAddress(address);
            showAlert("Address updated.", Alert.AlertType.INFORMATION);
            addressField.clear();
        } else {
            showAlert("Address field cannot be empty.", Alert.AlertType.ERROR);
        }
    }
   @FXML
public void buyTicketButtonAction() {
    String title = eventIdField.getText().trim().toLowerCase();
    if (title.isEmpty()) {
        showAlert("Event title cannot be empty.", Alert.AlertType.ERROR);
        return;
    }
    Event targetEvent = null;
    for (Event event : Database.eventDB) {
        if (event.getTitle().toLowerCase().equals(title)) {
            targetEvent = event;
            break;
        }
    }

       // ... inside buyTicketButtonAction() ...
       if (targetEvent == null) {
           showAlert("No event found with title: " + title, Alert.AlertType.ERROR);
           return;
       }

// DEBUG: Check Attendee and its Wallet instance
       System.out.println("[Controller] Current Attendee instance hash: " + System.identityHashCode(attendee));
       if (attendee != null && attendee.getWallet() != null) {
           System.out.println("[Controller] Current Attendee's Wallet instance hash: " + System.identityHashCode(attendee.getWallet()));
       } else {
           System.out.println("[Controller] Current Attendee or its Wallet is null!");
       }

// DEBUG: Compare with the instance in Database.attendeeDB
       for (Attendee dbAttendee : Database.attendeeDB) {
           if (dbAttendee.getUsername().equals(attendee.getUsername())) {
               System.out.println("[Controller] Database Attendee instance hash: " + System.identityHashCode(dbAttendee));
               if (dbAttendee.getWallet() != null) {
                   System.out.println("[Controller] Database Attendee's Wallet instance hash: " + System.identityHashCode(dbAttendee.getWallet()));
               }
               break;
           }
       }

       attendee.buyTicket(targetEvent);
       showAlert("Ticket purchase process completed. Check console for status.", Alert.AlertType.INFORMATION);
       eventIdField.clear();
       viewDashboard(); // Good to refresh UI

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
    public void viewDashboard() {
        String dashboardInfo = attendee.showDashboard();
        dashboardTextArea.setText(dashboardInfo);
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Attendee Panel");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Attendee getAttendee() {
        return attendee;
    }

    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
    }


}
