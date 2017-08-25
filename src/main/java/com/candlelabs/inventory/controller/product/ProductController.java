package com.candlelabs.inventory.controller.product;

import java.net.URL;
import java.io.IOException;

import java.rmi.RemoteException;
import java.util.ResourceBundle;

import com.candlelabs.inventory.controller.category.CategoryController;
import com.candlelabs.inventory.controller.supplier.SupplierController;
import com.candlelabs.inventory.controller.measurement.MeasurementController;

import com.candlelabs.inventory.controller.interfaces.ProductInitializer;
import com.candlelabs.inventory.rmi.interfaces.service.ProductService;

import com.candlelabs.inventory.model.Product;
import com.candlelabs.inventory.util.FXUtil;

import java.rmi.NotBoundException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

import com.candlelabs.inventory.RMIClient;
import com.candlelabs.inventory.controller.interfaces.MastermindInitializer;
import com.candlelabs.inventory.controller.mastermind.MastermindController;

/**
 *
 * @author Arturo Cordero
 */
public class ProductController extends ProductContainer implements Initializable, MastermindInitializer {
    
    // Belongs To    
    private MastermindController mastermindController;
    
    // Has 
    private CategoryController categoryController;
    private SupplierController supplierController;
    private MeasurementController measurementController;
    
    public ProductService productService;
    
    private void initServices() {
        
        try {
            
            this.productService = (ProductService) RMIClient.getRegistry().lookup("productService");
            
        } catch (RemoteException | NotBoundException ex) {
            
            System.out.println("Exception: " + ex.toString());
            
        }
        
    }
    
    @Override
    public void init(MastermindController controller) {
        this.mastermindController = controller;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.initServices();
        
        try {
            
            super.initValidators();
            
            super.initTV(productService.listProducts());
            
            super.initCBs(
                    this.productService.listCategories(), 
                    this.productService.listSuppliers(), 
                    this.productService.listMeasurements()
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
            
            this.mastermindController.newProduct(product);
            
            new Alert(
                    AlertType.INFORMATION,
                    product.toString()
            ).showAndWait();
            
        } else {
            
            getValidator().emptyFields().show();
            
        }
        
    }        
            
    private void initViews() {
        
        this.categoryController = this.initView(
                "/view/category/Category.fxml",
                CategoryController.class, this.getCategoryPane());
        
        this.supplierController = this.initView(
                "/view/supplier/Supplier.fxml", 
                SupplierController.class, this.getSupplierPane());
        
        this.measurementController = this.initView(
                "/view/measurement/Measurement.fxml", 
                MeasurementController.class, this.getMeasurementPane());
        
    }
    
    private <T extends ProductInitializer> T initView(
            String fxml, Class<T> clazz, AnchorPane pane) {
        
        T controller = null;
        
        try {
            
            FXMLLoader loader = FXUtil.loader(fxml, getClass());
            
            AnchorPane anchorPane = loader.<AnchorPane>load();
            controller = loader.<T>getController();
            
            controller.init(this);
            
            FXUtil.setAnchor(anchorPane);
            
            pane.getChildren().setAll(anchorPane);
            
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        
        return controller;
        
    }
    
}
