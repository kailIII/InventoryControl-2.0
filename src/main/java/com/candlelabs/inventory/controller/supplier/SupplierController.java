package com.candlelabs.inventory.controller.supplier;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.candlelabs.inventory.RMIClient;
import com.candlelabs.inventory.controller.interfaces.ProductInitializer;
import com.candlelabs.inventory.controller.product.ProductController;
import com.candlelabs.inventory.model.Supplier;
import com.candlelabs.inventory.rmi.implementations.service.CallbackClientImpl;

import com.candlelabs.inventory.rmi.interfaces.service.SupplierService;
import com.candlelabs.inventory.util.FXUtil;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author VakSF
 */
public class SupplierController extends SupplierContainer 
        implements Initializable, ProductInitializer {
    
    // Belongs to
    private ProductController productController;
    
    private SupplierService supplierService;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.initServices();
        
        try {
            
            this.initValidators();
            
            this.initProducts();
            
            this.initSuppliers();
            
        } catch (RemoteException ex) {
            
        }
        
    }

    @Override
    public void init(ProductController controller) {
        this.productController = controller;
    }
    
    private void initServices() {
        
        try {
            
            this.supplierService = (SupplierService) RMIClient.getRegistry().lookup("supplierService");
            
        } catch (RemoteException | NotBoundException ex) {
            
            System.out.println("Exception: " + ex.toString());
            
        }
        
    }
    
    public void initSuppliers() throws RemoteException {
        this.initSuppliers(this.supplierService.listSuppliers());
    }
    
    @FXML
    private void createEdit() {
        
        if (getValidator().validateFields()) {
            
            String action = getSubmitB().getText().toLowerCase();
            
            if (action.equals("crear")) {
                
                this.createSupplier();
                
            } else {
                
                if (action.equals("editar")) {
                    
                    this.editSupplier();
                    
                }
                
            }
            
        } else {
            
            getValidator().emptyFields().show();
            
        }
        
    }
    
    private void createSupplier() {
            
        Supplier supplier = this.getSupplier();
        
        try {
            
            Integer supplierId = this.supplierService.createSupplier(supplier);
            
            if (supplierId != null) {
                
                supplier.setId(supplierId);
                
                this.supplierAction(supplier, "create");
                
                setEditing(false);
                
                getSuppliers().add(supplier);
                getSuppliersTV().getSelectionModel().select(supplier);
                
                new Alert(
                        Alert.AlertType.INFORMATION, 
                        "Proveedor creado correctamente"
                ).show();
                
                getValidator().setEditable(false);
                getSubmitB().setDisable(true);
                
            } else {
                
                new Alert(
                        Alert.AlertType.ERROR,
                        "No se ha podido crear el proveedor. El nombre ya existe"
                ).show();
                
            }
            
        } catch (RemoteException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        
    }
    
    private void editSupplier() {
        
        Supplier supplier = FXUtil.selectedTableItem(getSuppliersTV());
        int index = FXUtil.selectedTableIndex(getSuppliersTV());
        
        if (supplier != null) {
            
            supplier.editSupplier(this.getSupplier());
            
            try {
                
                boolean updated = this.supplierService.updateSupplier(supplier);
                
                if (updated) {
                    
                    this.supplierAction(supplier, "edit");
                    
                    setEditing(false);
                    
                    getSuppliersTV().refresh();
                    getSuppliersTV().getSelectionModel().selectFirst();
                    
                    new Alert(
                            Alert.AlertType.INFORMATION,
                            "Proveedor editado correctamente"
                    ).show();
                    
                    getSubmitB().setText("Crear");
                    getSubmitB().setDisable(true);
                    
                    getValidator().setEditable(false);
                    
                } else {
                    
                    new Alert(
                            Alert.AlertType.ERROR,
                            "El proveedor no pudo ser editado. El nombre ya existe"
                    ).show();
                    
                }
                
            } catch (RemoteException ex) {
                System.out.println("Exception: " + ex.toString());
            }
            
        }
        
    }
    
    @FXML
    private void deleteSupplier() {
        
        Supplier supplier = FXUtil.selectedTableItem(getSuppliersTV());
        
        if (supplier != null) {
            
            Optional<ButtonType> result = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Está seguro de eliminar al proveedor '" + supplier.getCompany() + "'?"
            ).showAndWait();
            
            if (result.get() == ButtonType.OK) {
                
                try {
                    
                    boolean deleted = this.supplierService.deleteSupplier(supplier);
                    
                    if (deleted) {
                        
                        this.supplierAction(supplier, "delete");
                        
                        getSuppliers().remove(supplier);
                        
                        new Alert(
                                Alert.AlertType.INFORMATION,
                                "Proveedor eliminado correctamente"
                        ).show();
                        
                    } else {
                        
                        new Alert(
                                Alert.AlertType.ERROR,
                                "No se ha podido eliminar al proveedor"
                        ).show();
                        
                    }
                    
                } catch (RemoteException ex) {
                    System.out.println("Exception: " + ex.toString());
                }
                
            }
            
        }
        
    }
    
    @FXML
    private void newSupplier() {
        
        getInfoL().setText("Creando proveedor");
        getInfoL().setTextFill(Color.web("#d3cf43"));
        
        getValidator().setEditable(true);
        getValidator().clearFields();
        
        setEditing(true);
        
        getSubmitB().setDisable(false);
        getSubmitB().setText("Crear");
    }
    
    @FXML
    private void edit() {
        
        getInfoL().setText("Editando proveedor");
        getInfoL().setTextFill(Color.web("#45d852"));
        
        getValidator().setEditable(true);
        getCompanyTF().requestFocus();
        
        setEditing(true);
        
        getSubmitB().setDisable(false);
        getSubmitB().setText("Editar");
    }
    
    @FXML
    private void submit(KeyEvent event) throws IOException{
        
        if (event.getCode().equals(KeyCode.ENTER)) 
            getSubmitB().fire();
        
    }
    
    private void supplierAction(Supplier supplier, String action, int index) throws RemoteException {
        
        if (this.productController != null) {
            
            CallbackClientImpl client = this.getClient();
            
            client.getServer().supplierAction(client, supplier, action, index);
            
            if (action.equals("create")) {
                
                this.productController.getSuppliers().add(supplier);
                
            } else {
                
                if (action.equals("edit")) {
                    
                    this.productController.initProducts();
                    
                } else {
                    
                    if (action.equals("delete")) {
                        
                        this.productController.getSuppliers().remove(supplier);
                        
                    }
                    
                }
                
            }
            
        }
        
    }
    
    private void supplierAction(Supplier supplier, String action) throws RemoteException {
        this.supplierAction(supplier, action, 0);
    }
    
    private CallbackClientImpl getClient() {
        return this.productController
                .getMastermindController().getClient();
    }
    
    public ProductController getProductController() {
        return productController;
    }

    public SupplierService getSupplierService() {
        return supplierService;
    }
    
}
