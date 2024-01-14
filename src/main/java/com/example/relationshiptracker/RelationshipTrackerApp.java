package com.example.relationshiptracker;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Properties;
public class RelationshipTrackerApp extends Application {
    private static final Logger logger = LoggerFactory.getLogger(RelationshipTrackerApp.class);
    public static void main(String[] args) {
        launch(args);
    }
    private Properties properties;
    private DatabaseConnector databaseConnector;
    @Override
    public void init() {
        logger.info("Initializing Relationship Tracker App");
        // Load properties
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            logger.error("Error loading config.properties", e);
        }
        databaseConnector = new DatabaseConnector();
        databaseConnector.createTable();
    }
    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Relationship Tracker App");
        primaryStage.setTitle("Relationship Tracker");

        // UI Components
        Label titleLabel = new Label("Enter the start date of your relationship:");
        DatePicker datePicker = new DatePicker();
        Button calculateButton = new Button("Calculate");
        Label resultLabel = new Label();
        calculateButton.setOnAction(event -> {
            logger.debug("Calculate button clicked");
            LocalDate startDate = datePicker.getValue();
            if (startDate != null) {
                long daysTogether = calculateDaysBetween(startDate, LocalDate.now());
                resultLabel.setText("You've been together for " + daysTogether + " days!");
                logger.info("Calculated days together: {}", daysTogether);
                String partner1 = "Qeto";  // Replace with actual partner names
                String partner2 = "Kote";
                databaseConnector.insertData(partner1, partner2, java.sql.Date.valueOf(startDate));
            } else {
                resultLabel.setText("Please select a valid start date.");
                logger.warn("Invalid start date selected");
            }
        });
        Button fetchDataButton = new Button("Fetch Data");
        fetchDataButton.setOnAction(event -> {
            databaseConnector.fetchData();
        });
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(titleLabel, datePicker, calculateButton, resultLabel);

        Scene scene = new Scene(layout, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    long calculateDaysBetween(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate).getDays();
    }
}
