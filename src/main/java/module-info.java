module com.irede.java {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens com.irede.java.controllers to javafx.fxml;
    opens com.irede.java.models to javafx.base;

    exports com.irede.java;
}