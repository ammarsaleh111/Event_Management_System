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
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Label registerLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roleComboBox.setItems(FXCollections.observableArrayList("Admin", "Attendee", "Organizer"));
    }

    // Open Registration Stage
    @FXML
    public void switchToRegistrationStage(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/register.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/register.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handle Login Logic
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            showAlert(Alert.AlertType.ERROR, "Please enter all fields.");
            return;
        }

        boolean success = switch (role) {
            case "Admin" -> authenticateAdmin(username, password, event);
            case "Attendee" -> authenticateAttendee(username, password, event);
            case "Organizer" -> authenticateOrganizer(username, password, event);
            default -> false;
        };

        if (!success) {
            showAlert(Alert.AlertType.ERROR, "Invalid credentials or role.");
        }
    }

    private boolean authenticateAdmin(String username, String password, ActionEvent event) {
        for (Admin admin : Database.adminDB) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                try {
                    if(admin.getRole()== Admin.Role.SuperAdmin){
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SuperAdminScene.fxml"));
                            Parent root = loader.load();
                            SuperAdminController controller = loader.getController();
                            controller.setAdmin(admin);
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root);
                            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                            stage.setScene(scene);
                            stage.setMaximized(true);
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            showAlert(Alert.AlertType.ERROR, "Scene loading failed.");
                        }
                    }
                    else if(admin.getRole()== Admin.Role.RoomManager){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RoomManagerScene.fxml"));
                        Parent root = loader.load();
                        RoomManagerController controller = loader.getController();
                        controller.setAdmin1(admin);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                        stage.setScene(scene);
                        stage.setMaximized(true);
                        stage.show();
                    }
                    else if(admin.getRole()== Admin.Role.CategoryManager){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CategoryManagerScene.fxml"));
                        Parent root = loader.load();
                        CategoryManagerController controller = loader.getController();
                        controller.setAdmin2(admin);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                        stage.setScene(scene);
                        stage.setMaximized(true);
                        stage.show();                     }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Failed to load Admin Scene.");
                    return false;
                }

            }
        }
        return false;
    }


    private boolean authenticateAttendee(String username, String password, ActionEvent event) {
        for (Attendee attendee : Database.attendeeDB) {
            if (attendee.getUsername().equals(username) && attendee.getPassword().equals(password)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AttendeeScene.fxml"));
                    Parent root = loader.load();

                    AttendeeController controller = loader.getController();
                    controller.setAttendee(attendee);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.show();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Failed to load Attendee Scene.");
                    return false;
                }
            }
        }
        return false;
    }


    private boolean authenticateOrganizer(String username, String password, ActionEvent event) {
        for (Organizer organizer : Database.organizerDB) {
            if (organizer.getUsername().equals(username) && organizer.getPassword().equals(password)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/OrganizerDashboard.fxml"));
                    Parent root = loader.load();

                    OrganizerController controller = loader.getController();
                    controller.setOrganizer(organizer);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/OrganizerDashboard.css").toExternalForm());
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.show();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Failed to load Organizer Dashboard.");
                    return false;
                }
            }
        }
        return false;
    }
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}