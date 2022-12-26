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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainSceneSellerController implements Initializable {
    private Stage stage;

    private Scene scene;

    private RequestInterface request = new Request();

    private Parent root;
    @FXML
    private TableColumn<ShopClient, Integer> ClientIdSeller;

    @FXML
    private TableColumn<ShopClient, String> ClientNameSeller;

    @FXML
    private TableColumn<ShopClient, String> ClientSurnameSeller;
    @FXML
    private TableColumn<Product, Integer> ProductAmountS;

    @FXML
    private TableColumn<Product, Integer> ProductAmountSellsS;

    @FXML
    private TableColumn<Product, String> ProductFirmS;

    @FXML
    private TableColumn<Product, Integer> ProductIdS;

    @FXML
    private TableColumn<Product, String> ProductNameS;

    @FXML
    private TableColumn<Product, Float> ProductPriceS;

    @FXML
    private TableColumn<Product, Float> ProductRateS;
    @FXML
    private TableColumn<ShopCart, Integer> OrderAmountProductSeller;

    @FXML
    private TableColumn<ShopCart, Integer> OrderCartIdSeller;

    @FXML
    private TableColumn<ShopCart, Integer> OrderClientIdSeller;

    @FXML
    private TableColumn<ShopCart, Integer> OrderProductIdSeller;

    @FXML
    private TableColumn<ShopCart, String> OrderStatus;

    @FXML
    private TableView<Product> TableProductS;
    @FXML
    private TableView<ShopClient> TableClients;
    @FXML
    private TableView<ShopCart> TableOrdersSeller;
    @FXML
    private TextField ProductSearchByIdField;

    @FXML
    private Label WarningLabelProduct;
    @FXML
    private Label WarningLabelOrdersSeller;
    private static int index;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProductFirmS.setCellValueFactory(new PropertyValueFactory<Product,String>("firmName"));
        ProductAmountS.setCellValueFactory(new PropertyValueFactory<Product,Integer>("pamount"));
        ProductIdS.setCellValueFactory(new PropertyValueFactory<Product,Integer>("id_product"));
        ProductNameS.setCellValueFactory(new PropertyValueFactory<Product,String>("pname"));
        ProductAmountSellsS.setCellValueFactory(new PropertyValueFactory<Product,Integer>("amount_sells"));
        ProductPriceS.setCellValueFactory(new PropertyValueFactory<Product,Float>("price"));
        ProductRateS.setCellValueFactory(new PropertyValueFactory<Product,Float>("rate"));
        acceptingProductTab();

        ClientIdSeller.setCellValueFactory(new PropertyValueFactory<ShopClient, Integer>("id"));
        ClientNameSeller.setCellValueFactory(new PropertyValueFactory<ShopClient, String>("name"));
        ClientSurnameSeller.setCellValueFactory(new PropertyValueFactory<ShopClient, String>("surname"));
        acceptingClientsTab();

        OrderAmountProductSeller.setCellValueFactory(new PropertyValueFactory<ShopCart, Integer>("amount"));
        OrderCartIdSeller.setCellValueFactory(new PropertyValueFactory<ShopCart, Integer>("id"));
        OrderClientIdSeller.setCellValueFactory(new PropertyValueFactory<ShopCart, Integer>("id_client"));
        OrderProductIdSeller.setCellValueFactory(new PropertyValueFactory<ShopCart, Integer>("id_product"));
        OrderStatus.setCellValueFactory(new PropertyValueFactory<ShopCart, String>("status"));
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
            ProductSearchByIdField.setText("");
        }
    }

    public void searchingProductById(ActionEvent event){
        WarningLabelProduct.setText("");
        if(ProductSearchByIdField.getText() == "") WarningLabelProduct.setText("Вы не ввели логин!");
        else{
            request.setRequestType(RequestType.SEARCH_PRODUCT);
            request.setClientData(ProductSearchByIdField.getText());
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response response = Client.getInstance().acceptingReplyEnteringFromServer();
            if(response.getResponse().equals(Responses.BAD_REQUEST)){
                WarningLabelProduct.setText("Такого id нет!");
            } else{
                Type type = new TypeToken<ArrayList<Product>>(){}.getType();
                ArrayList<Product> product = new Gson().fromJson(response.getServerData(),type);
                ObservableList<Product> list = FXCollections.observableArrayList(product);
                TableProductS.setItems(list);
                ProductSearchByIdField.setText("");
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
            ProductSearchByIdField.setText("");
        }
    }

    public void refreshingTableOrders() {
        request.setRequestType(RequestType.SHOW_ORDERS);
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
        if (!response.getResponse().equals(Responses.BAD_REQUEST)) {
            Type type = new TypeToken<ArrayList<ShopCart>>() {
            }.getType();
            ArrayList<ShopCart> shopCartArrayList = new Gson().fromJson(response.getServerData(), type);
            ObservableList<ShopCart> list = FXCollections.observableArrayList(shopCartArrayList);
            TableOrdersSeller.setItems(list);
        }
    }

    private void acceptingClientsTab(){
        request.setRequestType(RequestType.SHOW_CLIENTS);
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
        if(!response.getResponse().equals(Responses.BAD_REQUEST)){
            Type type = new TypeToken<ArrayList<ShopClient>>(){}.getType();
            ArrayList<ShopClient> sellers = new Gson().fromJson(response.getServerData(),type);
            ObservableList<ShopClient> list = FXCollections.observableArrayList(sellers);
            TableClients.setItems(list);
        }
    }

    private void acceptingOrdersTab(){
        request.setRequestType(RequestType.SHOW_ORDERS);
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
        if (!response.getResponse().equals(Responses.BAD_REQUEST)) {
            Type type = new TypeToken<ArrayList<ShopCart>>() {}.getType();
            ArrayList<ShopCart> shopCartArrayList = new Gson().fromJson(response.getServerData(), type);
            ObservableList<ShopCart> list = FXCollections.observableArrayList(shopCartArrayList);
            TableOrdersSeller.setItems(list);
        }
    }

    public void acceptOrder(){
        index = -1;
        index = TableOrdersSeller.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index <= -1){
            WarningLabelOrdersSeller.setText("Вы не выбрали заказ!");
            return;
        }
        ShopCart shopCart = new ShopCart();
        shopCart.setId(OrderCartIdSeller.getCellData(index));
        shopCart.setId_product(OrderProductIdSeller.getCellData(index));
        shopCart.setAmount(OrderAmountProductSeller.getCellData(index));
        shopCart.setId_client(OrderClientIdSeller.getCellData(index));
        shopCart.setStatus("WaitingShipping");
        request.setRequestType(RequestType.ACCEPT_ORDER);
        request.setClientData(new Gson().toJson(shopCart));
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
        if (!response.getResponse().equals(Responses.BAD_REQUEST)) {
            request.setRequestType(RequestType.SET_AMOUNT_SELLER);
            request.setClientData(String.valueOf(NowUser.getInstance().getId()));
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response responseSeller = Client.getInstance().acceptingReplyEnteringFromServer();
        }
    }

    public void denyOrder(){
        index = -1;
        index = TableOrdersSeller.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index <= -1){
            WarningLabelOrdersSeller.setText("Вы не выбрали заказ!");
            return;
        }
        ShopCart shopCart = new ShopCart();
        shopCart.setId(OrderCartIdSeller.getCellData(index));
        shopCart.setId_product(OrderProductIdSeller.getCellData(index));
        shopCart.setAmount(OrderAmountProductSeller.getCellData(index));
        shopCart.setId_client(OrderClientIdSeller.getCellData(index));
        shopCart.setStatus("Deny");
        request.setRequestType(RequestType.DENY_ORDER);
        request.setClientData(new Gson().toJson(shopCart));
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
    }
}
