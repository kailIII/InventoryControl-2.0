package com.candlelabs.inventory.controller.supplier;

import com.candlelabs.inventory.controller.interfaces.ProductInitializer;
import com.candlelabs.inventory.controller.product.ProductController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 * @author VakSF
 */
public class SupplierController extends SupplierContainer 
        implements Initializable, ProductInitializer {
    
    private ProductController productController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @Override
    public void init(ProductController controller) {
        this.productController = controller;
    }
    
}
