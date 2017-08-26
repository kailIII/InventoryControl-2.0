package com.candlelabs.inventory.controller.store;

import com.candlelabs.inventory.controller.interfaces.StoreSelectionInitializer;
import com.candlelabs.inventory.controller.store.selection.StoreSelectionController;
import com.candlelabs.inventory.model.Store;
import com.candlelabs.inventory.rmi.implementations.service.CallbackClientImpl;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;

/**
 *
 * @author VakSF
 */
public class StoreController extends StoreContainer 
        implements Initializable, StoreSelectionInitializer {
    
    private StoreSelectionController storeSelectionController; 
    
    private CallbackClientImpl client;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @Override
    public void init(StoreSelectionController controller, CallbackClientImpl client) {
        
        this.storeSelectionController = controller;
        
        this.client = client;
        
        this.client.initStore(this);
        
        Store store = this.storeSelectionController.getStore();
    }

    @Override
    public boolean unregister() {
        
        try {
            
            return this.client.unregister();
            
        } catch (RemoteException ex) {
            
        }
        
        return false;
    }

    public StoreSelectionController getStoreSelectionController() {
        return storeSelectionController;
    }

    public CallbackClientImpl getClient() {
        return client;
    }
    
}
