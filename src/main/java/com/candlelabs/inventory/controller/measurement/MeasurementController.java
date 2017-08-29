package com.candlelabs.inventory.controller.measurement;

import com.candlelabs.inventory.RMIClient;
import com.candlelabs.inventory.controller.interfaces.ProductInitializer;
import com.candlelabs.inventory.controller.product.ProductController;
import com.candlelabs.inventory.model.Measurement;
import com.candlelabs.inventory.rmi.implementations.service.CallbackClientImpl;
import com.candlelabs.inventory.rmi.interfaces.service.MeasurementService;
import com.candlelabs.inventory.util.FXUtil;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author VakSF
 */
public class MeasurementController extends MeasurementContainer 
        implements Initializable, ProductInitializer {
    
    // Belongs to
    private ProductController productController;
    
    public MeasurementService measurementService;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.initServices();
        
        try {
            
            this.initValidators();
            
            this.initProducts();
            
            this.initMeasurements();
            
        } catch (RemoteException ex) {
            
        }
        
    }    

    @Override
    public void init(ProductController controller) {
        this.productController = controller;
    }
    
    private void initServices() {
        
        try {
            
            this.measurementService = (MeasurementService) 
                    RMIClient.getRegistry().lookup("measurementService");
            
        } catch (RemoteException | NotBoundException ex) {
            
            System.out.println("Exception: " + ex.toString());
            
        }
        
    }
    
    public void initMeasurements() throws RemoteException {
        this.initMeasurements(this.measurementService.listMeasurements());
    }
    
    @FXML
    private void createEdit() {
        
        if (getValidator().validateFields()) {
            
            String action = getSubmitB().getText().toLowerCase();
            
            if (action.equals("crear")) {
                
                this.createMeasurement();
                
            } else {
                
                if (action.equals("editar")) {
                    
                    this.editMeasurement();
                    
                }
                
            }
            
        } else {
            
            getValidator().emptyFields().show();
            
        }
        
    }
    
    @FXML
    private void newMeasurement() {
        
        getValidator().setEditable(true);
        
        getInfoL().setText("Creando medida");
        getInfoL().setTextFill(Color.web("#d3cf43"));
        
        setEditing(true);
        
        getValidator().clearFields();
        
        getProducts().clear();
        
        getSubmitB().setDisable(false);
        getSubmitB().setText("Crear");
    }
    
    @FXML
    private void edit() {
        
        getInfoL().setText("Editando categoría");
        getInfoL().setTextFill(Color.web("#45d852"));
        
        getValidator().setEditable(true);
        
        getNameTF().requestFocus();
        
        setEditing(true);
        
        getSubmitB().setDisable(false);
        getSubmitB().setText("Editar");
    }
    
    private void createMeasurement() {
            
        Measurement measurement = this.getMeasurement();
        
        try {
            
            Integer measurementId = this.measurementService.createMeasurement(measurement);
            
            if (measurementId != null) {
                
                measurement.setId(measurementId);
                
                this.measurementAction(measurement, "create");
                
                setEditing(false);
                
                getMeasurements().add(measurement);
                getMeasurementsTV().getSelectionModel().select(measurement);
                
                new Alert(
                        Alert.AlertType.INFORMATION,
                        "Medida creada correctamente"
                ).show();
                
                
                getValidator().setEditable(false);
                
                getSubmitB().setDisable(true);
                
            } else {
                
                new Alert(
                        Alert.AlertType.ERROR,
                        "No se ha podido crear la medida. El nombre ya existe"
                ).show();
                
            }
            
        } catch (RemoteException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        
    }
    
    private void editMeasurement() {
        
        Measurement measurement = FXUtil.selectedTableItem(getMeasurementsTV());
        int index = FXUtil.selectedTableIndex(getMeasurementsTV());
        
        if (measurement != null) {
            
            measurement.editMeasurement(this.getMeasurement());
            
            try {
                
                boolean updated = this.measurementService.updateMeasurement(measurement);
                
                if (updated) {
                    
                    this.measurementAction(measurement, "edit", index);
                    
                    setEditing(false);
                    
                    getMeasurementsTV().refresh();
                    getMeasurementsTV().getSelectionModel().selectFirst();
                    
                    new Alert(
                            Alert.AlertType.INFORMATION,
                            "Medida editada correctamente"
                    ).show();
                    
                    
                    getValidator().setEditable(false);
                    
                    getSubmitB().setText("Crear");
                    getSubmitB().setDisable(true);
                    
                } else {
                    
                    new Alert(
                            Alert.AlertType.ERROR,
                            "La medida no pudo ser editada. El nombre ya existe"
                    ).show();
                    
                }
                
            } catch (RemoteException ex) {
                System.out.println("Exception: " + ex.toString());
            }
            
        }
        
    }
    
    @FXML
    private void deleteMeasurement() {
        
        Measurement measurement = FXUtil.selectedTableItem(getMeasurementsTV());
        
        if (measurement != null) {
            
            Optional<ButtonType> result = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Está seguro de eliminar la medida '" + measurement.getName() + "'?"
            ).showAndWait();
            
            if (result.get() == ButtonType.OK) {
                
                try {
                    
                    boolean deleted = this.measurementService.deleteMeasurement(measurement);
                    
                    if (deleted) {
                        
                        this.measurementAction(measurement, "delete");
                        
                        getMeasurements().remove(measurement);
                        
                        new Alert(
                                Alert.AlertType.INFORMATION,
                                "Medida eliminada correctamente"
                        ).show();
                        
                    } else {
                        
                        new Alert(
                                Alert.AlertType.ERROR,
                                "No se ha podido eliminar la medida"
                        ).show();
                        
                    }
                    
                } catch (RemoteException ex) {
                    System.out.println("Exception: " + ex.toString());
                }
                
            }
            
        }
        
    }
    
    @FXML
    private void submit(KeyEvent event) throws IOException{
        
        if (event.getCode().equals(KeyCode.ENTER)) 
            getSubmitB().fire();
        
    }
    
    private void measurementAction(Measurement measurement, String action, int index) throws RemoteException {
        
        if (this.productController != null) {
            
            CallbackClientImpl client = this.getClient();
            
            client.getServer().measurementAction(client, measurement, action, index);
            
            if (action.equals("create")) {
                
                this.productController.getMeasurements().add(measurement);
                
            } else {
                
                if (action.equals("edit")) {
                    
                    this.productController.initProducts();
                    
                } else {
                    
                    if (action.equals("delete")) {
                        
                        this.productController.getMeasurements().remove(measurement);
                        
                    }
                    
                }
                
            }
            
        }
        
    }
    
    private void measurementAction(Measurement measurement, String action) throws RemoteException {
        this.measurementAction(measurement, action, 0);
    }
    
    private CallbackClientImpl getClient() {
        
        return this.productController
                .getMastermindController().getClient();
    }
    
    public ProductController getProductController() {
        return productController;
    }

    public MeasurementService getMeasurementService() {
        return measurementService;
    }
    
}
