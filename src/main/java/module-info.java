module com.example.relationshiptracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;
    requires org.testng;


    opens com.example.relationshiptracker to javafx.fxml;
    exports com.example.relationshiptracker;
}