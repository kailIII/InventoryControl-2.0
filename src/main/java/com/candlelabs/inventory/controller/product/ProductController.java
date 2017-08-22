package com.candlelabs.inventory.controller.product;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import static com.candlelabs.inventory.RMIInventory.productService;
import com.candlelabs.inventory.model.Product;
import java.util.List;

/**
 *
 * @author Arturo Cordero
 */
public class ProductController extends ProductContainer implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            
            List<Product> listProducts = productService.listProducts();
            
            System.out.println(listProducts.size());
            
            super.initTV(listProducts);
            
            super.initValidators();
            
        } catch (RemoteException ex) {
            
            System.out.println("Exception: " + ex.toString());
            
        }
        
    }
    
}
