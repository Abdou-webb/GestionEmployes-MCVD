module com.myapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires jbcrypt;
    requires java.sql;
    requires mysql.connector.j;
    // Autorise JavaFX à lire vos fichiers FXML et vos contrôleurs
    opens com.myapp.controllers to javafx.fxml;
    // Autorise le TableView à lire les données de vos modèles
    opens com.myapp.models to javafx.base;
    opens images to javafx.graphics, javafx.fxml;

    exports com.myapp;
    opens com.myapp.utils to javafx.base;
}