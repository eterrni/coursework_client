package by.bsuir.kp.сontroller;

import by.bsuir.kp.bean.Producer;
import by.bsuir.kp.bean.Product;
import by.bsuir.kp.bean.Provider;
import by.bsuir.kp.view.ChangeWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class NewProductPageController implements ChangeWindow {

    private static final String ADMIN_HOME_PAGE = "../view/adminHome.fxml";

    private static final String INIT_CHOICE_PRODUCER = "init_choice_producer";
    private static final String INIT_CHOICE_PROVIDER = "init_choice_provider";
    private static final String ADD_NEW_PRODUCT = "add_new_product";

    private static final String SUCCESSFUL_ADD_PRODUCT_MESSAGE = "successful_add_product";
    private static final String EMPTY_FIELD_MESSAGE = "Заполните поля";
    private static final String SUCCESSFUL_ADD_PRODUCT = "Добавление товара прошло успешно";
    private static final String UNSUCCESSFUL_ADD_PRODUCT = "Добавление товара прошло неуспешно";
    private static final String ERROR_MESSAGE = "Ошибка!";

    private ObservableList<Producer> producers = FXCollections.observableArrayList();
    private ObservableList<Provider> providers = FXCollections.observableArrayList();

    @FXML
    public TextField modelField;
    @FXML
    public TextField typeField;
    @FXML
    public TextField yearField;
    @FXML
    public TextField countField;
    @FXML
    public TextField fleshField;
    @FXML
    public ChoiceBox choiceProducer;
    @FXML
    public ChoiceBox choiceProvider;
    @FXML
    public TextField statusField;
    @FXML
    public Button addProduct;
    @FXML
    public Button goToMenuPage;

    @FXML
    private void initialize() {
        initChoiceProducer();
        initChoiceProvider();
        addProduct.setOnAction(actionEvent -> {
            if (validationProductData((Producer) choiceProducer.getSelectionModel().getSelectedItem(),
                    (Provider) choiceProvider.getSelectionModel().getSelectedItem())) {

                Client.sendMessage(ADD_NEW_PRODUCT);
                Client.sendObject(new Product(
                        modelField.getText(),
                        typeField.getText(),
                        Integer.valueOf(countField.getText()),
                        Integer.valueOf(yearField.getText()),
                        Integer.valueOf(fleshField.getText()),
                        statusField.getText(),
                        ((Producer) choiceProducer.getSelectionModel().getSelectedItem()).getName(),
                        ((Provider) choiceProvider.getSelectionModel().getSelectedItem()).getName()
                ));

                String result = null;
                try {
                    result = Client.readMessage();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, ERROR_MESSAGE);
                }
                if (result.equals(SUCCESSFUL_ADD_PRODUCT_MESSAGE)) {
                    JOptionPane.showMessageDialog(null, SUCCESSFUL_ADD_PRODUCT);
                } else {
                    JOptionPane.showMessageDialog(null, UNSUCCESSFUL_ADD_PRODUCT);
                }
            }
        });
        goToMenuPage.setOnAction(actionEvent -> {
            changeWindow(ADMIN_HOME_PAGE, goToMenuPage);
        });
    }

    private boolean validationProductData(Producer choiceProducer, Provider choiceProvider) {
        if (modelField.getLength() == 0 || typeField.getLength() == 0 ||
                yearField.getLength() == 0 || countField.getLength() == 0 ||
                fleshField.getLength() == 0 || statusField.getLength() == 0 || choiceProducer == null || choiceProvider == null) {
            JOptionPane.showMessageDialog(null, EMPTY_FIELD_MESSAGE);
            return false;
        } else {
            try {
                Integer.valueOf(countField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Некорректный ввод количества");
                return false;
            }
            try {
                Integer.valueOf(yearField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Некорректный ввод года");
                return false;
            }
            try {
                Integer.valueOf(fleshField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Некорректный ввод флеш");
                return false;
            }

            return true;
        }
    }

    private void initChoiceProducer() {
        producers.clear();
        Client.sendMessage(INIT_CHOICE_PRODUCER);
        List<Producer> producerList = (List<Producer>) Client.readObject();
        producers.addAll(producerList);
        choiceProducer.setItems(producers);

    }

    private void initChoiceProvider() {
        providers.clear();
        Client.sendMessage(INIT_CHOICE_PROVIDER);
        List<Provider> providerList = (List<Provider>) Client.readObject();
        providers.addAll(providerList);
        choiceProvider.setItems(providers);
    }
}
