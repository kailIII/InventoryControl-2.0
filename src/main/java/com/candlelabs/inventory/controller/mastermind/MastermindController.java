package com.candlelabs.inventory.controller.mastermind;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import com.candlelabs.inventory.controller.interfaces.MastermindInitializer;
import com.candlelabs.inventory.controller.interfaces.StoreSelectionInitializer;

import com.candlelabs.inventory.controller.product.ProductController;
import com.candlelabs.inventory.controller.store.selection.StoreSelectionController;

import com.candlelabs.inventory.model.Store;
import com.candlelabs.inventory.rmi.implementations.service.CallbackClientImpl;

import com.candlelabs.inventory.util.FXUtil;
import java.rmi.RemoteException;

/**
 *
 * @author VakSF
 */
public class MastermindController extends MastermindContainer 
        implements Initializable, StoreSelectionInitializer {
    
    private ProductController productController;
    private StoreSelectionController storeSelectionController; 
    
    private CallbackClientImpl client;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initViews();
    }
    
    @Override
    public void init(StoreSelectionController controller, CallbackClientImpl client) {
        
        this.storeSelectionController = controller;
        
        this.client = client;
        
        this.client.initMastermind(this);
        
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
    
    private void initViews() {
        
        this.productController = this.initView(
                "/view/product/Product.fxml", 
                ProductController.class, this.getProductPane());
        
    }
    
    private <T extends MastermindInitializer> T initView(
            String fxml, Class<T> clazz, AnchorPane pane) {
        
        T controller = null;
        
        try {
            
            FXMLLoader loader = FXUtil.loader(fxml, getClass());
            
            AnchorPane anchorPane = loader.<AnchorPane>load();
            controller = loader.<T>getController();
            
            controller.init(this);
            
            FXUtil.setAnchor(anchorPane);
            
            pane.getChildren().setAll(anchorPane);
            
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        
        return controller;
        
    }

    public ProductController getProductController() {
        return productController;
    }

    public StoreSelectionController getStoreSelectionController() {
        return storeSelectionController;
    }

    public CallbackClientImpl getClient() {
        return client;
    }
    
}
