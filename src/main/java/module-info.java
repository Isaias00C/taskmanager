module com.irede.java {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.irede.java.controller to javafx.fxml;

    exports com.irede.java;
}