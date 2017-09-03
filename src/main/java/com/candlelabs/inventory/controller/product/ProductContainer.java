package com.candlelabs.inventory.controller.product;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

import javafx.scene.control.TableView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXComboBox;

import com.candlelabs.inventory.model.Category;
import com.candlelabs.inventory.model.Measurement;
import com.candlelabs.inventory.model.Product;
import com.candlelabs.inventory.model.Supplier;

import com.candlelabs.inventory.util.FXUtil;
import com.candlelabs.inventory.util.ValidatorUtil;
import com.jfoenix.controls.JFXButton;
import java.util.Optional;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;

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
    private JFXTextField nameTF, descriptionTF, unitPriceTF, 
            brandTF, reorderLevelTF, unitsInStockTF, productNameTF, productDescriptionTF;
    
    @FXML
    private JFXButton submitB;
    
    @FXML
    private JFXComboBox<Measurement> measurementCB;
    private ObservableList<Measurement> measurements = FXCollections.observableArrayList();
    
    @FXML
    private JFXComboBox<Category> categoryCB, productCategoryCB;
    private ObservableList<Category> categories = FXCollections.observableArrayList();
    
    @FXML
    private JFXComboBox<Supplier> supplierCB;
    private ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
    
    @FXML
    private AnchorPane categoryPane, supplierPane, measurementPane;
    
    private ObservableList<Product> products;
    
    private ValidatorUtil validator;
    
    private boolean editing;

    protected ProductContainer() {
        this.editing = false;
    }
    
    protected void initProducts(List<Product> products) {
        
        this.products = FXCollections.observableArrayList();
        
        FilteredList<Product> filteredData = new FilteredList<>(
                this.products, (Product product) -> true);

        this.productNameTF.textProperty().addListener((observable, oldValue, word) -> {
            
            filteredData.setPredicate((Product product) -> {
                
                this.productsTV.getSelectionModel().clearSelection();
                
                this.validator.clearFields();
                
                if (word == null || word.isEmpty()) {
                    return true;
                }
                
                String lowerWord = word.toLowerCase();
                
                if (product.getName().toLowerCase().contains(lowerWord)) {
                    
                    return true;
                    
                } else if (product.getId().toString().toLowerCase().contains(lowerWord)) {
                    
                    return true;
                    
                }
                
                return false;
                
            });
            
        });
        
        this.productDescriptionTF.textProperty().addListener((observable, oldValue, word) -> {
            
            filteredData.setPredicate((Product product) -> {
                
                this.productsTV.getSelectionModel().clearSelection();
                this.validator.clearFields();
                
                if (word == null || word.isEmpty()) {
                    return true;
                }
                
                String lowerWord = word.toLowerCase();
                
                return product.getDescription().toLowerCase().contains(lowerWord);
                
            });
            
        });
        
        this.productCategoryCB.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    
                    filteredData.setPredicate((Product product) -> {
                        
                        this.productsTV.getSelectionModel().clearSelection();
                        this.validator.clearFields();
                        
                        if (newValue == null || newValue.getName().isEmpty()) {
                            return true;
                        }
                        
                        String lowerWord = newValue.getName().toLowerCase();
                        
                        return product.getCategory().getName().toLowerCase().contains(lowerWord);
                        
                    });
                    
                });
        

        SortedList<Product> sortedList = new SortedList<>(filteredData);
        
        sortedList.comparatorProperty().bind(productsTV.comparatorProperty());
        
        this.productsTV.setItems(sortedList);
        this.products.addAll(products);
        
        if (!this.products.isEmpty()) {
            this.productsTV.getSelectionModel().selectFirst();
            this.updateFields(FXUtil.selectedTableItem(this.productsTV));
        }
        
        this.productsTV.getSelectionModel().selectedItemProperty()
                .addListener(($obs, $old, $new) -> updateFields($new));
        
    }
    
    protected void initValidators() {
        
        this.validator = new ValidatorUtil(
                nameTF, descriptionTF, unitPriceTF,
                brandTF, reorderLevelTF, unitsInStockTF,
                categoryCB, supplierCB, measurementCB
        );
        
    }
    
    public void initCBs(
            List<Category> categories,
            List<Supplier> suppliers,
            List<Measurement> measurements) {
        
        this.categories.setAll(categories);
        this.suppliers.setAll(suppliers);
        this.measurements.setAll(measurements);
        
        this.productCategoryCB.setItems(this.categories);
        this.categoryCB.setItems(this.categories);
        this.supplierCB.setItems(this.suppliers);
        this.measurementCB.setItems(this.measurements);
    }
    
    private void updateFields(Product product) {
        
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
            
            if (product != null) {
                
                this.infoL.setText("Visualizando producto");
                this.infoL.setTextFill(Color.web("#4596d9"));
                
                this.validator.setEditable(false);
                
                this.nameTF.setText(FXUtil.objectStringValue(product.getName()));
                this.descriptionTF.setText(FXUtil.objectStringValue(product.getDescription()));
                this.unitPriceTF.setText(FXUtil.objectStringValue(product.getUnitPrice()));
                this.brandTF.setText(FXUtil.objectStringValue(product.getBrand()));
                
                this.reorderLevelTF.setText(FXUtil.objectStringValue(product.getReorderLevel()));
                this.unitsInStockTF.setText(FXUtil.objectStringValue(product.getUnitsInStock()));
                
                this.categoryCB.setValue(product.getCategory());
                this.supplierCB.setValue(product.getSupplier());
                this.measurementCB.setValue(product.getMeasurement());
                
                this.setEditing(false);
                
            } else {
                
                this.validator.clearFields();
                
                this.validator.clearFields();
                
            }
            
        }
        
    }
    
    protected Product getProduct() {
        
        return new Product(
                
                "code", // -> Generar código? 
                
                /**
                 * 
                 * Todo correcto
                 * 
                 */
                this.descriptionTF.getText(), 
                this.nameTF.getText(), 
                
                Double.parseDouble(this.unitPriceTF.getText()), 
                
                this.brandTF.getText(), 
                
                Integer.parseInt(this.reorderLevelTF.getText()), 
                Integer.parseInt(this.unitsInStockTF.getText()), 
                
                this.categoryCB.getValue(), 
                this.supplierCB.getValue(), 
                this.measurementCB.getValue()
                
        );
        
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

    public ValidatorUtil getValidator() {
        return validator;
    }

    public Label getInfoL() {
        return infoL;
    }

    public TableView<Product> getProductsTV() {
        return productsTV;
    }

    public JFXTextField getNameTF() {
        return nameTF;
    }

    public JFXTextField getDescriptionTF() {
        return descriptionTF;
    }

    public JFXTextField getUnitPriceTF() {
        return unitPriceTF;
    }

    public JFXTextField getBrandTF() {
        return brandTF;
    }

    public JFXTextField getReorderLevelTF() {
        return reorderLevelTF;
    }

    public JFXTextField getUnitsInStockTF() {
        return unitsInStockTF;
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public JFXComboBox<Measurement> getMeasurementCB() {
        return measurementCB;
    }

    public JFXComboBox<Category> getCategoryCB() {
        return categoryCB;
    }

    public JFXComboBox<Supplier> getSupplierCB() {
        return supplierCB;
    }

    public JFXButton getSubmitB() {
        return submitB;
    }
    
    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }
    
}
