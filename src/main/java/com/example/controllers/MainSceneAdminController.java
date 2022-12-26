package com.example.controllers;

import com.example.client.Client;
import com.example.entities.*;
import com.example.enumRequests.*;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Now;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainSceneAdminController implements Initializable{

    private Stage stage;

    private Scene scene;

    private RequestInterface request = new Request();

    private Parent root;
    @FXML
    private TableView<User> TableUsers;

    @FXML
    private TableColumn<User, String> UserEmail;

    @FXML
    private TableColumn<User, Integer> UserId;

    @FXML
    private TableColumn<User, String> UserLogin;

    @FXML
    private TableColumn<User, String> UserName;

    @FXML
    private TableColumn<User, String> UserPassword;

    @FXML
    private TableColumn<User, String> UserRole;

    @FXML
    private TableColumn<User, String> UserSurname;
    @FXML
    private TableColumn<Seller, Float> SellerHours;

    @FXML
    private TextField SellerHoursField;

    @FXML
    private TableColumn<Seller, Float> SellerHoursRate;

    @FXML
    private TableColumn<Seller, Integer> SellerId;

    @FXML
    private TableColumn<Seller, String> SellerName;

    @FXML
    private TableColumn<Seller, Float> SellerRatePerSell;

    @FXML
    private TextField SellerSearchIdField;

    @FXML
    private TableColumn<Seller, String> SellerSurname;

    @FXML
    private TableColumn<Seller, Integer> SellersSells;

    @FXML
    private TextField AddingEmailAdmin;

    @FXML
    private TextField AddingLoginAdmin;

    @FXML
    private TextField AddingNameAdmin;

    @FXML
    private PasswordField AddingPasswordAdmin;

    @FXML
    private TextField AddingSurnameAdmin;
    @FXML
    private TextField DeleteUserFieldID;

    @FXML
    private TextField LoginSearchUserField;
    @FXML
    private TextField ProfileNameEdit;
    @FXML
    private TextField ProfileSurnameEdit;
    @FXML
    private TextField ProfileEmailEdit;
    @FXML
    private TextField ProfileLoginEdit;
    @FXML
    private TextField ProfilePasswordEdit;
    @FXML
    private Button ProfileButtonEdit = new Button();

    @FXML
    private Label WarningLabel;

    @FXML
    private Tab ProductsTab;

    @FXML
    private Button ResfreshingButtonUsers;

    @FXML
    private RadioButton SelectRadioUser1;

    @FXML
    private RadioButton SelectRadioUser2;

    @FXML
    private RadioButton SelectRadioUser3;

    @FXML
    private TextField AddingAmountAdminProduct;
    @FXML
    private TextField AddFirmNameField;
    @FXML
    private TextField DeleteFirmIdField;
    @FXML
    private TableColumn<Firm, Integer> FirmId;
    @FXML
    private TableColumn<Firm, String> FirmName;
    @FXML
    private TableView<Firm> TableFirm;
    @FXML
    private TextField SearchFirmIdField;
    @FXML
    private TextField AddingNameAdminProduct;

    @FXML
    private TextField AddingPriceAdminProduct;
    @FXML
    private TextField ProductDeleteIdField;
    @FXML
    private TextField ProductSearchIdField;

    @FXML
    private TextField FirmFieldAdminExistId;
    @FXML
    private TextField SellerHoursRateField;
    @FXML
    private TextField SellerRatePerSellField;
    @FXML
    private TextField SellerIdSalaryField;
    @FXML
    private TextField SellerNameSalaryField;
    @FXML
    private TextField SellerSurnameSalaryField;
    @FXML
    private TextField SellerHoursSalaryField;
    @FXML
    private TextField SellerHoursRateSalaryField;
    @FXML
    private TextField SellerAmountSellsSalaryField;
    @FXML
    private TextField SellerRatePerSellSalaryField;
    @FXML
    private TextField SellerSalaryField;

    @FXML
    private TextField ReportSellerIdField;

    @FXML
    private TableColumn<Product, Integer> ProductAmount;

    @FXML
    private TableColumn<Product, Integer> ProductAmountSells;

    @FXML
    private TableColumn<Product, String> ProductFirm;

    @FXML
    private TableColumn<Product, Integer> ProductId;

    @FXML
    private TableColumn<Product, String> ProductName;

    @FXML
    private TableColumn<Product, Float> ProductPrice;

    @FXML
    private TableColumn<Product, Float> ProductRate;

    @FXML
    private TableView<Product> TableProduct;
    @FXML
    private TableView<Seller> TableSellers;
    @FXML
    private Label WarningLabelProducts;
    @FXML
    private Label WarningLabelFirms;
    @FXML
    private Label WarningLabelSellers;
    @FXML
    private Label LabelSalaryWarning;
    @FXML
    private Label SalaryLabel;
    @FXML
    private Label CreateReportLabel;
    @FXML
    private Label WarningLabelSellerReport;
    @FXML
    private Label ProfileName;
    @FXML
    private Label ProfileSurname;
    @FXML
    private Label ProfileEmail;
    @FXML
    private Label ProfileLogin;
    @FXML
    private Label ProfilePassword;
    @FXML
    private Label ProfileRole;
    @FXML
    private Button CalculateButton = new Button();

    private static int index;
    private static int clicked = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserId.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
        UserEmail.setCellValueFactory(new PropertyValueFactory<User,String>("EMail"));
        UserRole.setCellValueFactory(new PropertyValueFactory<User,String>("role"));
        UserLogin.setCellValueFactory(new PropertyValueFactory<User,String>("login"));
        UserPassword.setCellValueFactory(new PropertyValueFactory<User,String>("password"));
        UserName.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        UserSurname.setCellValueFactory(new PropertyValueFactory<User,String>("surname"));
        acceptingUsersTab();

        ProductFirm.setCellValueFactory(new PropertyValueFactory<Product,String>("firmName"));
        ProductAmount.setCellValueFactory(new PropertyValueFactory<Product,Integer>("pamount"));
        ProductId.setCellValueFactory(new PropertyValueFactory<Product,Integer>("id_product"));
        ProductName.setCellValueFactory(new PropertyValueFactory<Product,String>("pname"));
        ProductAmountSells.setCellValueFactory(new PropertyValueFactory<Product,Integer>("amount_sells"));
        ProductPrice.setCellValueFactory(new PropertyValueFactory<Product,Float>("price"));
        ProductRate.setCellValueFactory(new PropertyValueFactory<Product,Float>("rate"));
        acceptingProductTab();

        FirmName.setCellValueFactory(new PropertyValueFactory<Firm, String>("firm_name"));
        FirmId.setCellValueFactory(new PropertyValueFactory<Firm, Integer>("id_firm"));
        acceptingFirmTab();

        SellerId.setCellValueFactory(new PropertyValueFactory<Seller,Integer>("id"));
        SellerHours.setCellValueFactory(new PropertyValueFactory<Seller,Float>("hours"));
        SellerHoursRate.setCellValueFactory(new PropertyValueFactory<Seller,Float>("ratePerHour"));
        SellerRatePerSell.setCellValueFactory(new PropertyValueFactory<Seller,Float>("ratePerSell"));
        SellerName.setCellValueFactory(new PropertyValueFactory<Seller,String>("name"));
        SellersSells.setCellValueFactory(new PropertyValueFactory<Seller, Integer>("amountSells"));
        SellerSurname.setCellValueFactory(new PropertyValueFactory<Seller,String>("surname"));
        acceptingSellersTab();

        ProfileName.setText(NowUser.getInstance().getName());
        ProfileSurname.setText(NowUser.getInstance().getSurname());
        ProfileEmail.setText(NowUser.getInstance().getEMail());
        ProfileLogin.setText(NowUser.getInstance().getLogin());
        ProfilePassword.setText(NowUser.getInstance().getPassword());
        ProfileRole.setText("Administrator");
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

    public void deletingUserById(ActionEvent event){
        String regex = "\\d+";
        WarningLabel.setText("");
        if(DeleteUserFieldID.getText() == "") WarningLabel.setText("Введите id!");
        else{
            if(DeleteUserFieldID.getText().matches(regex)){
                request.setRequestType(RequestType.DELETE_USER);
                request.setClientData(DeleteUserFieldID.getText());
                Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                Response response = Client.getInstance().acceptingReplyEnteringFromServer();
                if(response.getResponse().equals(Responses.BAD_REQUEST)){
                    WarningLabel.setText("Такого id нет!");
                }
                DeleteUserFieldID.setText("");
            } else{
                WarningLabel.setText("Id должен содержать только цифры!");
            }
        }
    }

    public void searchingUserByLogin(ActionEvent event){
        WarningLabel.setText("");
        if(LoginSearchUserField.getText() == "") WarningLabel.setText("Вы не ввели логин!");
        else{
            request.setRequestType(RequestType.SEARCH_USER);
            request.setClientData(LoginSearchUserField.getText());
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response response = Client.getInstance().acceptingReplyEnteringFromServer();
            if(response.getResponse().equals(Responses.BAD_REQUEST)){
                WarningLabel.setText("Такого логина нет!");
            } else{
                Type type = new TypeToken<ArrayList<User>>(){}.getType();
                ArrayList<User> users = new Gson().fromJson(response.getServerData(),type);
                ObservableList<User> list = FXCollections.observableArrayList(users);
                TableUsers.setItems(list);
                LoginSearchUserField.setText("");
            }
        }
    }

    public void refreshingTableUser(){
        request.setRequestType(RequestType.SHOW_USERS);
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
        if(!response.getResponse().equals(Responses.BAD_REQUEST)){
            Type type = new TypeToken<ArrayList<User>>(){}.getType();
            ArrayList<User> users = new Gson().fromJson(response.getServerData(),type);
            ObservableList<User> list = FXCollections.observableArrayList(users);
            TableUsers.setItems(list);
            AddingEmailAdmin.setText("");
            AddingLoginAdmin.setText("");
            AddingPasswordAdmin.setText("");
            AddingNameAdmin.setText("");
            AddingSurnameAdmin.setText("");
        }
    }

    public void addingUser(ActionEvent event){
        if (AddingEmailAdmin.getText() == ""
                || AddingLoginAdmin.getText() == ""
                || AddingPasswordAdmin.getText() == ""
                || AddingNameAdmin.getText() == ""
                || AddingSurnameAdmin.getText() == "") {
            WarningLabel.setText("Вы ввели не все данные!");
        } else {
            User user = new User();
            user.setLogin(AddingLoginAdmin.getText());
            user.setPassword(AddingPasswordAdmin.getText());
            user.setName(AddingNameAdmin.getText());
            user.setSurname(AddingSurnameAdmin.getText());
            user.setEMail(AddingEmailAdmin.getText());
            if(SelectRadioUser1.isSelected()){
                user.setRole("A");
            } else if(SelectRadioUser2.isSelected()){
                user.setRole("S");
            } else {
                user.setRole("C");
            }
            request.setRequestType(RequestType.ADD_USER);
            request.setClientData(new Gson().toJson(user));
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response response = Client.getInstance().acceptingReplyEnteringFromServer();
            if(response.getResponse().equals(Responses.BAD_REQUEST)){
                WarningLabel.setText("Введите другие логин и(или) почту!");
            } else{
                if(user.getRole().equals("S")){
                    Seller seller = new Seller();
                    request.setRequestType(RequestType.SEARCH_USER);
                    request.setClientData(user.getLogin());
                    System.out.println(request.getClientData());
                    Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                    Response responseNew = Client.getInstance().acceptingReplyEnteringFromServer();
                    if (!responseNew.getResponse().equals(Responses.BAD_REQUEST)) {
                        System.out.println("Все ок, мы нашли пользователя!");
                        Type type = new TypeToken<ArrayList<User>>() {
                        }.getType();
                        ArrayList<User> users = new Gson().fromJson(responseNew.getServerData(), type);
                        for (User us : users) {
                            seller.setId_user(us.getId());
                        }
                        request.setRequestType(RequestType.ADD_SELLER);
                        request.setClientData(new Gson().toJson(seller));
                        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                        Response responseSeller = Client.getInstance().acceptingReplyEnteringFromServer();
                        if (responseSeller.getResponse().equals(Responses.BAD_REQUEST)) {
                            WarningLabel.setText("Ошибка добавления продавца!");
                        }
                    } else{
                        WarningLabel.setText("Ошибка добавления продавца!");
                    }
                }
                if(user.getRole().equals("C")){
                    ShopClient shopClient = new ShopClient();
                    request.setRequestType(RequestType.SEARCH_USER);
                    request.setClientData(user.getLogin());
                    System.out.println(request.getClientData());
                    Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                    Response responseNew = Client.getInstance().acceptingReplyEnteringFromServer();
                    if (!responseNew.getResponse().equals(Responses.BAD_REQUEST)) {
                        System.out.println("Все ок, мы нашли пользователя!");
                        Type type = new TypeToken<ArrayList<User>>() {
                        }.getType();
                        ArrayList<User> users = new Gson().fromJson(responseNew.getServerData(), type);
                        for (User us : users) {
                            shopClient.setId_user(us.getId());
                        }
                        request.setRequestType(RequestType.ADD_CLIENT);
                        request.setClientData(new Gson().toJson(shopClient));
                        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                        Response responseClient = Client.getInstance().acceptingReplyEnteringFromServer();
                        if (responseClient.getResponse().equals(Responses.BAD_REQUEST)) {
                            WarningLabel.setText("Ошибка добавления клиента!");
                        }
                    } else{
                        WarningLabel.setText("Ошибка добавления клиента!");
                    }
                }
                WarningLabel.setText("");
                AddingEmailAdmin.setText("");
                AddingLoginAdmin.setText("");
                AddingPasswordAdmin.setText("");
                AddingNameAdmin.setText("");
                AddingSurnameAdmin.setText("");
            }
        }
    }

    @FXML
    public void selectingUser(MouseEvent event){
        index = -1;
        index = TableUsers.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index <= -1) return;
        AddingSurnameAdmin.setText(UserSurname.getCellData(index).toString());
        AddingNameAdmin.setText(UserName.getCellData(index).toString());
        AddingLoginAdmin.setText(UserLogin.getCellData(index).toString());
        AddingPasswordAdmin.setText(UserPassword.getCellData(index).toString());
        AddingEmailAdmin.setText(UserEmail.getCellData(index).toString());
    }

    public void editingUser(ActionEvent event){
        if (AddingEmailAdmin.getText() == ""
                || AddingLoginAdmin.getText() == ""
                || AddingPasswordAdmin.getText() == ""
                || AddingNameAdmin.getText() == ""
                || AddingSurnameAdmin.getText() == "") {
            WarningLabel.setText("Что-то не так....");
        }else {
            User user = new User();
            user.setLogin(AddingLoginAdmin.getText());
            user.setPassword(AddingPasswordAdmin.getText());
            user.setName(AddingNameAdmin.getText());
            user.setSurname(AddingSurnameAdmin.getText());
            user.setEMail(AddingEmailAdmin.getText());
            user.setId(UserId.getCellData(index));
            if(SelectRadioUser1.isSelected()){
                user.setRole("A");
            } else if(SelectRadioUser2.isSelected()){
                user.setRole("S");
            } else {
                user.setRole("C");
            }
            request.setRequestType(RequestType.EDIT_USER);
            request.setClientData(new Gson().toJson(user));
            System.out.println(new Gson().toJson(user));
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response response = Client.getInstance().acceptingReplyEnteringFromServer();
            if(response.getResponse().equals(Responses.BAD_REQUEST)){
                WarningLabel.setText("Введите другие логин и(или) почту!");
            } else{
                if((user.getRole().equals("C") || user.getRole().equals("A"))
                        && UserRole.getCellData(index).toString().equals("S")){
                    deleteSellerByIdUser(user.getId());
                    System.out.println("Удоляем селлера из пользователя");
                }
                if(user.getRole().equals("S")){
                    Seller seller = new Seller();
                    seller.setId_user(user.getId());
                    System.out.println("Добовляем селлера из пользователя");
                    request.setRequestType(RequestType.ADD_SELLER);
                    request.setClientData(new Gson().toJson(seller));
                    Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                    Response responseSeller = Client.getInstance().acceptingReplyEnteringFromServer();
                    if(responseSeller.getResponse().equals(Responses.BAD_REQUEST)){
                        WarningLabel.setText("Ошибка добавления продавца!");
                    }
                }
                if((user.getRole().equals("A") || user.getRole().equals("S"))
                        && UserRole.getCellData(index).toString().equals("C")){
                    deleteClientByIdUser(user.getId());
                    System.out.println("Удоляем клиента из пользователя");
                }
                if(user.getRole().equals("C")){
                    ShopClient client = new ShopClient();
                    client.setId_user(user.getId());
                    System.out.println("Добовляем клиента из пользователя");
                    request.setRequestType(RequestType.ADD_CLIENT);
                    request.setClientData(new Gson().toJson(client));
                    Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                    Response responseSeller = Client.getInstance().acceptingReplyEnteringFromServer();
                    if(responseSeller.getResponse().equals(Responses.BAD_REQUEST)){
                        WarningLabel.setText("Ошибка добавления продавца!");
                    }
                }
                WarningLabel.setText("");
                AddingEmailAdmin.setText("");
                AddingLoginAdmin.setText("");
                AddingPasswordAdmin.setText("");
                AddingNameAdmin.setText("");
                AddingSurnameAdmin.setText("");
            }
        }
    }

    private void deleteClientByIdUser(Integer id){
        request.setRequestType(RequestType.DELETE_CLIENT);
        request.setClientData(id.toString());
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
    }

    public void deletingProductById(ActionEvent event){
        String regex = "\\d+";
        WarningLabelProducts.setText("");
        if(ProductDeleteIdField.getText() == "") WarningLabelProducts.setText("Введите id!");
        else{
            if(ProductDeleteIdField.getText().matches(regex)){
                request.setRequestType(RequestType.DELETE_PRODUCT);
                request.setClientData(ProductDeleteIdField.getText());
                Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                Response response = Client.getInstance().acceptingReplyEnteringFromServer();
                if(response.getResponse().equals(Responses.BAD_REQUEST)){
                    WarningLabelProducts.setText("Такого id нет!");
                }
                ProductDeleteIdField.setText("");
            } else{
                WarningLabelProducts.setText("Id должен содержать только цифры!");
            }
        }
    }

    public void searchingProductById(ActionEvent event){
        WarningLabelProducts.setText("");
        if(ProductSearchIdField.getText() == "") WarningLabelProducts.setText("Вы не ввели логин!");
        else{
            request.setRequestType(RequestType.SEARCH_PRODUCT);
            request.setClientData(ProductSearchIdField.getText());
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response response = Client.getInstance().acceptingReplyEnteringFromServer();
            if(response.getResponse().equals(Responses.BAD_REQUEST)){
                WarningLabelProducts.setText("Такого id нет!");
            } else{
                Type type = new TypeToken<ArrayList<Product>>(){}.getType();
                ArrayList<Product> product = new Gson().fromJson(response.getServerData(),type);
                ObservableList<Product> list = FXCollections.observableArrayList(product);
                TableProduct.setItems(list);
                ProductSearchIdField.setText("");
            }
        }
    }

    private void acceptingUsersTab(){
        request.setRequestType(RequestType.SHOW_USERS);
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
        if(!response.getResponse().equals(Responses.BAD_REQUEST)){
            Type type = new TypeToken<ArrayList<User>>(){}.getType();
            ArrayList<User> users = new Gson().fromJson(response.getServerData(),type);
            ObservableList<User> list = FXCollections.observableArrayList(users);
            TableUsers.setItems(list);
        }
    }

    private void acceptingProductTab(){
        request.setRequestType(RequestType.SHOW_PRODUCTS);
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
        if(!response.getResponse().equals(Responses.BAD_REQUEST)){
            Type type = new TypeToken<ArrayList<Product>>(){}.getType();
            ArrayList<Product> users = new Gson().fromJson(response.getServerData(),type);
            ObservableList<Product> list = FXCollections.observableArrayList(users);
            TableProduct.setItems(list);
            AddingNameAdminProduct.setText("");
            AddingAmountAdminProduct.setText("");
            AddingPriceAdminProduct.setText("");
        }
    }
    private void acceptingFirmTab(){
        request.setRequestType(RequestType.SHOW_FIRMS);
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
        if(!response.getResponse().equals(Responses.BAD_REQUEST)){
            Type type = new TypeToken<ArrayList<Firm>>(){}.getType();
            ArrayList<Firm> firms = new Gson().fromJson(response.getServerData(),type);
            ObservableList<Firm> list = FXCollections.observableArrayList(firms);
            TableFirm.setItems(list);
            AddFirmNameField.setText("");
            SearchFirmIdField.setText("");
            DeleteFirmIdField.setText("");
        }
    }
    private void acceptingSellersTab(){
        request.setRequestType(RequestType.SHOW_SELLERS);
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
        if(!response.getResponse().equals(Responses.BAD_REQUEST)){
            Type type = new TypeToken<ArrayList<Seller>>(){}.getType();
            ArrayList<Seller> sellers = new Gson().fromJson(response.getServerData(),type);
            ObservableList<Seller> list = FXCollections.observableArrayList(sellers);
            TableSellers.setItems(list);
            SellerSearchIdField.setText("");
            SellerHoursField.setText("");
            SellerHoursRateField.setText("");
            SellerRatePerSellField.setText("");
        }
    }

    public void refreshingTableProduct(){
        request.setRequestType(RequestType.SHOW_PRODUCTS);
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
        if(!response.getResponse().equals(Responses.BAD_REQUEST)){
            Type type = new TypeToken<ArrayList<Product>>(){}.getType();
            ArrayList<Product> users = new Gson().fromJson(response.getServerData(),type);
            ObservableList<Product> list = FXCollections.observableArrayList(users);
            TableProduct.setItems(list);
            AddingNameAdminProduct.setText("");
            AddingAmountAdminProduct.setText("");
            AddingPriceAdminProduct.setText("");
        }
    }
    public void refreshingTableFirm(){
        request.setRequestType(RequestType.SHOW_FIRMS);
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
        if(!response.getResponse().equals(Responses.BAD_REQUEST)){
            Type type = new TypeToken<ArrayList<Firm>>(){}.getType();
            ArrayList<Firm> firms = new Gson().fromJson(response.getServerData(),type);
            ObservableList<Firm> list = FXCollections.observableArrayList(firms);
            TableFirm.setItems(list);
        }
    }
    public void refreshingTableSellers(){
        request.setRequestType(RequestType.SHOW_SELLERS);
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
        if(!response.getResponse().equals(Responses.BAD_REQUEST)){
            Type type = new TypeToken<ArrayList<Seller>>(){}.getType();
            ArrayList<Seller> sellers = new Gson().fromJson(response.getServerData(),type);
            ObservableList<Seller> list = FXCollections.observableArrayList(sellers);
            TableSellers.setItems(list);
        }
    }

    public void addingProduct(ActionEvent event){
        if (AddingNameAdminProduct.getText() == ""
                || AddingAmountAdminProduct.getText() == ""
                || AddingPriceAdminProduct.getText() == "") {
            WarningLabelProducts.setText("Вы ввели не все данные!");
        } else {
            Product product = new Product();
            product.setPname(AddingNameAdminProduct.getText());
            product.setPamount(Integer.parseInt(AddingAmountAdminProduct.getText()));
            product.setPrice(Float.parseFloat(AddingPriceAdminProduct.getText()));
            request.setRequestType(RequestType.ADD_PRODUCT);
            request.setClientData(new Gson().toJson(product));
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response response = Client.getInstance().acceptingReplyEnteringFromServer();
            if(response.getResponse().equals(Responses.BAD_REQUEST)){
                WarningLabel.setText("Упс, что-то не так...");
            } else{
                WarningLabelProducts.setText("");
                AddingNameAdminProduct.setText("");
                AddingAmountAdminProduct.setText("");
                AddingPriceAdminProduct.setText("");
            }
        }
    }

    @FXML
    public void selectingProduct(MouseEvent event){
        index = -1;
        index = TableProduct.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index <= -1) return;
        AddingNameAdminProduct.setText(ProductName.getCellData(index).toString());
        AddingPriceAdminProduct.setText(ProductPrice.getCellData(index).toString());
        AddingAmountAdminProduct.setText(ProductAmount.getCellData(index).toString());
    }

    public void editingProduct(ActionEvent event){
        if (AddingNameAdminProduct.getText() == ""
                || AddingPriceAdminProduct.getText() == ""
                || AddingAmountAdminProduct.getText() == "") {
            WarningLabelProducts.setText("Что-то не так....");
        }else {
            Product product = new Product();
            product.setPname(AddingNameAdminProduct.getText());
            product.setPamount(Integer.parseInt(AddingAmountAdminProduct.getText()));
            product.setPrice(Float.parseFloat(AddingPriceAdminProduct.getText()));
            product.setId_product(ProductId.getCellData(index));
            request.setRequestType(RequestType.EDIT_PRODUCT);
            request.setClientData(new Gson().toJson(product));
            System.out.println(new Gson().toJson(product));
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response response = Client.getInstance().acceptingReplyEnteringFromServer();
            if(response.getResponse().equals(Responses.BAD_REQUEST)){
                WarningLabelProducts.setText("Что-то не так....");
            } else{
                WarningLabelProducts.setText("");
                AddingNameAdminProduct.setText("");
                AddingAmountAdminProduct.setText("");
                AddingPriceAdminProduct.setText("");
            }
        }
    }

    public void setProductFirm(){
        if (AddingNameAdminProduct.getText() == ""
                || AddingPriceAdminProduct.getText() == ""
                || AddingAmountAdminProduct.getText() == "" || FirmFieldAdminExistId.getText() == "") {
            WarningLabelProducts.setText("Вы не выбрали продукт или фирму!");
        }else {
            Product product = new Product();
            product.setPname(AddingNameAdminProduct.getText());
            product.setPamount(Integer.parseInt(AddingAmountAdminProduct.getText()));
            product.setPrice(Float.parseFloat(AddingPriceAdminProduct.getText()));
            product.setId_product(ProductId.getCellData(index));
            product.setFirm_id(Integer.parseInt(FirmFieldAdminExistId.getText()));
            request.setRequestType(RequestType.ADD_FIRM_PRODUCT);
            request.setClientData(new Gson().toJson(product));
            System.out.println(new Gson().toJson(product));
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response response = Client.getInstance().acceptingReplyEnteringFromServer();
            if(response.getResponse().equals(Responses.BAD_REQUEST)){
                WarningLabelProducts.setText("Что-то не так....");
            } else{
                WarningLabelProducts.setText("");
                AddingNameAdminProduct.setText("");
                AddingAmountAdminProduct.setText("");
                AddingPriceAdminProduct.setText("");
            }
        }
    }

    public void addingFirm(ActionEvent event){
        if (AddFirmNameField.getText() == "") {
            WarningLabelFirms.setText("Вы ввели не все данные!");
        } else {
            Firm firm = new Firm();
            firm.setFirm_name(AddFirmNameField.getText());
            request.setRequestType(RequestType.ADD_FIRM);
            request.setClientData(new Gson().toJson(firm));
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response response = Client.getInstance().acceptingReplyEnteringFromServer();
            if(response.getResponse().equals(Responses.BAD_REQUEST)){
                WarningLabelFirms.setText("Упс, что-то не так...");
            } else{
                AddFirmNameField.setText("");
            }
        }
    }

    @FXML
    public void selectingFirm(MouseEvent event){
        index = -1;
        index = TableFirm.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index <= -1) return;
        AddFirmNameField.setText(FirmName.getCellData(index).toString());
    }

    public void editingFirm(ActionEvent event){
        if (AddFirmNameField.getText() == "") {
            WarningLabelFirms.setText("Вы не выбрали фирму!");
        }else {
            Firm firm = new Firm();
            firm.setFirm_name(AddFirmNameField.getText());
            firm.setId_firm(FirmId.getCellData(index));
            request.setRequestType(RequestType.EDIT_FIRM);
            request.setClientData(new Gson().toJson(firm));
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response response = Client.getInstance().acceptingReplyEnteringFromServer();
            if(response.getResponse().equals(Responses.BAD_REQUEST)){
                WarningLabelFirms.setText("Что-то не так....");
            } else{
                WarningLabelFirms.setText("");
                AddFirmNameField.setText("");
            }
        }
    }
    public void searchingFirm(ActionEvent event){
        WarningLabelFirms.setText("");
        String regex = "\\d+";
        if(SearchFirmIdField.getText() == "") WarningLabelFirms.setText("Вы не ввели id!");
        else{
            if(SearchFirmIdField.getText().matches(regex)) {
                request.setRequestType(RequestType.SEARCH_FIRM);
                request.setClientData(SearchFirmIdField.getText());
                Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                Response response = Client.getInstance().acceptingReplyEnteringFromServer();
                if (response.getResponse().equals(Responses.BAD_REQUEST)) {
                    WarningLabelFirms.setText("Такого id нет!");
                } else {
                    Type type = new TypeToken<ArrayList<Firm>>() {
                    }.getType();
                    ArrayList<Firm> firms = new Gson().fromJson(response.getServerData(), type);
                    ObservableList<Firm> list = FXCollections.observableArrayList(firms);
                    TableFirm.setItems(list);
                    SearchFirmIdField.setText("");
                }
            } else{
                WarningLabelFirms.setText("ID должен содержать цифры!");
            }
        }
    }
    public void deletingFirmById(ActionEvent event){
        String regex = "\\d+";
        WarningLabelFirms.setText("");
        if(DeleteFirmIdField.getText() == "") WarningLabelFirms.setText("Введите id!");
        else{
            if(DeleteFirmIdField.getText().matches(regex)){
                request.setRequestType(RequestType.DELETE_FIRM);
                request.setClientData(DeleteFirmIdField.getText());
                Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                Response response = Client.getInstance().acceptingReplyEnteringFromServer();
                if(response.getResponse().equals(Responses.BAD_REQUEST)){
                    WarningLabelFirms.setText("Такого id нет!");
                }
                DeleteFirmIdField.setText("");
            } else{
                WarningLabelFirms.setText("Id должен содержать только цифры!");
            }
        }
    }
    @FXML
    public void selectingSeller(MouseEvent event){
        index = -1;
        index = TableSellers.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index <= -1) return;
        SellerHoursField.setText(SellerHours.getCellData(index).toString());
        SellerHoursRateField.setText(SellerHoursRate.getCellData(index).toString());
        SellerRatePerSellField.setText(SellerRatePerSell.getCellData(index).toString());
    }

    public void editingSeller(ActionEvent event){
        if (SellerHoursField.getText() == ""
                || SellerHoursRateField.getText() == ""
                || SellerRatePerSellField.getText() == "") {
            WarningLabelSellers.setText("Что-то не так....");
        }else {
            Seller seller = new Seller();
            seller.setHours(Float.parseFloat(SellerHoursField.getText()));
            seller.setRatePerHour(Float.parseFloat(SellerHoursRateField.getText()));
            seller.setRatePerSell(Float.parseFloat(SellerRatePerSellField.getText()));
            seller.setId(SellerId.getCellData(index));
            request.setRequestType(RequestType.EDIT_SELLERS);
            request.setClientData(new Gson().toJson(seller));
            System.out.println(new Gson().toJson(seller));
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response response = Client.getInstance().acceptingReplyEnteringFromServer();
            if(response.getResponse().equals(Responses.BAD_REQUEST)){
                WarningLabelSellers.setText("Что-то не так....");
            } else{
                WarningLabelSellers.setText("");
                SellerHoursField.setText("");
                SellerHoursRateField.setText("");
                SellerRatePerSellField.setText("");
            }
        }
    }
    public void searchingSeller(ActionEvent event){
        WarningLabelSellers.setText("");
        String regex = "\\d+";
        if(SellerSearchIdField.getText() == "") WarningLabelSellers.setText("Вы не ввели id!");
        else{
            if(SellerSearchIdField.getText().matches(regex)) {
                request.setRequestType(RequestType.SEARCH_SELLERS);
                request.setClientData(SellerSearchIdField.getText());
                Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                Response response = Client.getInstance().acceptingReplyEnteringFromServer();
                if (response.getResponse().equals(Responses.BAD_REQUEST)) {
                    WarningLabelSellers.setText("Такого id нет!");
                } else {
                    ArrayList<Seller> sellers = new ArrayList<>();
                    sellers.add(new Gson().fromJson(response.getServerData(), Seller.class));
                    ObservableList<Seller> list = FXCollections.observableArrayList(sellers);
                    TableSellers.setItems(list);
                    SellerSearchIdField.setText("");
                }
            } else{
                WarningLabelFirms.setText("ID должен содержать цифры!");
            }
        }
    }

    public void deleteSellerByIdUser(Integer id){
        request.setRequestType(RequestType.DELETE_SELLER);
        request.setClientData(id.toString());
        Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
        Response response = Client.getInstance().acceptingReplyEnteringFromServer();
    }

    public void calculateSalarySeller(){
        String regex = "\\d+";
        if(SellerIdSalaryField.getText().equals("")) LabelSalaryWarning.setText("Вы не ввели id");
        else{
            if(SellerIdSalaryField.getText().matches(regex)) {
                request.setRequestType(RequestType.SEARCH_SELLERS);
                request.setClientData(SellerIdSalaryField.getText());
                Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                Response response = Client.getInstance().acceptingReplyEnteringFromServer();
                if (response.getResponse().equals(Responses.BAD_REQUEST)) {
                    LabelSalaryWarning.setText("Такого id нет!");
                } else {
                   Seller seller = (Seller)new Gson().fromJson(response.getServerData(),Seller.class);
                    SellerIdSalaryField.setText(String.valueOf(seller.getId()));
                    SellerNameSalaryField.setText(seller.getName());
                    SellerSurnameSalaryField.setText(seller.getSurname());
                    SellerHoursSalaryField.setText(String.valueOf(seller.getHours()));
                    SellerHoursRateSalaryField.setText(String.valueOf(seller.getRatePerHour()));
                    SellerAmountSellsSalaryField.setText(String.valueOf(seller.getAmountSells()));
                    SellerRatePerSellSalaryField.setText(String.valueOf(seller.getRatePerSell()));
                    request.setRequestType(RequestType.CALCULATE_SALARY_SELLER);
                    request.setClientData(new Gson().toJson(seller));
                    Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                    Response responseSalary = Client.getInstance().acceptingReplyEnteringFromServer();
                    SellerSalaryField.setText(responseSalary.getServerData());
                    LabelSalaryWarning.setText("");
                    SalaryLabel.setVisible(true);
                    SellerSalaryField.setVisible(true);
                    SellerSalaryField.setDisable(false);
                    CalculateButton.setVisible(false);
                }
            } else{
                LabelSalaryWarning.setText("ID должен содержать цифры!");
            }
        }
    }

    public void resetButtonSalary(){
        SalaryLabel.setVisible(false);
        SellerSalaryField.setVisible(false);
        CalculateButton.setVisible(true);
        CalculateButton.setDisable(false);
        SellerIdSalaryField.setText("");
        SellerNameSalaryField.setText("");
        SellerSurnameSalaryField.setText("");
        SellerHoursSalaryField.setText("");
        SellerHoursRateSalaryField.setText("");
        SellerAmountSellsSalaryField.setText("");
        SellerRatePerSellSalaryField.setText("");
        LabelSalaryWarning.setText("");
    }

    public void creatingReportSeller(){
        WarningLabelSellerReport.setText("");
        String regex = "\\d+";
        if(ReportSellerIdField.getText() == "") WarningLabelSellerReport.setText("Вы не ввели id!");
        else{
            if(ReportSellerIdField.getText().matches(regex)) {
                request.setRequestType(RequestType.SEARCH_SELLERS);
                request.setClientData(ReportSellerIdField.getText());
                Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                Response response = Client.getInstance().acceptingReplyEnteringFromServer();
                if (response.getResponse().equals(Responses.BAD_REQUEST)) {
                    WarningLabelSellerReport.setText("Такого id нет!");
                } else {
                    Seller seller = (Seller)new Gson().fromJson(response.getServerData(),Seller.class);
                    request.setRequestType(RequestType.CALCULATE_SALARY_SELLER);
                    request.setClientData(new Gson().toJson(seller));
                    Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
                    Response responseSalary = Client.getInstance().acceptingReplyEnteringFromServer();
                    seller.setSalary(Float.parseFloat(responseSalary.getServerData()));
                    ExcelReport.createReportForSeller(seller);
                }
            } else{
                WarningLabelSellerReport.setText("ID должен содержать цифры!");
            }
        }
    }

    public void editProfile(){
        if(clicked == 0 || clicked == 2) {
            clicked = 1;
            ProfileNameEdit.setVisible(true);
            ProfileSurnameEdit.setVisible(true);
            ProfileLoginEdit.setVisible(true);
            ProfilePasswordEdit.setVisible(true);
            ProfileEmailEdit.setVisible(true);
            ProfileNameEdit.setText(NowUser.getInstance().getName());
            ProfileSurnameEdit.setText(NowUser.getInstance().getSurname());
            ProfileLoginEdit.setText(NowUser.getInstance().getLogin());
            ProfilePasswordEdit.setText(NowUser.getInstance().getPassword());
            ProfileEmailEdit.setText(NowUser.getInstance().getEMail());
        } else{
            clicked = 2;
            request.setRequestType(RequestType.EDIT_USER);
            NowUser.getInstance().setName(ProfileNameEdit.getText());
            NowUser.getInstance().setSurname(ProfileSurnameEdit.getText());
            NowUser.getInstance().setEMail(ProfileEmailEdit.getText());
            NowUser.getInstance().setLogin(ProfileLoginEdit.getText());
            NowUser.getInstance().setPassword(ProfilePasswordEdit.getText());
            request.setClientData(new Gson().toJson(NowUser.getInstance()));
            Client.getInstance().sendingRequestToServer(new Gson().toJson(request));
            Response response = Client.getInstance().acceptingReplyEnteringFromServer();
            if (response.getResponse().equals(Responses.BAD_REQUEST)) {
                WarningLabelSellers.setText("Такого id нет!");
            } else {

                ProfileNameEdit.setVisible(false);
                ProfileSurnameEdit.setVisible(false);
                ProfileLoginEdit.setVisible(false);
                ProfilePasswordEdit.setVisible(false);
                ProfileEmailEdit.setVisible(false);
                ProfileName.setText(NowUser.getInstance().getName());
                ProfileSurname.setText(NowUser.getInstance().getSurname());
                ProfileEmail.setText(NowUser.getInstance().getEMail());
                ProfileLogin.setText(NowUser.getInstance().getLogin());
                ProfilePassword.setText(NowUser.getInstance().getPassword());
            }
        }
    }
}

