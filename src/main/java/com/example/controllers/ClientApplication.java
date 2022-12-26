package com.example.controllers;

import com.example.client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("MainSceneClient.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Tech.by");
        stage.setScene(scene);

        stage.setResizable(false);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            try {
                logout(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void logout(Stage stage) throws IOException {
        Client.getInstance().closeEverything();
        stage.close();
    }

    public static void main(String[] args) {
        Client.getInstance();
        launch();
    }
}