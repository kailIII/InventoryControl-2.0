package com.candlelabs.inventory.controller.store.selection;

import com.candlelabs.inventory.RMIClient;
import com.candlelabs.inventory.controller.interfaces.LoginInitializer;
import com.candlelabs.inventory.controller.interfaces.StoreSelectionInitializer;
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
    private void next(ActionEvent event) throws RemoteException, NotBoundException {
        
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
                
                this.mastermindController = this.initView(
                        "/view/mastermind/Mastermind.fxml",
                        MastermindController.class,
                        "Cerebro - Control de inventario"
                );
                
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
    
    private <T extends StoreSelectionInitializer> T initView(
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
