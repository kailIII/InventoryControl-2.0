package com.candlelabs.inventory.controller.product;

import java.net.URL;
import java.io.IOException;

import java.rmi.RemoteException;
import java.util.ResourceBundle;

import static com.candlelabs.inventory.RMIClient.productService;
import static com.candlelabs.inventory.RMIClient.categoryService;
import static com.candlelabs.inventory.RMIClient.supplierService;
import static com.candlelabs.inventory.RMIClient.measurementService;

import com.candlelabs.inventory.controller.category.CategoryController;
import com.candlelabs.inventory.controller.supplier.SupplierController;
import com.candlelabs.inventory.controller.measurement.MeasurementController;

import com.candlelabs.inventory.controller.interfaces.ProductInitializer;
import com.candlelabs.inventory.model.Product;

import com.candlelabs.inventory.util.FXUtil;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Arturo Cordero
 */
public class ProductController extends ProductContainer implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            
            super.initValidators();
            
            super.initTV(productService.listProducts());
            
            super.initCBs(
                    categoryService.listCategories(), 
                    supplierService.listSuppliers(), 
                    measurementService.listMeasurements()
            );
            
            this.initViews();
            
        } catch (RemoteException ex) {
            
            System.out.println("Exception: " + ex.toString());
            
        }
        
    }
    
    @FXML
    private void createProduct() {
        
        if (getValidator().validateFields()) {
            
            Product product = getProduct();
            
            new Alert(
                    AlertType.INFORMATION,
                    product.toString()
            ).showAndWait();
            
        } else {
            
            getValidator().emptyFields().show();
            
        }
        
    }
    
    private void initViews() {
        
        this.initView(
                "/view/category/Category.fxml",
                CategoryController.class, this.getCategoryPane());
        
        this.initView(
                "/view/supplier/Supplier.fxml", 
                SupplierController.class, this.getSupplierPane());
        
        this.initView(
                "/view/measurement/Measurement.fxml", 
                MeasurementController.class, this.getMeasurementPane());
        
    }
    
    private <T extends ProductInitializer> void initView(
            String fxml, Class<T> clazz, AnchorPane pane) {
        
        try {
            
            FXMLLoader loader = FXUtil.loader(fxml, getClass());
            
            AnchorPane anchorPane = loader.<AnchorPane>load();
            loader.<T>getController().init(this);
            
            FXUtil.setAnchor(anchorPane);
            
            pane.getChildren().setAll(anchorPane);
            
        } catch (IOException ex) {
            
        }
        
    }
    
}
