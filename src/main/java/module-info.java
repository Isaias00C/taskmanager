module com.irede.java {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.irede.java.controllers to javafx.fxml;

    exports com.irede.java;
}