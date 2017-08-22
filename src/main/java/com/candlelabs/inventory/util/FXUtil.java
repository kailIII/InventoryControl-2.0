package com.candlelabs.inventory.util;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    
}
