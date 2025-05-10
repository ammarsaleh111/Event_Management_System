package com.example.emsss;
import com.example.emsss.DB.Database;
import com.example.emsss.model.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class registerController implements Initializable {

    @FXML private ComboBox<String> roleComboBox;
    @FXML private VBox adminFields, attendeeFields, organizerFields;
    @FXML private TextField usernameField, passwordField;
    @FXML private TextField startWorkingHoursField, endWorkingHoursField, balanceField, addressField, organizerBalanceField;
    @FXML private ComboBox<String> genderComboBox, roleComboBoxAdmin;
    @FXML private DatePicker dateOfBirthPicker;
    @FXML private Button registerButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleComboBox.setItems(FXCollections.observableArrayList("Admin", "Attendee", "Organizer"));
        genderComboBox.setItems(FXCollections.observableArrayList("MALE", "FEMALE", "OTHER"));
        roleComboBoxAdmin.setItems(FXCollections.observableArrayList("SuperAdmin", "RoomManager", "CategoryManager"));
        roleComboBox.setOnAction(this::handleRoleSelection);
        registerButton.setOnAction(this::handleRegister);
    }

    public void switchToLoginStage(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/login.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRoleSelection(ActionEvent event) {
        String selectedRole = roleComboBox.getValue();

        adminFields.setVisible(false); adminFields.setManaged(false);
        attendeeFields.setVisible(false); attendeeFields.setManaged(false);
        organizerFields.setVisible(false); organizerFields.setManaged(false);

        switch (selectedRole) {
            case "Admin" -> {
                adminFields.setVisible(true); adminFields.setManaged(true);
            }
            case "Attendee" -> {
                attendeeFields.setVisible(true); attendeeFields.setManaged(true);
            }
            case "Organizer" -> {
                organizerFields.setVisible(true); organizerFields.setManaged(true);
            }
        }
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String role = roleComboBox.getValue();
        String username = usernameField.getText();
        String password = passwordField.getText();
        LocalDate dob = dateOfBirthPicker.getValue();
        LocalDate year2010 = LocalDate.of(2010, 1, 1);
        if (role == null || username.isEmpty() || password.isEmpty() || dob == null ) {
            showAlert(Alert.AlertType.ERROR, "Missing required fields");
            return;
        }
        if( !dob.isBefore(year2010))  {showAlert(Alert.AlertType.ERROR, "u are younger than 15 years");return;}


        switch (role) {
            case "Admin" -> registerAdmin(username, password, dob);
            case "Attendee" -> registerAttendee(username, password, dob);
            case "Organizer" -> registerOrganizer(username, password, dob);
        }

        showAlert(Alert.AlertType.INFORMATION, "User registered successfully!");
        clearFields();
    }

    private void registerAdmin(String username, String password, LocalDate dob) {
        try {
            int startHour = Integer.parseInt(startWorkingHoursField.getText());
            int endHour = Integer.parseInt(endWorkingHoursField.getText());
            Admin.Role adminRole = Admin.Role.valueOf(roleComboBoxAdmin.getValue());

            if ( startHour < 0 || startHour > 24 || endHour < 0 || endHour > 24) {
                throw new IllegalArgumentException();
            }

            Admin admin = new Admin(username, password, dob, startHour, endHour, adminRole);
            Database.adminDB.add(admin);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Invalid admin input.");
        }
    }

    private void registerAttendee(String username, String password, LocalDate dob) {
        try {
            double balance = Double.parseDouble(balanceField.getText());
            String address = addressField.getText();
            Attendee.Gender gender = Attendee.Gender.valueOf(genderComboBox.getValue());

            if (address.isEmpty()) {
                throw new IllegalArgumentException();
            }

            Attendee attendee = new Attendee(username, password, dob, balance, address, gender);
            Database.attendeeDB.add(attendee);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Invalid attendee input.");
        }
    }

    private void registerOrganizer(String username, String password, LocalDate dob) {
        try {
            double balance = Double.parseDouble(organizerBalanceField.getText());

            Organizer organizer = new Organizer(username, password, dob, new Wallet(balance));
            Database.organizerDB.add(organizer);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Invalid organizer input.");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Registration");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        dateOfBirthPicker.setValue(null);
        startWorkingHoursField.clear();
        endWorkingHoursField.clear();
        roleComboBoxAdmin.setValue(null);
        balanceField.clear();
        addressField.clear();
        genderComboBox.setValue(null);
        organizerBalanceField.clear();
        roleComboBox.setValue(null);
        handleRoleSelection(null);
    }
}
