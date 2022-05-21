module com.doan.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires gson;
    requires lombok;
    requires unirest.java;
    requires java.mail;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires imageio.jxl;
    opens com.doan.client to javafx.fxml;
    exports com.doan.client;
    exports com.doan.client.Controller;
    opens com.doan.client.Controller to javafx.fxml;
    exports com.doan.client.Model;
    opens com.doan.client.Model to gson;
}