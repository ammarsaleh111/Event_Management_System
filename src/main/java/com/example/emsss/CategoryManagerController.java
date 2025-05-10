package com.example.emsss;
import com.example.emsss.DB.Database;
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
import java.io.IOException;
import com.example.emsss.model.*;
public class CategoryManagerController {

    @FXML
    private TextField categoryNameField;

    @FXML
    private TextField categoryIdField;
    
    @FXML 
    private TextField startWorkingHoursField;
    
    @FXML 
    private TextField endWorkingHoursField;

    @FXML
    private TextArea dashboardTextArea;

    private Admin admin2;


    public void initialize() {
    }

    public void setAdmin2(Admin admin2) {
        this.admin2 = admin2;
        showDashboard();
    }

    @FXML
    public void setStartWorkingHoursAction() {
        try {
            int start = Integer.parseInt(startWorkingHoursField.getText());

            if (start < 0 || start > 24) {
                throw new IllegalArgumentException("Hour out of range");
            }
            admin2.setStartWorkingHours(start);
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
            admin2.setEndWorkingHours(end);
            showDashboard();
            showAlert("End working hour set to " + end);
            endWorkingHoursField.clear();
        } catch (IllegalArgumentException e) {
            showAlert("Invalid input for end working hours. Please enter a number between 0 and 24.");
        }
    }

    @FXML
    private void addCategory() {
        String name = categoryNameField.getText().trim();

        if (name.isEmpty()) {
            showAlert("Please enter a category name.");
            return;
        }

        if (Category.isNameExists(name)) {
            showAlert("Category name already exists: " + name);
            return;
        }


        if (admin2.addCategory(name)) {
            showAlert("Category added successfully!");
            clearFields();
            showDashboard();
        } else {
            showAlert("You are not authorized to add categories.");
        }
    }

    @FXML
    private void updateCategory() {
        try {
            int id = Integer.parseInt(categoryIdField.getText().trim());
            String newName = categoryNameField.getText().trim();

            if (newName.isEmpty()) {
                showAlert("Please enter a new category name.");
                return;
            }

            if (Category.isNameExists(newName)) {
                showAlert("New category name already exists!");
                return;
            }

            if (admin2.updateCategory(id, newName)) {
                showAlert("Category updated successfully!");
            } else {
                showAlert("Category not found or you are not authorized.");
            }

            clearFields();
            showDashboard();
        } catch (NumberFormatException e) {
            showAlert("Invalid ID format.");
        }
    }

    @FXML
    private void deleteCategory() {
        try {
            int id = Integer.parseInt(categoryIdField.getText().trim());

            if (admin2.deleteCategory(id)) {
                showAlert("Category deleted successfully!");
            } else {
                showAlert("Category not found or you are not authorized.");
            }

            clearFields();
            showDashboard();
        } catch (NumberFormatException e) {
            showAlert("Invalid ID format.");
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
            showAlert("Failed to return to Admin Choosing screen.");
        }
    }


    @FXML
    private void showDashboard() {
        StringBuilder dashboardContent = new StringBuilder();
        dashboardContent.append("\n==========  Category Dashboard ==========\n");
        dashboardContent.append("\n--- Working Hours ---\n");
        dashboardContent.append("Start: ").append(admin2.getStartWorkingHours()).append("\n");
        dashboardContent.append("End: ").append(admin2.getEndWorkingHours()).append("\n");

        dashboardContent.append("\n==========  Categories ==========\n");
        for (Category category : Database.categoryDB) {
            dashboardContent.append(category).append("  --> id: ").append(category.getId()).append("\n");
        }

        dashboardContent.append("=========================================\n");

        dashboardTextArea.setText(dashboardContent.toString());
    }

    private void clearFields() {
        categoryNameField.clear();
        categoryIdField.clear();
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Category Manager");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
