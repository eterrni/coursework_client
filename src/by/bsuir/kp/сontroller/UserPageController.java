package by.bsuir.kp.сontroller;

import by.bsuir.kp.bean.Product;
import by.bsuir.kp.bean.User;
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

public class UserPageController implements ChangeWindow {

    private static final String USER_NOT_SELECTED_MESSAGE = "Выберите пользователя";
    private static final String SUCCESSFUL_DELETE = "Удаление прошло успешно";
    private static final String UNSUCCESSFUL_DELETE = "Удаление прошло неуспешно";
    private static final String SUCCESSFUL_APPOINT = "Изменение статуса пользователя прошло успешно";
    private static final String UNSUCCESSFUL_APPOINT = "Изменение статуса пользователя прошло неуспешно";


    private static final String SUCCESSFUL_DELETE_MESSAGE = "successful_delete";
    private static final String SUCCESSFUL_APPOINT_MESSAGE = "successful_appoint";


    private static final String INIT_USERS = "init_users";
    private static final String DELETE_USER = "delete_user";
    private static final String APPOINT_AN_USER = "appoint_an_user";
    private static final String APPOINT_AN_ADMINISTRATOR = "appoint_an_administrator";

    private static final String USER_PAGE = "../view/user_page.fxml";
    private static final String ADMIN_HOME_PAGE = "../view/adminHome.fxml";
    private static final String WELCOME_PAGE = "../view/sample.fxml";
    private ObservableList<User> usersData = FXCollections.observableArrayList();

    @FXML
    public TableView tableUser;
    @FXML
    public TableColumn idUser;
    @FXML
    public TableColumn loginUser;
    @FXML
    public TableColumn roleUser;
    @FXML
    public Button exit;
    @FXML
    public Button delete_user;
    @FXML
    public Button appoint_an_administrator;
    @FXML
    public Button appoint_an_user;
    @FXML
    public Button go_to_menu;

    @FXML
    private void initialize() {
        initUsers();

        appoint_an_user.setOnAction(actionEvent -> {
            try {
                User selectedUser = (User) tableUser.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    Client.sendMessage(APPOINT_AN_USER);
                    String userId = selectedUser.getId().toString();
                    Client.sendMessage(userId);
                    String result = Client.readMessage();
                    if (result.equals(SUCCESSFUL_APPOINT_MESSAGE)) {
                        JOptionPane.showMessageDialog(null, SUCCESSFUL_APPOINT);
                    } else {
                        JOptionPane.showMessageDialog(null, UNSUCCESSFUL_APPOINT);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, USER_NOT_SELECTED_MESSAGE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                changeWindow(USER_PAGE, appoint_an_user);
            }
        });

        appoint_an_administrator.setOnAction(actionEvent -> {
            try {
                User selectedUser = (User) tableUser.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    Client.sendMessage(APPOINT_AN_ADMINISTRATOR);
                    String userId = selectedUser.getId().toString();
                    Client.sendMessage(userId);
                    String result = Client.readMessage();
                    if (result.equals(SUCCESSFUL_APPOINT_MESSAGE)) {
                        JOptionPane.showMessageDialog(null, SUCCESSFUL_APPOINT);
                    } else {
                        JOptionPane.showMessageDialog(null, UNSUCCESSFUL_APPOINT);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, USER_NOT_SELECTED_MESSAGE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                changeWindow(USER_PAGE, appoint_an_administrator);
            }
        });

        delete_user.setOnAction(actionEvent -> {
            try {
                User selectedUser = (User) tableUser.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    Client.sendMessage(DELETE_USER);
                    String userId = selectedUser.getId().toString();
                    Client.sendMessage(userId);
                    String result = Client.readMessage();
                    if (result.equals(SUCCESSFUL_DELETE_MESSAGE)) {
                        JOptionPane.showMessageDialog(null, SUCCESSFUL_DELETE);
                    } else {
                        JOptionPane.showMessageDialog(null, UNSUCCESSFUL_DELETE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, USER_NOT_SELECTED_MESSAGE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        go_to_menu.setOnAction(actionEvent -> {
            changeWindow(ADMIN_HOME_PAGE, go_to_menu);
        });

        exit.setOnAction(actionEvent -> {
            changeWindow(WELCOME_PAGE, exit);
        });
    }

    private void initUsers() {
        usersData.clear();
        Client.sendMessage(INIT_USERS);
        List<User> usersList = (List<User>) Client.readObject();
        usersData.addAll(usersList);
        // устанавливаем тип и значение которое должно хранится в колонке
        idUser.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        loginUser.setCellValueFactory(new PropertyValueFactory<Product, String>("login"));
        roleUser.setCellValueFactory(new PropertyValueFactory<Product, String>("role"));
        // заполняем таблицу данными
        tableUser.setItems(usersData);
    }
}
