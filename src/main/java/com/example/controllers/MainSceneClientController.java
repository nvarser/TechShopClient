package com.example.controllers;

import com.example.client.Client;
import com.example.entities.*;
import com.example.enumRequests.*;
import com.example.requests.Request;
import com.example.requests.RequestInterface;
import com.example.requests.Response;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.poi.ss.formula.functions.Now;


import java.io.IOException;
import java.util.Objects;

public class MainSceneClientController {
    @FXML
    private TextField MainSceneLoginTextField;
    @FXML
    private TextField MainScenePasswordTextField;
    @FXML
    private Label LabelWarningMainScene;


    private RequestInterface request = new Request();

    private Stage stage;

    private Scene scene;

    private Parent root;

    private String clientData;

    private Gson gson = new Gson();

    public void clickOnEnter(ActionEvent event) throws IOException {
        NowUser.setInstance(null);
        if( MainSceneLoginTextField.getText() == "" || MainScenePasswordTextField.getText() == ""){
            LabelWarningMainScene.setText("Вы ввели не все данные!");
        } else{
            User user = new User();
            String login = MainSceneLoginTextField.getText();
            String password = MainScenePasswordTextField.getText();
            user.setLogin(login);
            user.setPassword(password);
            request.setRequestType(RequestType.AUTH);
            request.setClientData(gson.toJson(user));
            Client.getInstance().sendingRequestToServer(gson.toJson(request));
            System.out.println(gson.toJson(request));
            MainSceneLoginTextField.setText("");
            MainScenePasswordTextField.setText("");
            Response response = Client.getInstance().acceptingReplyEnteringFromServer();
           if(response.getResponse().equals(Responses.BAD_REQUEST)){
                LabelWarningMainScene.setText("Данного пользователя нет!");
            } else{
                NowUser.setInstance(gson.fromJson(response.getServerData(), User.class));
                System.out.println(gson.toJson(NowUser.getInstance()));
                choosingRoot(NowUser.getInstance());
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    public void onTextFieldClicked(){
        LabelWarningMainScene.setText("");
    }

    public void clickOnSignIn(ActionEvent event) throws IOException {
        NowUser.setInstance(null);
        root = FXMLLoader.load(getClass().getResource("RegistrationClient.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void choosingRoot(User user){
        try {
            if (Objects.equals(user.getRole(), "S")) {
                root = FXMLLoader.load(getClass().getResource("MainSceneSeller.fxml"));
            } else if (Objects.equals(user.getRole(), "C")) {
                root = FXMLLoader.load(getClass().getResource("MainSceneShopClient.fxml"));
            } else if (Objects.equals(user.getRole(), "A")) {
                root = FXMLLoader.load(getClass().getResource("MainSceneAdministrator.fxml"));
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}