package com.myapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class SceneManager {
    private static Stage primaryStage;

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void switchScene(String fxmlFile, String title) throws IOException {
        String path = "/views/" + fxmlFile;
        URL resource = SceneManager.class.getResource(path);

        if (resource == null) {
            throw new IOException("Fichier introuvable : " + path);
        }

        FXMLLoader loader = new FXMLLoader(resource);
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);

        // pour l'effet plein ecran
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}