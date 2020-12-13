package by.bsuir.kp.сontroller;

import by.bsuir.kp.bean.Product;
import by.bsuir.kp.view.ChangeWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.PromptData;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class UserHomeController implements ChangeWindow {
    public Button find_product;
    public Button exit;
    public Button show_all_products;

    private static final String FIND_PRODUCT = "find_product";
    private static final String INIT_DATA = "init_data";
    private static final String WELCOME_PAGE = "../view/sample.fxml";

    private ObservableList<Product> productsData = FXCollections.observableArrayList();


    @FXML
    private TableView<Product> tableProducts;

    @FXML
    private TableColumn<Product, String> modelColumn;

    @FXML
    private TableColumn<Product, String> typeColumn;

    @FXML
    private TableColumn<Product, String> producerColumn;

    @FXML
    private TableColumn<Product, Integer> countColumn;

    @FXML
    private TableColumn<Product, Integer> yearColumn;

    @FXML
    private TableColumn<Product, String> providerColumn;

    @FXML
    private TableColumn<Product, Integer> fleshColumn;

    @FXML
    private TableColumn<Product, String> statusColumn;


    // инициализируем форму данными
    @FXML
    private void initialize() {
        initData();

        find_product.setOnAction(actionEvent -> {
            String findProductName = JOptionPane.showInputDialog("Введите название модели товара");
            findProduct(findProductName);
        });

        show_all_products.setOnAction(actionEvent -> {
            initData();
        });

        exit.setOnAction(actionEvent -> {
            changeWindow(WELCOME_PAGE, exit);
        });

    }

    private void initData() {
        productsData.clear();
        Client.sendMessage(INIT_DATA);
        List<Product> productList = (List<Product>) Client.readObject();
        productsData.addAll(productList);
        // устанавливаем тип и значение которое должно хранится в колонке
        modelColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("model"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("type"));
        producerColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("producerName"));
        countColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("count"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("year"));
        providerColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("providerName"));
        fleshColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("flesh"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("status"));
        // заполняем таблицу данными
        tableProducts.setItems(productsData);
    }

    private void findProduct(String findProductName) {
        if (findProductName != null) {
            productsData.clear();
            Client.sendMessage(FIND_PRODUCT);
            Client.sendMessage(findProductName);
            List<Product> findProductList = (List<Product>) Client.readObject();
            if (findProductList != null) {
                productsData.addAll(findProductList);
                // устанавливаем тип и значение которое должно хранится в колонке
                modelColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("model"));
                typeColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("type"));
                producerColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("producerName"));
                countColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("count"));
                yearColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("year"));
                providerColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("providerName"));
                fleshColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("flesh"));
                statusColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("status"));
                // заполняем таблицу данными
                tableProducts.setItems(productsData);
            } else {
                JOptionPane.showMessageDialog(null, "Продукт не найден");
                initData();
            }
        }
    }
}
