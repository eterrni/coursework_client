package by.bsuir.kp.сontroller;

import by.bsuir.kp.bean.User;
import by.bsuir.kp.view.ChangeWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;

public class WelcomePageController implements ChangeWindow {
    private Client client;
    private static final String LOGIN = "login";
    private static final String LOCALHOST = "localhost";

    private static final String USER_NOT_EXIST = "user_not_exist";
    private static final String SUCCESSFUL_AUTHORIZATION_MESSAGE = "successful_authorization";

    private static final String EMPTY_FIELD_MESSAGE = "Заполните поля";
    private static final String SUCCESSFUL_AUTHORIZATION = "Успешная авторизация, ";
    private static final String INCORRECT_DATA_ENTRY = "Некорректный ввод данных";

    private static final String ADMIN_ROLE = "admin";

    private static final String ADMIN_PAGE = "../view/adminHome.fxml";
    private static final String USER_PAGE = "../view/userHome.fxml";
    private static final String REGISTRATION_PAGE = "../view/registration.fxml";

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField loginField;
    @FXML
    private Button signInButton;
    @FXML
    private Button signUpButton;

    @FXML
    void initialize() {
        start();
        signInButton.setOnAction(actionEvent -> {
            try {
                if (loginField.getLength() == 0 || passwordField.getLength() == 0) {
                    JOptionPane.showMessageDialog(null, EMPTY_FIELD_MESSAGE);
                } else {
                    Client.sendMessage(LOGIN);
                    Client.sendUser(new User(loginField.getText(), passwordField.getText()));
                    String message = Client.readMessage();
                    if (message.equals(USER_NOT_EXIST)) {
                        JOptionPane.showMessageDialog(null, INCORRECT_DATA_ENTRY);
                    }
                    if (message.equals(SUCCESSFUL_AUTHORIZATION_MESSAGE)) {
                        JOptionPane.showMessageDialog(null, SUCCESSFUL_AUTHORIZATION + loginField.getText());
                        if (Client.readMessage().equals(ADMIN_ROLE)) {
                            changeWindow(ADMIN_PAGE, signInButton);
                        } else {
                            changeWindow(USER_PAGE, signInButton);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        signUpButton.setOnAction(actionEvent -> {
            changeWindow(REGISTRATION_PAGE, signUpButton);
        });
    }

    private void start() {
        if (Client.getClientSocket() == null) {
            client = new Client(LOCALHOST, 8080);
        }
    }

}


