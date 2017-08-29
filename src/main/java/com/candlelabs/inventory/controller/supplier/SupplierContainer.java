package com.candlelabs.inventory.controller.supplier;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import com.candlelabs.inventory.model.Supplier;
import com.candlelabs.inventory.model.Product;
import com.candlelabs.inventory.util.FXUtil;
import com.candlelabs.inventory.util.ValidatorUtil;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;

/**
 *
 * @author VakSF
 */
public class SupplierContainer {
    
    @FXML
    private TableView<Supplier> suppliersTV;
    
    private ObservableList<Supplier> suppliers;
    
    @FXML
    private TableView<Product> productsTV;
    
    private ObservableList<Product> products;
    
    @FXML
    private JFXTextField companyTF, contactTF, addressTF, phoneTF, 
            cityTF, countryTF, searchTF;
    
    @FXML
    private JFXButton submitB;
    
    @FXML
    private Label infoL;
    
    private ValidatorUtil validator;
    
    private boolean editing;

    protected SupplierContainer() {
        this.editing = false;
    }
    
    protected void initValidators() {
        
        this.validator = new ValidatorUtil(
                companyTF, contactTF, addressTF, 
                phoneTF, cityTF, countryTF
        );
        
    }
    
    public void initSuppliers(List<Supplier> suppliers) {
        
        this.suppliers = FXCollections.observableArrayList();
        
        FilteredList<Supplier> filteredData = new FilteredList<>(
                this.suppliers, (Supplier supplier) -> true);

        this.searchTF.textProperty().addListener((observable, oldValue, word) -> {
            
            filteredData.setPredicate((Supplier supplier) -> {
                
                this.suppliersTV.getSelectionModel().clearSelection();
                
                this.validator.clearFields();
                
                if (word == null || word.isEmpty()) {
                    return true;
                }
                
                String lowerWord = word.toLowerCase();
                
                if (supplier.getCompany().toLowerCase().contains(lowerWord)) {
                    
                    return true;
                    
                } else if (supplier.getContact().toLowerCase().contains(lowerWord)) {
                    
                    return true;
                    
                }
                
                return false;
                
            });
            
        });

        SortedList<Supplier> sortedList = new SortedList<>(filteredData);
        
        sortedList.comparatorProperty().bind(suppliersTV.comparatorProperty());

        this.suppliersTV.setItems(sortedList);
        
        this.suppliers.setAll(suppliers);
        
        if (!this.suppliers.isEmpty()) {
            this.suppliersTV.getSelectionModel().selectFirst();
            this.updateFields(FXUtil.selectedTableItem(this.suppliersTV));
        }
        
        this.suppliersTV.getSelectionModel().selectedItemProperty()
                .addListener(($obs, $old, $new) -> updateFields($new));
        
    }
    
    protected void initProducts() {
        
        this.products = FXCollections.observableArrayList();
        
        this.productsTV.setItems(this.products);
        
    }
    
    private void updateFields(Supplier supplier) {
        
        boolean update = true;
        
        if (isEditing()) {
            
            Optional<ButtonType> result = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Desea salir del modo edición?"
            ).showAndWait();
            
            if (result.get() != ButtonType.OK) {
                update = false;
            }
            
        }
        
        if (update) {
            
            if (supplier != null) {
                
                this.infoL.setText("Visualizando proveedor");
                this.infoL.setTextFill(Color.web("#4596d9"));
                
                this.validator.setEditable(false);
                
                List<Product> supplierProducts = supplier.getProducts();
                
                this.companyTF.setText(FXUtil.objectStringValue(supplier.getCompany()));
                this.contactTF.setText(FXUtil.objectStringValue(supplier.getContact()));
                this.addressTF.setText(FXUtil.objectStringValue(supplier.getAddress()));
                this.phoneTF.setText(FXUtil.objectStringValue(supplier.getPhone()));
                this.cityTF.setText(FXUtil.objectStringValue(supplier.getCity()));
                this.countryTF.setText(FXUtil.objectStringValue(supplier.getCountry()));
                
                this.products.setAll(supplierProducts);
                
                this.setEditing(false);
                
            } else {
                
                this.validator.clearFields();
                
                this.products.clear();
                
            }
            
        }
        
    }
    
    protected Supplier getSupplier() {
        
        return new Supplier(
                
                this.companyTF.getText(),
                this.contactTF.getText(),
                this.addressTF.getText(),
                this.cityTF.getText(),
                this.countryTF.getText(),
                this.phoneTF.getText()
                
        );
        
    }

    public TableView<Supplier> getSuppliersTV() {
        return suppliersTV;
    }

    public ObservableList<Supplier> getSuppliers() {
        return suppliers;
    }

    public TableView<Product> getProductsTV() {
        return productsTV;
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public JFXTextField getCompanyTF() {
        return companyTF;
    }

    public JFXTextField getContactTF() {
        return contactTF;
    }

    public JFXTextField getAddressTF() {
        return addressTF;
    }

    public JFXTextField getPhoneTF() {
        return phoneTF;
    }

    public JFXTextField getCityTF() {
        return cityTF;
    }

    public JFXTextField getCountryTF() {
        return countryTF;
    }

    public JFXTextField getSearchTF() {
        return searchTF;
    }

    public ValidatorUtil getValidator() {
        return validator;
    }
    
    public JFXButton getSubmitB() {
        return submitB;
    }

    public Label getInfoL() {
        return infoL;
    }
    
    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }
    
}
