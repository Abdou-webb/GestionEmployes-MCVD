module com.myapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires jbcrypt;
    requires java.sql;
    requires mysql.connector.j;

    opens com.myapp.controllers to javafx.fxml;
    // Autorise le TableView à lire les données des classes models
    opens com.myapp.models to javafx.base;
    opens images to javafx.graphics, javafx.fxml;

    exports com.myapp;
    opens com.myapp.utils to javafx.base;
}