class ExcelReport{
    public static void createReportForSeller(Seller seller){
        Workbook wb = new HSSFWorkbook();
        Font font = wb.createFont();
        CellStyle style = wb.createCellStyle();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        Sheet sheetSeller = wb.createSheet("sellerId" + seller.getId());
        sheetSeller.setColumnWidth(0, 6000);
        sheetSeller.setColumnWidth(1, 5000);
        sheetSeller.autoSizeColumn(1);
        sheetSeller.addMergedRegion(new CellRangeAddress(0,0,0,1));
        Row row0 = sheetSeller.createRow(0);
        Cell cell0 = row0.createCell(0);
        cell0.setCellValue("Отчет о струднике " + seller.getId());
        cell0.setCellStyle(style);
        ArrayList<Row> rows = new ArrayList<>();
        for (int i = 1; i <= 10; i++){
            Row row = sheetSeller.createRow(i);
            rows.add(row);
        }
        ArrayList<Cell> cells = new ArrayList<>();
        for (Row row: rows){
            Cell cell = row.createCell(0);
            cells.add(cell);
        }
        cells.get(0).setCellValue("Имя");
        cells.get(1).setCellValue("Фамилия");
        cells.get(2).setCellValue("Почта");
        cells.get(3).setCellValue("Логин");
        cells.get(4).setCellValue("Пароль");
        cells.get(5).setCellValue("Отработанные часы");
        cells.get(6).setCellValue("Почасовая ставка");
        cells.get(7).setCellValue("Кол-во продаж");
        cells.get(8).setCellValue("Ставка за ед. продукции");
        cells.get(9).setCellValue("Текущая зарплата");
        cells.get(9).setCellStyle(style);
        ArrayList<Cell> cellsSeller = new ArrayList<>();
        for (Row row: rows) {
            Cell cell = row.createCell(1);
            cellsSeller.add(cell);
        }
        cellsSeller.get(0).setCellValue(seller.getName());
        cellsSeller.get(1).setCellValue(seller.getSurname());
        cellsSeller.get(2).setCellValue(seller.getEMail());
        cellsSeller.get(3).setCellValue(seller.getLogin());
        cellsSeller.get(4).setCellValue(seller.getPassword());
        cellsSeller.get(5).setCellValue(seller.getHours());
        cellsSeller.get(6).setCellValue(seller.getRatePerHour());
        cellsSeller.get(7).setCellValue(seller.getAmountSells());
        cellsSeller.get(8).setCellValue(seller.getRatePerSell());
        cellsSeller.get(9).setCellValue(seller.getSalary());
        cellsSeller.get(9).setCellStyle(style);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream("C:\\Users\\Артемон\\Desktop\\ReportsSeller.xls");
            wb.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}