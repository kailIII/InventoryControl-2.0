package com.candlelabs.inventory.controller.login;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.candlelabs.inventory.RMIClient;
import com.candlelabs.inventory.controller.interfaces.LoginInitializer;
import com.candlelabs.inventory.controller.store.selection.StoreSelectionController;
import com.candlelabs.inventory.model.User;
import com.candlelabs.inventory.rmi.interfaces.service.LoginService;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author VakSF
 */
public class LoginController extends LoginContainer implements Initializable {
    
    private StoreSelectionController storeSelectionController;
    
    private LoginService loginService;
    
    private User loggedUser;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            
            this.loginService = (LoginService) RMIClient.getRegistry().lookup("loginService");
            
        } catch (RemoteException | NotBoundException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        
        this.initValidators();
        
    }
    
    @FXML
    private void login(ActionEvent event) throws RemoteException, IOException {
        
        if (getValidator().validateFields()) {
            
            String username = getUserTF().getText();
            String password = getPasswordPF().getText();
            
            User user = this.loginService.login(username, password);
            
            if (user != null) {
                
                this.loggedUser = user;
                
                this.storeSelectionController = this.initView(
                        "/view/store/selection/StoreSelection.fxml",
                        StoreSelectionController.class,
                        "Seleccionar ubicación - Control de inventario"
                );
                
                this.close(event);
                
            } else {
                
                this.notFound();
                
            }
            
        } else {
            
            getValidator().emptyFields().show();
            
        }
        
    }

    public User getLoggedUser() {
        return loggedUser;
    }
    
    private <T extends LoginInitializer> T initView(
            String fxml, Class<T> clazz, String title) {
        
        T controller = null;
        
        try {
            
            FXMLLoader loader = new FXMLLoader(clazz.getResource(fxml));
        
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene((Pane) loader.load()));
            
            controller = loader.<T>getController();
            
            controller.init(this);
            
            stage.setTitle(title);
            stage.show();
            
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        
        return controller;
        
    }
    
}
