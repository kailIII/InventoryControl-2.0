package com.candlelabs.inventory.controller.mastermind;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import com.candlelabs.inventory.controller.interfaces.MastermindInitializer;
import com.candlelabs.inventory.controller.interfaces.StoreSelectionInitializer;
import com.candlelabs.inventory.controller.interfaces.ToServer;

import com.candlelabs.inventory.controller.product.ProductController;
import com.candlelabs.inventory.controller.store.selection.StoreSelectionController;

import com.candlelabs.inventory.model.Category;
import com.candlelabs.inventory.model.Store;

import com.candlelabs.inventory.util.FXUtil;

/**
 *
 * @author VakSF
 */
public class MastermindController extends MastermindContainer 
        implements Initializable, StoreSelectionInitializer, ToServer {
    
    private ProductController productController;
    
    private StoreSelectionController storeSelectionController; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.initViews();
    }
    
    @Override
    public void init(StoreSelectionController controller) {
        this.storeSelectionController = controller;
        
        Store store = this.storeSelectionController.getStore();
        
        System.out.println(
                "Store: " + store.getName() + " - " + store.getTypeStore().getDescription()
        );
        
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

    @Override
    public void newCategory(Category category) {
        System.out.println("Mastermind");
        System.out.println("New Category");
    }
    
}
