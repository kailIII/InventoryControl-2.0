package com.candlelabs.inventory.controller.mastermind;

import com.candlelabs.inventory.controller.interfaces.MastermindInitializer;
import com.candlelabs.inventory.controller.interfaces.ProductInitializer;
import com.candlelabs.inventory.controller.product.ProductController;
import com.candlelabs.inventory.model.Product;
import com.candlelabs.inventory.util.FXUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author VakSF
 */
public class MastermindController extends MastermindContainer implements Initializable {
    
    private ProductController productController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.initViews();
        
    }
    
    private void initViews() {
        
        this.productController = this.initView(
                "/view/product/Product.fxml", 
                ProductController.class, this.getProductPane());
        
    }
    
    public void newProduct(Product product) {
        
        System.out.println("New product: " + product.getName());
        
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
    
    
    
}
