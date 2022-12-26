module com.example.guiclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.apache.poi.poi;


    exports com.example.controllers;
    exports com.example.enumRequests;
    opens com.example.controllers to javafx.fxml;
    opens com.example.entities to javafx.base, com.google.gson;
    opens com.example.enumRequests to com.google.gson;
    exports com.example.requests;
    opens com.example.requests to com.google.gson;
}