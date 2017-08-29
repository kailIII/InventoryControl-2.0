package com.candlelabs.inventory.controller.login;

import com.candlelabs.inventory.util.ValidatorUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author VakSF
 */
public class LoginContainer {
    
    @FXML
    private TextField userTF;
    
    @FXML
    private Button submitB;
    
    @FXML
    private PasswordField passwordPF;
    
    private ValidatorUtil validator;
    
    public LoginContainer() {
        
    }
    
    protected void initValidators() {
        
        this.validator = new ValidatorUtil(
                userTF, passwordPF
        );
        
    }
    
    protected void notFound() {
        
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error!");
        alert.setHeaderText("No se ha podido iniciar sesión");
        alert.setContentText("Usuario y/o contraseña incorrectos...");
        alert.showAndWait();
        
    }
    
    protected void close(ActionEvent event) {
        ((Node) this.getUserTF()).getScene().getWindow().hide();
    }

    public TextField getUserTF() {
        return userTF;
    }

    public PasswordField getPasswordPF() {
        return passwordPF;
    }

    public ValidatorUtil getValidator() {
        return validator;
    }

    public Button getSubmitB() {
        return submitB;
    }
    
}
