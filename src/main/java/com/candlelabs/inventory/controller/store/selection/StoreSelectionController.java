package com.candlelabs.inventory.controller.store.selection;

import com.candlelabs.inventory.RMIClient;
import com.candlelabs.inventory.controller.interfaces.LoginInitializer;
import com.candlelabs.inventory.controller.login.LoginController;
import com.candlelabs.inventory.controller.mastermind.MastermindController;
import com.candlelabs.inventory.model.Store;
import com.candlelabs.inventory.rmi.implementations.service.CallbackClientImpl;
import com.candlelabs.inventory.rmi.interfaces.service.ServerResponder;
import com.candlelabs.inventory.util.FXUtil;
import java.io.IOException;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author VakSF
 */
public class StoreSelectionController extends StoreSelectionContainer 
        implements Initializable, LoginInitializer {
    
    private LoginController loginController;
    
    private MastermindController mastermindController;
    
    private Store store;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @Override
    public void init(LoginController controller) {
        
        this.loginController = controller;
        
        List<Store> stores = this.loginController.getLoggedUser().getStores();
        
        this.initTV(stores);
    }
    
    @FXML
    private void next(ActionEvent event) 
            throws RemoteException, NotBoundException, IOException {
        
        Store selectedStore = FXUtil.selectedTableItem(getStoresTV());
        
        ServerResponder server = (ServerResponder) 
                RMIClient.getRegistry().lookup("serverResponder");
        
        CallbackClientImpl client = new CallbackClientImpl(
                server, selectedStore.getName()
        );
        
        if (client.register()) {
            
            this.store = selectedStore;
            
            System.out.println("La ubicación solicitada está disponible");
            
            String type = selectedStore.getTypeStore().getDescription().toLowerCase();
            
            if (type.equals("cerebro")) {
                
                String fxml = "/view/mastermind/Mastermind.fxml";
                String title = "Cerebro - Control de inventario";
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(new Scene((Pane) loader.load()));
                
                this.mastermindController = loader.<MastermindController>getController();
                this.mastermindController.init(this, client);
                
                stage.setOnCloseRequest((windowEvent) -> {
                    
                    this.mastermindController.unregister();
                    
                    System.exit(0);
                    
                });
                
                stage.setTitle(title);
                stage.show();
                
                this.close(event);
                
            } else if (type.equals("tienda")) {
                
                System.out.println("Vista pendiente");
                
            } else if (type.equals("bodega")) {
                
                System.out.println("Vista pendiente");
                
            }
            
        } else {
            
            new Alert(
                    AlertType.ERROR,
                    "La ubicación solicitada se encuentra ocupada"
            ).show();
            
        }
        
    }
    
    public Store getStore() {
        return store;
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public MastermindController getMastermindController() {
        return mastermindController;
    }
    
}
