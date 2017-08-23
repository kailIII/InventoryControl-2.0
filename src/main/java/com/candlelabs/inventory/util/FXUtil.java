package com.candlelabs.inventory.util;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Arturh
 */
public class FXUtil {
    
    public static String nodeStringValue(Node node) {
        
        String className = node.getClass().getSimpleName();
        
        if (className.equals("TextField") || className.equals("JFXTextField")) {
            
            TextField textField = (TextField) node;
            
            return textField.getText();
            
        } else {
            
            if (className.equals("PasswordField")) {
                
                PasswordField passwordField = (PasswordField) node;
                
                return passwordField.getText();
                
            } else {
                
                if (className.equals("ComboBox") || className.equals("JFXComboBox")) {
                    
                    ComboBox comboBox = (ComboBox) node;
                    
                    return comboBox.getValue().toString();
                    
                }
                
            }
            
        }
        
        return null;
        
    }
    
    public static String objectStringValue(Object object) {
        
        try {
            
            String className = object.getClass().getSimpleName();
        
            if (className.equalsIgnoreCase("Integer")) {

                return ((Integer) object).toString();

            }
            
        } catch (NullPointerException ex) {
            
            return "Error: " + ex.toString();
            
        }
        
        return object.toString();
    }
    
    public static FXMLLoader loader(String fxml, Class clazz) throws IOException {
        return new FXMLLoader(clazz.getResource(fxml));
    }
    
    public static void setAnchor(AnchorPane anchorPane) {
        AnchorPane.setTopAnchor(anchorPane, 0.0);
        AnchorPane.setBottomAnchor(anchorPane, 0.0);
        AnchorPane.setLeftAnchor(anchorPane, 0.0);
        AnchorPane.setRightAnchor(anchorPane, 0.0);
    }
    
    public static <T> T selectedTableItem(TableView<T> tableView) {
        return tableView.getSelectionModel().getSelectedItem();
    }
    
}
