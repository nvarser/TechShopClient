package com.example.controllers;

import com.example.client.Client;
import com.example.entities.NowUser;
import com.example.entities.User;
import com.example.enumRequests.*;
import com.example.requests.Request;
import com.example.requests.RequestInterface;
import com.example.requests.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class RegistrationSceneController {
    @FXML
    private TextField FirstNameRegistrationField;
    @FXML
    private TextField SurnameRegistrationField;
    @FXML
    private TextField EMailRegistrationField;
    @FXML
    private TextField LoginRegistrationField;
    @FXML
    private TextField PasswordRegistrationField;
    @FXML
    private Label WarningLabel;

    private Stage stage;

    private Scene scene;

    private RequestInterface request = new Request();

    private Parent root;

    public void clickOnSignUp(ActionEvent event) throws IOException {
        if (FirstNameRegistrationField.getText() == ""
                || SurnameRegistrationField.getText() == ""
                || EMailRegistrationField.getText() == ""
                || LoginRegistrationField.getText() == ""
                || PasswordRegistrationField.getText() == "") {
            WarningLabel.setText("Вы ввели не все данные!");
        } else {
            User user = new User();
            user.setLogin(LoginRegistrationField.getText());
            user.setPassword(PasswordRegistrationField.getText());
            user.setName(FirstNameRegistrationField.getText());
            user.setSurname(SurnameRegistrationField.getText());
            user.setEMail(EMailRegistrationField.getText());
            user.setRole("C");
            System.out.println(new Gson().toJson(user));
            request.setRequestType(RequestType.REG);
            request.setClientData(new Gson().toJson(user));
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response response = Client.getInstance().acceptingReplyEnteringFromServer();
            if(response.getResponse().equals(Responses.BAD_REQUEST)){
                WarningLabel.setText("Введите другой логин или почту!");
            } else{
                System.out.println("Поиск юзера!");
                request.setRequestType(RequestType.SEARCH_USER);
                request.setClientData(user.getLogin());
                Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                Response response1 = Client.getInstance().acceptingReplyEnteringFromServer();
                Type type = new TypeToken<ArrayList<User>>(){}.getType();
                ArrayList<User> users = new Gson().fromJson(response1.getServerData(),type);
                NowUser.setInstance(users.get(0));
                request.setRequestType(RequestType.ADD_CLIENT_REGISTRATION);
                request.setClientData(String.valueOf(users.get(0).getId()));
                System.out.println("Добавили клиента!!");
                Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                Response response2 = Client.getInstance().acceptingReplyEnteringFromServer();
                root = FXMLLoader.load(getClass().getResource("MainSceneShopClient.fxml"));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            }
        }
}

    public void clickedOnBack(ActionEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("MainSceneClient.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
