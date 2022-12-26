package com.example.controllers;

import com.example.client.Client;
import com.example.entities.*;
import com.example.enumRequests.RequestType;
import com.example.enumRequests.Responses;
import com.example.requests.Request;
import com.example.requests.RequestInterface;
import com.example.requests.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class MainSceneShopClientController implements Initializable {
    private Stage stage;

    private Scene scene;

    private RequestInterface request = new Request();

    private Parent root;
    @FXML
    private Label BalanceCardLabel;

    @FXML
    private Button ButtonSwitchSides;

    @FXML
    private TextField ClientCVVCardField;

    @FXML
    private TextField ClientDateCardField;

    @FXML
    private TextField ClientSerialNumberCardField;

    @FXML
    private ImageView ImageCard;

    @FXML
    private TableColumn<ShopCart, Integer> OrderAmountProductClient;

    @FXML
    private TableColumn<ShopCart, Integer> OrderCartIdClient;

    @FXML
    private TableColumn<ShopCart, Integer> OrderProductIdClient;

    @FXML
    private TableColumn<ShopCart, String> OrderStatusClient;

    @FXML
    private TableColumn<Product, Integer> ProductAmountC;

    @FXML
    private TableColumn<Product, Integer> ProductAmountSellsC;

    @FXML
    private TableColumn<Product, String> ProductFirmC;

    @FXML
    private TableColumn<Product, Integer> ProductIdC;

    @FXML
    private TableColumn<Product, String> ProductNameC;

    @FXML
    private TableColumn<Product, Float> ProductPriceC;

    @FXML
    private TableColumn<Product, Float> ProductRateC;

    @FXML
    private TableView<Product> TableProductS;
    @FXML
    private TableView<ShopCart> TableOrdersC;

    @FXML
    private TextField ProductCSearchByIdField;
    @FXML
    private TextField AddBalanceTextField;
    @FXML
    private TextField AmountOfAddingProduct;
    @FXML
    private Label WarningLabelProductC;
    @FXML
    private Label WarningLabelCard;
    @FXML
    private Label WarningLabelOrder;
    private static int clicked = 0;
    private static int clickedBalance = 0;
    private static int index;
    private static Card card = new Card();
    private ArrayList<ShopCart> shopCarts = new ArrayList<ShopCart>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProductFirmC.setCellValueFactory(new PropertyValueFactory<Product,String>("firmName"));
        ProductAmountC.setCellValueFactory(new PropertyValueFactory<Product,Integer>("pamount"));
        ProductIdC.setCellValueFactory(new PropertyValueFactory<Product,Integer>("id_product"));
        ProductNameC.setCellValueFactory(new PropertyValueFactory<Product,String>("pname"));
        ProductAmountSellsC.setCellValueFactory(new PropertyValueFactory<Product,Integer>("amount_sells"));
        ProductPriceC.setCellValueFactory(new PropertyValueFactory<Product,Float>("price"));
        ProductRateC.setCellValueFactory(new PropertyValueFactory<Product,Float>("rate"));
        acceptingProductTab();

        card.setId_client(NowUser.getInstance().getId());
        request.setRequestType(RequestType.SEARCH_CARD);
        request.setClientData(new Gson().toJson(card));
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response responseAddCard = Client.getInstance().acceptingReplyEnteringFromServer();
        if(!responseAddCard.getResponse().equals(Responses.BAD_REQUEST)) {
            card = (Card) new Gson().fromJson(responseAddCard.getServerData(), Card.class);
            ClientSerialNumberCardField.setText(card.getSerialNumber());
            ClientDateCardField.setText(card.getDate());
            ClientCVVCardField.setText(card.getCvv());
            BalanceCardLabel.setText(String.valueOf(card.getSum()));
            ClientSerialNumberCardField.setDisable(true);
            ClientDateCardField.setDisable(true);
            ClientCVVCardField.setDisable(true);
        }

        OrderAmountProductClient.setCellValueFactory(new PropertyValueFactory<ShopCart,Integer>("amount"));
        OrderCartIdClient.setCellValueFactory(new PropertyValueFactory<ShopCart, Integer>("id"));
        OrderProductIdClient.setCellValueFactory(new PropertyValueFactory<ShopCart, Integer>("id_product"));
        OrderStatusClient.setCellValueFactory(new PropertyValueFactory<ShopCart, String>("status"));
        acceptingOrdersTab();
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

    private void acceptingProductTab(){
        request.setRequestType(RequestType.SHOW_PRODUCTS);
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
        if(!response.getResponse().equals(Responses.BAD_REQUEST)){
            Type type = new TypeToken<ArrayList<Product>>(){}.getType();
            ArrayList<Product> users = new Gson().fromJson(response.getServerData(),type);
            ObservableList<Product> list = FXCollections.observableArrayList(users);
            TableProductS.setItems(list);
            ProductCSearchByIdField.setText("");
        }
    }

    private void acceptingOrdersTab(){
        request.setRequestType(RequestType.SHOW_ORDERS_CLIENT);
        request.setClientData(String.valueOf(card.getId_client()));
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
        if (!response.getResponse().equals(Responses.BAD_REQUEST)) {
            Type type = new TypeToken<ArrayList<ShopCart>>() {}.getType();
            ArrayList<ShopCart> shopCartArrayList = new Gson().fromJson(response.getServerData(), type);
            ObservableList<ShopCart> list = FXCollections.observableArrayList(shopCartArrayList);
            TableOrdersC.setItems(list);
            ProductCSearchByIdField.setText("");
        }
    }

    public void searchingProductByName(ActionEvent event){
        WarningLabelProductC.setText("");
        if(ProductCSearchByIdField.getText() == "") WarningLabelProductC.setText("Вы не ввели логин!");
        else{
            request.setRequestType(RequestType.SEARCH_PRODUCT_NAME);
            request.setClientData(ProductCSearchByIdField.getText());
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response response = Client.getInstance().acceptingReplyEnteringFromServer();
            if(response.getResponse().equals(Responses.BAD_REQUEST)){
                WarningLabelProductC.setText("Такого id нет!");
            } else{
                Type type = new TypeToken<ArrayList<Product>>(){}.getType();
                ArrayList<Product> product = new Gson().fromJson(response.getServerData(),type);
                ObservableList<Product> list = FXCollections.observableArrayList(product);
                TableProductS.setItems(list);
                ProductCSearchByIdField.setText("");
            }
        }
    }

    public void refreshingTableProduct() {
        request.setRequestType(RequestType.SHOW_PRODUCTS);
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
        if (!response.getResponse().equals(Responses.BAD_REQUEST)) {
            Type type = new TypeToken<ArrayList<Product>>() {
            }.getType();
            ArrayList<Product> users = new Gson().fromJson(response.getServerData(), type);
            ObservableList<Product> list = FXCollections.observableArrayList(users);
            TableProductS.setItems(list);
            ProductCSearchByIdField.setText("");
        }
    }

    public void refreshingTableOrder(){
        request.setRequestType(RequestType.SHOW_ORDERS_CLIENT);
        request.setClientData(String.valueOf(card.getId_client()));
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
        if (!response.getResponse().equals(Responses.BAD_REQUEST)) {
            Type type = new TypeToken<ArrayList<ShopCart>>() {
            }.getType();
            ArrayList<ShopCart> shopCartArrayList = new Gson().fromJson(response.getServerData(), type);
            ObservableList<ShopCart> list = FXCollections.observableArrayList(shopCartArrayList);
            TableOrdersC.setItems(list);
            ProductCSearchByIdField.setText("");
        }
    }

    @FXML
    public void selectingProduct(MouseEvent event){

    }

    public void clickedOnSwitchingSides(){
        String regex = "\\d+";
        if(card.getCvv() != null || card.getDate() != null || card.getSerialNumber() != null){

        }
        if(ClientSerialNumberCardField.getText().equals("") || ClientDateCardField.getText().equals("")){
            WarningLabelCard.setText("Вы ввели не все данные!");
            return;
        }
        if(ClientSerialNumberCardField.getText().length() < 16 || ClientDateCardField.getText().length() < 5){
            WarningLabelCard.setText("Ошибка ввода данных!");
            return;
        }
        if(!ClientSerialNumberCardField.getText().matches(regex)){
            WarningLabelCard.setText("Номер должен содержать цифры!");
            return;
        }
        if(clicked == 0 || clicked == 2){
            card.setSerialNumber(ClientSerialNumberCardField.getText());
            card.setDate(ClientDateCardField.getText());
            clicked = 1;
            RotateTransition rotate = new RotateTransition();
            FadeTransition fade = new FadeTransition();
            rotate.setNode(ImageCard);
            rotate.setInterpolator(Interpolator.LINEAR);
            rotate.setByAngle(180);
            rotate.setAxis(Rotate.Y_AXIS);
            ClientSerialNumberCardField.setVisible(false);
            ClientDateCardField.setVisible(false);
            rotate.play();
            fade.setDuration(Duration.millis(3000));
            fade.setInterpolator(Interpolator.LINEAR);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.setNode(ClientCVVCardField);
            fade.play();
            ClientCVVCardField.setVisible(true);
        } else{
            if(ClientCVVCardField.getText().equals("")){
                WarningLabelCard.setText("Вы ввели не все данные!");
                return;
            }
            if(!ClientCVVCardField.getText().matches(regex)) {
                WarningLabelCard.setText("CVV должен содержать цифры!");
                return;
            }
            card.setCvv(ClientCVVCardField.getText());
            clicked = 2;
            RotateTransition rotate = new RotateTransition();
            FadeTransition fade = new FadeTransition();
            FadeTransition fade1 = new FadeTransition();
            rotate.setNode(ImageCard);
            rotate.setDuration(Duration.millis(500));
            rotate.setInterpolator(Interpolator.LINEAR);
            rotate.setByAngle(180);
            rotate.setAxis(Rotate.Y_AXIS);
            ClientCVVCardField.setVisible(false);
            rotate.play();
            ClientSerialNumberCardField.setVisible(true);
            ClientDateCardField.setVisible(true);
            fade.setDuration(Duration.millis(3000));
            fade.setInterpolator(Interpolator.LINEAR);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.setNode(ClientSerialNumberCardField);
            fade.play();
            fade1.setNode(ClientDateCardField);
            fade1.setDuration(Duration.millis(3000));
            fade1.setInterpolator(Interpolator.LINEAR);
            fade1.setFromValue(0);
            fade1.setToValue(1);
            fade1.play();
        }
    }

    public void addCard(){
        if(card.getCvv() == null || card.getDate() == null || card.getSerialNumber() == null){
            WarningLabelCard.setText("Вы не ввели все данные карты!");
        }
        else{
            card.setId_client(NowUser.getInstance().getId());
            request.setRequestType(RequestType.SEARCH_CARD);
            request.setClientData(new Gson().toJson(card));
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response response = Client.getInstance().acceptingReplyEnteringFromServer();
            if(response.getResponse().equals(Responses.ACCEPTED)){
                WarningLabelCard.setText("У вас уже есть карта!");
            } else{
                card.setId_client(NowUser.getInstance().getId());
                request.setRequestType(RequestType.ADD_CARD);
                request.setClientData(new Gson().toJson(card));
                Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                Response responseAddCard = Client.getInstance().acceptingReplyEnteringFromServer();
                card = (Card) new Gson().fromJson(responseAddCard.getServerData(),Card.class);
                card.setId_client(NowUser.getInstance().getId());
                ClientSerialNumberCardField.setText(card.getSerialNumber());
                ClientDateCardField.setText(card.getDate());
                ClientCVVCardField.setText(card.getCvv());
                BalanceCardLabel.setText(String.valueOf(card.getSum()));
                ClientSerialNumberCardField.setDisable(true);
                ClientDateCardField.setDisable(true);
                ClientCVVCardField.setDisable(true);
            }
        }
    }

    public void addBalanceToCard(){
        if(clickedBalance == 0 || clickedBalance == 2){
            clickedBalance = 1;
            AddBalanceTextField.setVisible(true);
        } else{
            clickedBalance = 2;
            AddBalanceTextField.setVisible(false);
            if(AddBalanceTextField.getText().equals("")){
                WarningLabelCard.setText("Введите сумму пополнения!");
                return;
            } else{
                card.setAddingBalance(Float.parseFloat(AddBalanceTextField.getText()));
                request.setRequestType(RequestType.ADD_BALANCE_CARD);
                request.setClientData(new Gson().toJson(card));
                Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                Response responseAddBalanceCard = Client.getInstance().acceptingReplyEnteringFromServer();
                card.setSum(Float.parseFloat(responseAddBalanceCard.getServerData()));
                System.out.println(Float.parseFloat(responseAddBalanceCard.getServerData()));
                BalanceCardLabel.setText(String.valueOf(card.getSum()));
            }
        }
    }

    public void addToCart(){
        index = -1;
        index = TableProductS.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index <= -1){
            WarningLabelProductC.setText("Вы не выбрали товар!");
            return;
        }
        if(AmountOfAddingProduct.getText().equals("")){
            WarningLabelProductC.setText("Вы не выбрали количество товара!");
            return;
        }
        String regex = "\\d+";
        if(!AmountOfAddingProduct.getText().matches(regex)){
            WarningLabelProductC.setText("Введите целое число!");
            return;
        }
        ShopCart shopCart = new ShopCart();
        shopCart.setId_product(ProductIdC.getCellData(index));
        shopCart.setAmount(Integer.parseInt(AmountOfAddingProduct.getText()));
        System.out.println("Id client " + card.getId_client());
        shopCart.setId_client(card.getId_client());
        shopCart.setStatus("NeedPay");
        request.setRequestType(RequestType.ADD_ORDER);
        request.setClientData(new Gson().toJson(shopCart));
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response responseAddOrder = Client.getInstance().acceptingReplyEnteringFromServer();
        if(responseAddOrder.getResponse().equals(Responses.BAD_REQUEST)){
            WarningLabelProductC.setText("Введите меньшее количество товара!");
        }
    }

    @FXML
    private void payForOrder(){
        index = -1;
        index = TableOrdersC.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index <= -1){
            WarningLabelOrder.setText("Вы не выбрали товар!");
            return;
        }
        if(OrderStatusClient.getCellData(index).equals("NeedPay")) {
            ShopCart shopCart = new ShopCart();
            shopCart.setId_product(OrderProductIdClient.getCellData(index));
            shopCart.setAmount(OrderAmountProductClient.getCellData(index));
            shopCart.setId_client(card.getId_client());
            shopCart.setId(OrderCartIdClient.getCellData(index));
            request.setRequestType(RequestType.PAY_FOR_ORDER);
            request.setClientData(new Gson().toJson(shopCart));
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response responseAddOrder = Client.getInstance().acceptingReplyEnteringFromServer();
            if (responseAddOrder.getResponse().equals(Responses.BAD_REQUEST)) {
                WarningLabelOrder.setText("Не достаточно средств на балансе!");
            } else {
                card.setId_client(NowUser.getInstance().getId());
                request.setRequestType(RequestType.SEARCH_CARD);
                request.setClientData(new Gson().toJson(card));
                Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                Response responseAddCard = Client.getInstance().acceptingReplyEnteringFromServer();
                if (!responseAddCard.getResponse().equals(Responses.BAD_REQUEST)) {
                    card = (Card) new Gson().fromJson(responseAddCard.getServerData(), Card.class);
                    ClientSerialNumberCardField.setText(card.getSerialNumber());
                    ClientDateCardField.setText(card.getDate());
                    ClientCVVCardField.setText(card.getCvv());
                    BalanceCardLabel.setText(String.valueOf(card.getSum()));
                    ClientSerialNumberCardField.setDisable(true);
                    ClientDateCardField.setDisable(true);
                    ClientCVVCardField.setDisable(true);
                }
            }
        }
    }
    @FXML
    public void removeFromCart(){
        index = -1;
        index = TableOrdersC.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index <= -1){
            WarningLabelOrder.setText("Вы не выбрали товар!");
            return;
        }
        if(OrderStatusClient.getCellData(index).equals("NeedPay")) {
            ShopCart shopCart = new ShopCart();
            shopCart.setId_product(OrderProductIdClient.getCellData(index));
            shopCart.setAmount(OrderAmountProductClient.getCellData(index));
            shopCart.setId_client(card.getId_client());
            shopCart.setId(OrderCartIdClient.getCellData(index));
            request.setRequestType(RequestType.REMOVE_PRODUCT_FROM_CART);
            request.setClientData(new Gson().toJson(shopCart));
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response responseAddOrder = Client.getInstance().acceptingReplyEnteringFromServer();
            if (responseAddOrder.getResponse().equals(Responses.BAD_REQUEST)) {
                WarningLabelOrder.setText("чето не так");
            }
        }
    }
}
