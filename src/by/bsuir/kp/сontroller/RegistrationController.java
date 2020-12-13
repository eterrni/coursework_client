package by.bsuir.kp.сontroller;

import by.bsuir.kp.bean.User;
import by.bsuir.kp.view.ChangeWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;

public class RegistrationController implements ChangeWindow {

    private static final String WELCOME_PAGE = "../view/sample.fxml";

    private static final String REGISTRATION = "registration";
    private static final String USER_EXISTS = "user_exists";
    private static final String INVALID_LOGIN = "invalid_login";
    private static final String INVALID_PASSWORD = "invalid_password";
    private static final String SUCCESSFUL_REGISTRATION = "successful_registration";

    private static final String EMPTY_FIELD_MESSAGE = "Заполните поля";
    private static final String USER_EXISTS_MESSAGE = "Такой пользователь уже существует";
    private static final String INVALID_LOGIN_MESSAGE = "Логин должен начинаться с буквы и состоять из 2-ух до 14-ти символов";
    private static final String INVALID_PASSWORD_MESSAGE = "Пароль должен содержать буквы и цифры, и состоять из 5-ти до 15-ти символов";
    private static final String SUCCESSFUL_REGISTRATION_MESSAGE = "Регистрация прошла успешно, ";


    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField loginField;
    @FXML
    private Button signUpButton;
    @FXML
    private Button goToWelcomePageButton;

    @FXML
    void initialize() {
        signUpButton.setOnAction(actionEvent -> {
            try {
                if (loginField.getLength() == 0 || passwordField.getLength() == 0) {
                    JOptionPane.showMessageDialog(null, EMPTY_FIELD_MESSAGE);
                } else {
                    Client.sendMessage(REGISTRATION);
                    Client.sendUser(new User(loginField.getText(), passwordField.getText()));
                    String message = Client.readMessage();
                    if (message.equals(USER_EXISTS)) {
                        JOptionPane.showMessageDialog(null, USER_EXISTS_MESSAGE);
                    }
                    if (message.equals(INVALID_LOGIN)) {
                        JOptionPane.showMessageDialog(null, INVALID_LOGIN_MESSAGE);
                    }
                    if (message.equals(INVALID_PASSWORD)) {
                        JOptionPane.showMessageDialog(null, INVALID_PASSWORD_MESSAGE);
                    }
                    if (message.equals(SUCCESSFUL_REGISTRATION)) {
                        JOptionPane.showMessageDialog(null, SUCCESSFUL_REGISTRATION_MESSAGE + loginField.getText());
                        changeWindow(WELCOME_PAGE, signUpButton);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        goToWelcomePageButton.setOnAction(actionEvent -> {
            changeWindow(WELCOME_PAGE, goToWelcomePageButton);
        });
    }
}
