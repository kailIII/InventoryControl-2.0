package com.candlelabs.inventory.controller.measurement;

import com.candlelabs.inventory.model.Measurement;
import com.candlelabs.inventory.model.Product;
import com.candlelabs.inventory.util.FXUtil;
import com.candlelabs.inventory.util.ValidatorUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;

/**
 *
 * @author VakSF
 */
public class MeasurementContainer{
    
    @FXML
    private TableView<Measurement> measurementsTV;
    
    private ObservableList<Measurement> measurements;
    
    @FXML
    private TableView<Product> productsTV;
    
    private ObservableList<Product> products;
    
    @FXML
    private JFXTextField nameTF, abbreviationTF, searchTF;
    
    @FXML
    private JFXButton submitB;
    
    @FXML
    private Label infoL;
    
    private ValidatorUtil validator;
    
    private boolean editing;
    
    public MeasurementContainer() {
        this.editing = false;
    }
    
    protected void initValidators() {
        
        this.validator = new ValidatorUtil(
                nameTF, abbreviationTF
        );
        
    }
    
    protected void initMeasurements(List<Measurement> measurements) {
        
        this.measurements = FXCollections.observableArrayList();
        
        FilteredList<Measurement> filteredData = new FilteredList<>(
                this.measurements, (Measurement measurement) -> true);

        this.searchTF.textProperty().addListener((observable, oldValue, word) -> {
            
            filteredData.setPredicate((Measurement measurement) -> {
                
                this.measurementsTV.getSelectionModel().clearSelection();
                
                this.validator.clearFields();
                
                if (word == null || word.isEmpty()) {
                    return true;
                }
                
                String lowerWord = word.toLowerCase();
                
                if (measurement.getName().toLowerCase().contains(lowerWord)) {
                    
                    return true;
                    
                } else if (measurement.getAbbreviation().toLowerCase().contains(lowerWord)) {
                    
                    return true;
                    
                } else if (measurement.getId().toString().toLowerCase().contains(lowerWord)) {
                    
                    return true;
                    
                }
                
                return false;
                
            });
            
        });

        SortedList<Measurement> sortedList = new SortedList<>(filteredData);
        
        sortedList.comparatorProperty().bind(measurementsTV.comparatorProperty());

        this.measurementsTV.setItems(sortedList);
        
        this.measurements.addAll(measurements);
        
        if (!this.measurements.isEmpty()) {
            this.measurementsTV.getSelectionModel().selectFirst();
            this.updateFields(FXUtil.selectedTableItem(this.measurementsTV));
        }
        
        this.measurementsTV.getSelectionModel().selectedItemProperty()
                .addListener(($obs, $old, $new) -> updateFields($new));
        
    }
    
    protected void initProducts() {
        
        this.products = FXCollections.observableArrayList();
        
        this.productsTV.setItems(this.products);
        
    }
    
    private void updateFields(Measurement measurement) {
        
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
            
            if (measurement != null) {
                
                this.infoL.setText("Visualizando unidad");
                this.infoL.setTextFill(Color.web("#4596d9"));
                
                this.validator.setEditable(false);
                
                List<Product> measurementProducts = measurement.getProducts();
                
                this.nameTF.setText(FXUtil.objectStringValue(measurement.getName()));
                this.abbreviationTF.setText(FXUtil.objectStringValue(measurement.getAbbreviation()));
                
                this.products.setAll(measurementProducts);
                
                this.setEditing(false);
                
            }
            
        } else {
            
            this.validator.clearFields();
            
            this.products.clear();
            
        }
    }
    
    protected Measurement getMeasurement() {
        
        return new Measurement(
                this.nameTF.getText(), 
                this.abbreviationTF.getText()
        );
        
    }
    
    public ObservableList<Measurement> getMeasurements() {
        return measurements;
    }

    public ObservableList<Product> getProducts() {
        return products;
    }
    
    public TableView<Measurement> getMeasurementsTV() {
        return measurementsTV;
    }

    public TableView<Product> getProductsTV() {
        return productsTV;
    }

    public JFXTextField getNameTF() {
        return nameTF;
    }

    public JFXTextField getAbbreviationTF() {
        return abbreviationTF;
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
