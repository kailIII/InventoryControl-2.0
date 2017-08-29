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
import com.candlelabs.inventory.rmi.implementations.service.CallbackClientImpl;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author Arturo Cordero
 */
public class ProductController extends ProductContainer 
        implements Initializable, MastermindInitializer {
    
    // Belongs To    
    private MastermindController mastermindController;
    
    // Has 
    private CategoryController categoryController;
    private SupplierController supplierController;
    private MeasurementController measurementController;
    
    private ProductService productService;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.initServices();
        
        try {
            
            this.initValidators();
            
            this.initProducts();
            
            this.initCBs(
                    this.productService.listCategories(), 
                    this.productService.listSuppliers(), 
                    this.productService.listMeasurements()
            );
            
            this.initViews();
            
        } catch (RemoteException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        
    }
    
    @Override
    public void init(MastermindController controller) {
        this.mastermindController = controller;
    }
    
    private void initServices() {
        
        try {
            
            this.productService = (ProductService) RMIClient.getRegistry().lookup("productService");
            
        } catch (RemoteException | NotBoundException ex) {
            
            System.out.println("Exception: " + ex.toString());
            
        }
        
    }
    
    public void initProducts() throws RemoteException {
        this.initProducts(productService.listProducts());
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
    
    @FXML
    private void createEdit() {
        
        if (getValidator().validateFields()) {
            
            String action = getSubmitB().getText().toLowerCase();
            
            if (action.equals("crear")) {
                
                this.createProduct();
                
            } else {
                
                if (action.equals("editar")) {
                    
                    this.editProduct();
                    
                }
                
            }
            
        } else {
            
            getValidator().emptyFields().show();
            
        }
        
    }
    
    private void createProduct() {
            
        Product product = this.getProduct();
        
        try {
            
            Integer productId = this.productService.createProduct(product);
            
            if (productId != null) {
                
                product.setId(productId);
                
                this.productAction(product, "create");
                
                setEditing(false);
                
                getProducts().add(product);
                getProductsTV().getSelectionModel().select(product);
                
                new Alert(
                        AlertType.INFORMATION,
                        "Producto creado correctamente"
                ).show();
                
                getValidator().setEditable(false);
                getSubmitB().setDisable(true);
                
            } else {
                
                new Alert(
                        AlertType.ERROR,
                        "No se ha podido crear el producto. El nombre ya existe"
                ).show();
                
            }
            
        } catch (RemoteException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        
    }
    
    private void editProduct() {
        
        Product product = FXUtil.selectedTableItem(getProductsTV());
        int index = FXUtil.selectedTableIndex(getProductsTV());
        
        if (product != null) {
            
            product.editProduct(this.getProduct());
            
            try {
                
                boolean updated = this.productService.updateProduct(product);
                
                if (updated) {
                    
                    this.productAction(product, "edit", index);
                    
                    setEditing(false);
                    
                    getProductsTV().refresh();
                    getProductsTV().getSelectionModel().selectFirst();
                    
                    new Alert(
                            AlertType.INFORMATION,
                            "Producto editado correctamente"
                    ).show();
                    
                    getSubmitB().setText("Crear");
                    getSubmitB().setDisable(true);
                    
                    getValidator().setEditable(false);
                    
                } else {
                    
                    new Alert(
                            AlertType.ERROR,
                            "El producto no pudo ser editado. El nombre ya existe"
                    ).show();
                    
                }
                
            } catch (RemoteException ex) {
                System.out.println("Exception: " + ex.toString());
            }
            
        }
        
    }
    
    @FXML
    private void deleteProduct() {
        
        Product product = FXUtil.selectedTableItem(getProductsTV());
        
        if (product != null) {
            
            Optional<ButtonType> result = new Alert(
                    AlertType.CONFIRMATION,
                    "Está seguro de eliminar el producto '" + product.getName() + "'?"
            ).showAndWait();
            
            if (result.get() == ButtonType.OK) {
                
                try {
                    
                    boolean deleted = this.productService.deleteProduct(product);
                    
                    if (deleted) {
                        
                        this.productAction(product, "delete");
                        
                        getProducts().remove(product);
                        
                        new Alert(
                                AlertType.INFORMATION,
                                "Producto eliminado correctamente"
                        ).show();
                        
                    } else {
                        
                        new Alert(
                                AlertType.ERROR,
                                "No se ha podido eliminar el producto"
                        ).show();
                        
                    }
                    
                } catch (RemoteException ex) {
                    System.out.println("Exception: " + ex.toString());
                }
                
            }
            
        }
        
    }
    
    @FXML
    private void newProduct() {
        
        getInfoL().setText("Creando producto");
        getInfoL().setTextFill(Color.web("#d3cf43"));
        
        getValidator().setEditable(true);
        getValidator().clearFields();
        
        setEditing(true);
        
        getSubmitB().setDisable(false);
        getSubmitB().setText("Crear");
    }
    
    @FXML
    private void edit() {
        
        getInfoL().setText("Editando producto");
        getInfoL().setTextFill(Color.web("#45d852"));
        
        getValidator().setEditable(true);
        getNameTF().requestFocus();
        
        setEditing(true);
        
        getSubmitB().setDisable(false);
        getSubmitB().setText("Editar");
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
    
    @FXML
    private void submit(KeyEvent event) throws IOException{
        
        if (event.getCode().equals(KeyCode.ENTER)) 
            getSubmitB().fire();
        
    }
    
    private void productAction(Product product, String action, int index) throws RemoteException {
        
        if (this.mastermindController != null) {
            
            CallbackClientImpl client = this.getClient();
            
            client.getServer().productAction(client, product, action, index);
            
        }
        
        if (this.categoryController != null)
            this.categoryController.initCategories();
        
        if (this.measurementController != null)
            this.measurementController.initMeasurements();
        
        if (this.supplierController != null)
            this.supplierController.initSuppliers();
        
        
    }
    
    private void productAction(Product product, String action) throws RemoteException {
        this.productAction(product, action, 0);
    }
    
    private CallbackClientImpl getClient() {
        return this.getMastermindController().getClient();
    }
    
    public MastermindController getMastermindController() {
        return mastermindController;
    }

    public CategoryController getCategoryController() {
        return categoryController;
    }

    public SupplierController getSupplierController() {
        return supplierController;
    }

    public MeasurementController getMeasurementController() {
        return measurementController;
    }

    public ProductService getProductService() {
        return productService;
    }
    
}
