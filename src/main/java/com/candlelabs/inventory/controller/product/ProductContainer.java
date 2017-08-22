package com.candlelabs.inventory.controller.product;

import javafx.fxml.FXML;

import javafx.scene.control.TableView;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXComboBox;

import com.candlelabs.inventory.model.Category;
import com.candlelabs.inventory.model.Measurement;
import com.candlelabs.inventory.model.Product;
import com.candlelabs.inventory.model.Supplier;
import com.candlelabs.inventory.util.FXUtil;
import com.candlelabs.inventory.util.ValidatorUtil;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Arturo Cordero
 */
public class ProductContainer {
    
    @FXML
    private TableView<Product> productsTV;
    
    @FXML
    private JFXTextField nameTF, descriptionTF, priceTF, brandTF;
    
    @FXML
    private JFXComboBox<Measurement> measurementCB;
    
    @FXML
    private JFXComboBox<Category> categoryCB;
    
    @FXML
    private JFXComboBox<Supplier> supplierCB;
    
    private ObservableList<Product> productList;
    
    private ValidatorUtil validator;

    protected ProductContainer() {
        System.out.println("ProductContainer");
    }
    
    protected void initTV(List<Product> productList) {
        
        this.productList = FXCollections.observableArrayList();
        
        this.productsTV.setItems(this.productList);
        this.productList.addAll(productList);
        
        if (!this.productList.isEmpty())
            this.productsTV.getSelectionModel().selectFirst();
        
        this.productsTV.getSelectionModel().selectedItemProperty()
                .addListener(($obs, $old, $new) -> updateFields($new));
        
    }
    
    protected void initValidators() {
        
        this.validator = new ValidatorUtil(
                nameTF, descriptionTF, priceTF, brandTF, 
                categoryCB, supplierCB, measurementCB
        );
        
    }
    
    protected void initCBs(
            List<Category> categories,
            List<Supplier> suppliers,
            List<Measurement> measurements) {
        
        this.categoryCB.getItems().addAll(categories);
        this.supplierCB.getItems().addAll(suppliers);
        this.measurementCB.getItems().addAll(measurements);
        
    }
    
    private void updateFields(Product product) {
        
        if (product != null) {
            
            this.nameTF.setText(FXUtil.objectStringValue(product.getName()));
            this.descriptionTF.setText(FXUtil.objectStringValue(product.getDescription()));
            this.priceTF.setText(FXUtil.objectStringValue(product.getUnitPrice()));
            this.brandTF.setText(FXUtil.objectStringValue(product.getBrand()));
            
            this.categoryCB.setValue(product.getCategory());
            this.supplierCB.setValue(product.getSupplier());
            this.measurementCB.setValue(product.getMeasurement());
            
        }
        
    }
    
}
