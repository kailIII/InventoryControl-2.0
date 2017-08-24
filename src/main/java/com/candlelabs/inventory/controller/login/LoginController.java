package com.candlelabs.inventory.controller.login;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.candlelabs.inventory.RMIClient;
import com.candlelabs.inventory.rmi.interfaces.service.LoginService;

import com.candlelabs.inventory.util.FXUtil;

import java.io.IOException;
import javafx.stage.Stage;

/**
 *
 * @author VakSF
 */
public class LoginController extends LoginContainer implements Initializable {
    
    private LoginService loginService;
    
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
            
            String user = getUserTF().getText();
            String password = getPasswordPF().getText();
            
            boolean login = this.loginService.login(user, password);
            
            if (login) {
                
                Stage stage = FXUtil.stageFXML(
                        "/view/product/Product.fxml",
                        "Productos - Control de inventario",
                        this.getClass()
                );
                
                stage.show();
                    
                this.close(event);
                
            } else {
                
                this.notFound();
                
            }
            
        } else {
            
            getValidator().emptyFields().show();
            
        }
        
    }
    
}
