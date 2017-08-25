package com.candlelabs.inventory.controller.category;

import com.candlelabs.inventory.RMIClient;

import com.candlelabs.inventory.controller.interfaces.ProductInitializer;
import com.candlelabs.inventory.controller.product.ProductController;

import com.candlelabs.inventory.rmi.interfaces.service.CategoryService;

import com.candlelabs.inventory.model.Category;
import com.candlelabs.inventory.util.FXUtil;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.paint.Color;

/**
 *
 * @author VakSF
 */
public class CategoryController extends CategoryContainer 
        implements Initializable, ProductInitializer {
    
    private ProductController productController;
    
    public CategoryService categoryService;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.initServices();
        
        try {
            
            this.initValidators();
            
            this.initProducts();
            
            this.initCategories(categoryService.listCategories());
            
        } catch (RemoteException ex) {
            
        }
        
    }
    
    @Override
    public void init(ProductController controller) {
        this.productController = controller;
    }
    
    @FXML
    private void createEdit() {
        
        if (getValidator().validateFields()) {
            
            String action = getSubmitB().getText().toLowerCase();
            
            if (action.equals("crear")) {
                
                this.createCategory();
                
            } else {
                
                if (action.equals("editar")) {
                    
                    this.editCategory();
                    
                }
                
            }
            
        } else {
            
            getValidator().emptyFields().show();
            
        }
        
    }
    
    @FXML
    private void newCategory() {
        
        getNameTF().setEditable(true);
        
        getInfoL().setText("Creando categoría");
        getInfoL().setTextFill(Color.web("#d3cf43"));
        
        setEditing(true);
        
        getValidator().clearFields();
        
        getProducts().clear();
        
        getSubmitB().setDisable(false);
        getSubmitB().setText("Crear");
    }
    
    @FXML
    private void edit() {
        
        getInfoL().setText("Editando categoría");
        getInfoL().setTextFill(Color.web("#45d852"));
        
        getNameTF().setEditable(true);
        getNameTF().requestFocus();
        
        setEditing(true);
        
        getSubmitB().setDisable(false);
        getSubmitB().setText("Editar");
    }
    
    private void createCategory() {
        
        String name = getNameTF().getText();
            
        Category category = new Category(name);
        
        try {
            
            Integer categoryId = this.categoryService.createCategory(category);
            
            if (categoryId != null) {
                
                category.setId(categoryId);
                
                setEditing(false);
                
                getCategories().add(category);
                
                getCategoriesTV().getSelectionModel().select(category);
                
                new Alert(
                        AlertType.INFORMATION,
                        "Categoría creada correctamente"
                ).show();
                
                getNameTF().setEditable(false);
                getSubmitB().setDisable(true);
                
            } else {
                
                new Alert(
                        AlertType.ERROR,
                        "No se ha podido crear la categoría. El nombre ya existe"
                ).show();
                
            }
            
        } catch (RemoteException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        
    }
    
    private void editCategory() {
        
        Category category = FXUtil.selectedTableItem(getCategoriesTV());
        int index = FXUtil.selectedTableIndex(getCategoriesTV());
        
        if (category != null) {
            
            category.setName(getNameTF().getText());
            
            try {
                
                boolean updated = this.categoryService.updateCategory(category);
                
                if (updated) {
                    
                    setEditing(false);
                    
                    getCategoriesTV().refresh();
                    
                    getCategoriesTV().getSelectionModel().selectFirst();
                    
                    new Alert(
                            AlertType.INFORMATION,
                            "Categoría editada correctamente"
                    ).show();
                    
                    getSubmitB().setText("Crear");
                    getNameTF().setEditable(false);
                    getSubmitB().setDisable(true);
                    
                } else {
                    
                    new Alert(
                            AlertType.ERROR,
                            "La categoría no pudo ser editada. El nombre ya existe"
                    ).show();
                    
                }
                
            } catch (RemoteException ex) {
                System.out.println("Exception: " + ex.toString());
            }
            
        }
        
    }
    
    @FXML
    private void deleteCategory() {
        
        Category category = FXUtil.selectedTableItem(getCategoriesTV());
        
        Optional<ButtonType> result = new Alert(
                AlertType.CONFIRMATION, 
                "Está seguro de eliminar la categoría '" + category.getName() + "'?"
        ).showAndWait();
        
        if (result.get() == ButtonType.OK) {
            
            try {
                
                boolean deleted = this.categoryService.deleteCategory(category);
                
                if (deleted) {
                    
                    getCategories().remove(category);
                    
                    new Alert(
                            AlertType.INFORMATION,
                            "Categoría eliminada correctamente"
                    ).show();
                    
                } else {
                    
                    new Alert(
                            AlertType.ERROR,
                            "No se ha podido eliminar la categoría"
                    ).show();
                    
                }
                
            } catch (RemoteException ex) {
                System.out.println("Exception: " + ex.toString());
            }
            
        }
        
    }
    
    @FXML
    private void submit(KeyEvent event) throws IOException{
        
        if (event.getCode().equals(KeyCode.ENTER)) 
            getSubmitB().fire();
        
    }
    
    private void initServices() {
        
        try {
            
            this.categoryService = (CategoryService) RMIClient.getRegistry().lookup("categoryService");
            
        } catch (RemoteException | NotBoundException ex) {
            
            System.out.println("Exception: " + ex.toString());
            
        }
        
    }
    
}
