module ImproveWeatherApp {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;

    requires org.json;


    opens controllers;
    opens view;
    opens main;
}