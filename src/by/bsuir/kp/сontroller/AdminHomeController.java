package by.bsuir.kp.сontroller;

import by.bsuir.kp.bean.Product;
import by.bsuir.kp.view.ChangeWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class AdminHomeController implements ChangeWindow {
    public Button add_new_product;
    public Button add_new_producer;
    public Button add_new_provider;
    public Button delete_product;
    public Button find_product;
    public Button exit;
    public Button user_management;
    public Button show_all_products;

    private static final String WELCOME_PAGE = "../view/sample.fxml";
    private static final String USER_PAGE = "../view/user_page.fxml";
    private static final String ADD_NEW_PRODUCT_PAGE = "../view/newProductPage.fxml";

    private static final String ADD_NEW_PRODUCER = "add_new_producer";
    private static final String ADD_NEW_PROVIDER = "add_new_provider";
    private static final String FIND_PRODUCT = "find_product";
    private static final String INIT_DATA = "init_data";
    private static final String DELETE_PRODUCT = "delete_product";

    private static final String SUCCESSFUL_DELETE_MESSAGE = "successful_delete_message";
    private static final String SUCCESSFUL_ADD_PRODUCER_MESSAGE = "successful_add_producer_message";
    private static final String SUCCESSFUL_ADD_PROVIDER_MESSAGE = "successful_add_provider_message";
    private static final String PRODUCER_EXISTS_MESSAGE = "producer_exists_message";
    private static final String PROVIDER_EXISTS_MESSAGE = "provider_exists_message";

    private static final String PRODUCER_EXISTS = "Данный производитель уже добавлен в систему.";
    private static final String PROVIDER_EXISTS = "Данный поставщик уже добавлен в систему.";
    private static final String SUCCESSFUL_ADD_PRODUCER = "Производитель добавлен";
    private static final String SUCCESSFUL_ADD_PROVIDER = "Поставщик добавлен";
    private static final String UNSUCCESSFUL_ADD_PRODUCER = "Производитель не добавлен";
    private static final String UNSUCCESSFUL_ADD_PROVIDER = "Поставщик не добавлен";
    private static final String PRODUCT_NOT_FOUND = "Продукт не найден";
    private static final String SUCCESSFUL_DELETE = "Удаление товара прошло успешно";
    private static final String UNSUCCESSFUL_DELETE = "Удаление товара не прошло, ошибка.";
    private static final String PRODUCT_NOT_SELECTED_MESSAGE = "Выберите товар, который хотите удалить";

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
        add_new_product.setOnAction(actionEvent -> {
            changeWindow(ADD_NEW_PRODUCT_PAGE, add_new_product);
        });
        add_new_producer.setOnAction(actionEvent -> {
            try {
                String newProducerName = JOptionPane.showInputDialog("Введите название компании производителя");
                if (newProducerName != null && newProducerName.length() != 0) {
                    Client.sendMessage(ADD_NEW_PRODUCER);
                    Client.sendMessage(newProducerName);
                    String result = Client.readMessage();
                    if (result.equals(SUCCESSFUL_ADD_PRODUCER_MESSAGE)) {
                        JOptionPane.showMessageDialog(null, SUCCESSFUL_ADD_PRODUCER);
                    } else if (result.equals(PRODUCER_EXISTS_MESSAGE)) {
                        JOptionPane.showMessageDialog(null, PRODUCER_EXISTS);
                    } else {
                        JOptionPane.showMessageDialog(null, UNSUCCESSFUL_ADD_PRODUCER);
                    }
                }
            } catch (IOException e) {
                // TO DO log
                e.printStackTrace();
            }
        });
        add_new_provider.setOnAction(actionEvent -> {
            try {
                String newProviderName = JOptionPane.showInputDialog("Введите название компании поставщика");
                if (newProviderName != null && newProviderName.length() != 0) {
                    Client.sendMessage(ADD_NEW_PROVIDER);
                    Client.sendMessage(newProviderName);
                    String result = Client.readMessage();
                    if (result.equals(SUCCESSFUL_ADD_PROVIDER_MESSAGE)) {
                        JOptionPane.showMessageDialog(null, SUCCESSFUL_ADD_PROVIDER);
                    } else if (result.equals(PROVIDER_EXISTS_MESSAGE)) {
                        JOptionPane.showMessageDialog(null, PROVIDER_EXISTS);
                    } else {
                        JOptionPane.showMessageDialog(null, UNSUCCESSFUL_ADD_PROVIDER);
                    }
                }
            } catch (IOException e) {
                // TO DO log
                e.printStackTrace();
            }
        });

        user_management.setOnAction(actionEvent -> {
            changeWindow(USER_PAGE, user_management);
        });
        delete_product.setOnAction(actionEvent -> {
            try {
                Product selectedProduct = tableProducts.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    Client.sendMessage(DELETE_PRODUCT);
                    String productId = selectedProduct.getId().toString();
                    Client.sendMessage(productId);
                    String result = Client.readMessage();
                    if (result.equals(SUCCESSFUL_DELETE_MESSAGE)) {
                        JOptionPane.showMessageDialog(null, SUCCESSFUL_DELETE);
                    } else {
                        JOptionPane.showMessageDialog(null, UNSUCCESSFUL_DELETE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, PRODUCT_NOT_SELECTED_MESSAGE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        find_product.setOnAction(actionEvent -> {
            String findProductName = JOptionPane.showInputDialog("Введите название модели товара");
            findProduct(findProductName);
        });
        exit.setOnAction(actionEvent -> {
            changeWindow(WELCOME_PAGE, exit);
        });
        show_all_products.setOnAction(actionEvent -> {
            initData();
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
                JOptionPane.showMessageDialog(null, PRODUCT_NOT_FOUND);
                initData();
            }
        }
    }
}
