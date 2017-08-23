package com.candlelabs.inventory.controller.product;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXComboBox;

import com.candlelabs.inventory.model.Category;
import com.candlelabs.inventory.model.Measurement;
import com.candlelabs.inventory.model.Product;
import com.candlelabs.inventory.model.Supplier;
import com.candlelabs.inventory.util.FXUtil;
import com.candlelabs.inventory.util.ValidatorUtil;
import javafx.scene.control.Label;

/**
 *
 * @author Arturo Cordero
 */
public class ProductContainer {
    
    @FXML
    private Label infoL;
    
    @FXML
    private TableView<Product> productsTV;
    
    @FXML
    private JFXTextField nameTF, descriptionTF, priceTF, brandTF;
    
    @FXML
    private JFXComboBox<Measurement> measurementCB;
    private ObservableList<Measurement> measurements = FXCollections.observableArrayList();
    
    @FXML
    private JFXComboBox<Category> categoryCB;
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    
    @FXML
    private JFXComboBox<Supplier> supplierCB;
    private ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
    
    @FXML
    private AnchorPane categoryPane, supplierPane, measurementPane;
    
    private ObservableList<Product> productList;
    
    private ValidatorUtil validator;

    protected ProductContainer() {
        System.out.println("ProductContainer");
    }
    
    protected void initTV(List<Product> productList) {
        
        this.productList = FXCollections.observableArrayList();
        
        this.productsTV.setItems(this.productList);
        this.productList.addAll(productList);
        
        if (!this.productList.isEmpty()) {
            this.productsTV.getSelectionModel().selectFirst();
            this.updateFields(FXUtil.selectedTableItem(this.productsTV));
        }
        
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
        
        this.categories.setAll(categories);
        this.suppliers.setAll(suppliers);
        this.measurements.setAll(measurements);
        
        this.categoryCB.setItems(this.categories);
        this.supplierCB.setItems(this.suppliers);
        this.measurementCB.setItems(this.measurements);
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

    public AnchorPane getCategoryPane() {
        return categoryPane;
    }

    public AnchorPane getSupplierPane() {
        return supplierPane;
    }

    public AnchorPane getMeasurementPane() {
        return measurementPane;
    }

    public ObservableList<Measurement> getMeasurements() {
        return measurements;
    }

    public ObservableList<Category> getCategories() {
        return categories;
    }

    public ObservableList<Supplier> getSuppliers() {
        return suppliers;
    }
    
}
