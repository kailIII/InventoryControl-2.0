package com.candlelabs.inventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.candlelabs.inventory.rmi.interfaces.service.ProductService;
import com.candlelabs.inventory.rmi.interfaces.service.SupplierService;
import com.candlelabs.inventory.rmi.interfaces.service.CategoryService;
import com.candlelabs.inventory.rmi.interfaces.service.MeasurementService;

import static javafx.application.Application.launch;

public class RMIClient extends Application {
    
    public static ProductService productService;
    public static SupplierService supplierService;
    public static CategoryService categoryService;
    public static MeasurementService measurementService;

    static { 
        connectServer(); 
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/view/product/Product.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("Inventory Control 2.0");
        stage.setScene(scene);
        stage.show();
    }
    
    private static void connectServer() {
        
        try {
            
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            RMIClient.productService = (ProductService) registry.lookup("productService");
            RMIClient.supplierService = (SupplierService) registry.lookup("supplierService");
            RMIClient.categoryService = (CategoryService) registry.lookup("categoryService");
            RMIClient.measurementService = (MeasurementService) registry.lookup("measurementService");
            
            System.out.println("Connected to server");
            
        } catch (RemoteException | NotBoundException ex) {
            
            System.out.println("Exception: " + ex.toString());
            
            System.out.println("No server running!");
            
            System.exit(0);
            
        }
        
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
