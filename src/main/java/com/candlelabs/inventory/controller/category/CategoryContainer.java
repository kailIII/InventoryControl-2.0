package com.candlelabs.inventory.controller.category;

import javafx.fxml.FXML;

import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXButton;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;

import javafx.scene.control.TableView;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import javafx.scene.paint.Color;

import com.candlelabs.inventory.util.ValidatorUtil;

import com.candlelabs.inventory.model.Category;
import com.candlelabs.inventory.model.Product;
import com.candlelabs.inventory.util.FXUtil;


/**
 * 
 * @author VakSF
 */
public class CategoryContainer {
    
    @FXML
    private TableView<Category> categoriesTV;
    
    private ObservableList<Category> categories;
    
    @FXML
    private TableView<Product> productsTV;
    
    private ObservableList<Product> products;
    
    @FXML
    private JFXTextField nameTF, searchTF;
    
    @FXML
    private JFXButton submitB;
    
    @FXML
    private Label infoL;
    
    private ValidatorUtil validator;
    
    private boolean editing;
    
    protected CategoryContainer() {
        this.editing = false;
    }
    
    protected void initValidators() {
        
        this.validator = new ValidatorUtil(
                nameTF
        );
        
    }
    
    public void initCategories(List<Category> categories) {
        
        this.categories = FXCollections.observableArrayList();
        
        FilteredList<Category> filteredData = new FilteredList<>(
                this.categories, (Category category) -> true);

        this.searchTF.textProperty().addListener((observable, oldValue, word) -> {
            
            filteredData.setPredicate((Category category) -> {
                
                if (word == null || word.isEmpty()) {
                    return true;
                }
                
                String lowerWord = word.toLowerCase();
                
                if (category.getName().toLowerCase().contains(lowerWord)) {
                    
                    return true;
                    
                } else if (category.getId().toString().toLowerCase().contains(lowerWord)) {
                    
                    return true;
                    
                }
                
                return false;
                
            });
            
        });

        SortedList<Category> sortedList = new SortedList<>(filteredData);
        
        sortedList.comparatorProperty().bind(categoriesTV.comparatorProperty());

        this.categoriesTV.setItems(sortedList);
        
        this.categories.setAll(categories);
        
        if (!this.categories.isEmpty()) {
            this.categoriesTV.getSelectionModel().selectFirst();
            this.updateFields(FXUtil.selectedTableItem(this.categoriesTV));
        }
        
        this.categoriesTV.getSelectionModel().selectedItemProperty()
                .addListener(($obs, $old, $new) -> updateFields($new));
        
    }
    
    protected void initProducts() {
        
        this.products = FXCollections.observableArrayList();
        
        this.productsTV.setItems(this.products);
        
    }
    
    private void updateFields(Category category) {
        
        boolean update = true;
        
        if (isEditing()) {
            
            Optional<ButtonType> result = new Alert(
                    AlertType.CONFIRMATION,
                    "Desea salir del modo edición?"
            ).showAndWait();
            
            if (result.get() != ButtonType.OK) {
                update = false;
            }
            
        }
        
        if (update) {
            
            if (category != null) {
                
                getInfoL().setText("Visualizando categoría");
                getInfoL().setTextFill(Color.web("#4596d9"));
                getValidator().setEditable(false);
                
                List<Product> categoryProducts = category.getProducts();
                
                this.nameTF.setText(FXUtil.objectStringValue(category.getName()));
                
                this.products.setAll(categoryProducts);
                
                this.setEditing(false);
                
            }
            
        }
        
    }
    
    protected Category getCategory() {
        
        return new Category(
                this.nameTF.getText()
        );
        
    }

    public ObservableList<Category> getCategories() {
        return categories;
    }

    public ObservableList<Product> getProducts() {
        return products;
    }
    
    public TableView<Category> getCategoriesTV() {
        return categoriesTV;
    }

    public TableView<Product> getProductsTV() {
        return productsTV;
    }

    public JFXTextField getNameTF() {
        return nameTF;
    }

    public JFXButton getSubmitB() {
        return submitB;
    }

    public Label getInfoL() {
        return infoL;
    }

    public ValidatorUtil getValidator() {
        return validator;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }
    
}
