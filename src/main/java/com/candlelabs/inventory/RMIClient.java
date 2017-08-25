package com.candlelabs.inventory;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class RMIClient extends Application {
    
    public static String SERVER_ADDRESS = "127.0.0.1";
    
    @Override
    public void start(Stage stage) throws Exception {
        
        String url = "/view/login/Login.fxml";
//        String url = "/view/mastermind/Mastermind.fxml";
//        String url = "/view/category/Category.fxml";
//        String url = "/view/measurement/Measurement.fxml";
        
        Parent root = FXMLLoader.load(getClass().getResource(url));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("Inventory Control 2.0");
        stage.setScene(scene);
        stage.show();
    }
    
    public static Registry getRegistry() {
        
        Registry registry = null;
        
        try {
            
            registry = LocateRegistry.getRegistry(
                    RMIClient.SERVER_ADDRESS, Registry.REGISTRY_PORT
            );
            
            System.out.println("Registry Ready");
            
        } catch (RemoteException ex) {
            
            System.out.println("Exception: " + ex.toString());
            
            System.out.println("Unable to connect to server");
            
        }
        
        return registry;
        
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
