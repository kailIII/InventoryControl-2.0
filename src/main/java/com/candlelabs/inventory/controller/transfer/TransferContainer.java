package com.candlelabs.inventory.controller.transfer;

import com.candlelabs.inventory.bean.SortBy;
import com.candlelabs.inventory.controller.interfaces.TransferInitializer;
import com.candlelabs.inventory.controller.transfer.detail.TransferDetailController;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.fxml.FXML;

import com.jfoenix.controls.JFXTextField;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import com.candlelabs.inventory.model.Transfer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 *
 * @author VakSF
 */
public class TransferContainer {
    
    @FXML
    private TableView<Transfer> transfersTV, transfersSessionTV;
    
    private ObservableList<Transfer> transfers, transfersSession;
    
    @FXML
    private TableColumn<Transfer, String> initialTC, endTC, 
            initialSessionTC, endSessionTC, detailsTC, detailsSessionTC;
    
    @FXML
    private JFXTextField searchTF, searchSessionTF;
    
    @FXML
    private JFXComboBox<SortBy> searchByCB, searchBySessionCB;
    
    private TransferDetailController transferDetailController;
    
    public TransferContainer() {
        
    }
    
    public void initTransfers(List<Transfer> transfers) {
        
        this.transfers = FXCollections.observableArrayList();
        this.transfersSession = FXCollections.observableArrayList();
        
        FilteredList<Transfer> filteredData = new FilteredList<>(
                this.transfers, (Transfer transfer) -> true);
        
        this.searchTF.textProperty().addListener(
                (observable, oldValue, word) -> {
            
            filteredData.setPredicate((Transfer transfer) -> {
                
                if (word == null || word.isEmpty()) {
                    return true;
                }
                
                String lowerWord = word.toLowerCase();
                String name = this.searchByCB.getValue().getName();
                
                switch (name) {
                    case "code":
                        return transfer.getCode().toLowerCase().contains(lowerWord);
                    case "source":
                        return transfer.getFromStore().getName().toLowerCase().contains(lowerWord);
                    case "destination":
                        return transfer.getToStore().getName().toLowerCase().contains(lowerWord);
                    case "status":
                        return transfer.getStatus().getDescription().toLowerCase().contains(lowerWord);
                    case "quantity":
                        return transfer.getQuantity().toString().toLowerCase().contains(lowerWord);
                    default:
                        break;
                }
                
                return false;
                
            });
            
        });

        SortedList<Transfer> sortedList = new SortedList<>(filteredData);
        
        sortedList.comparatorProperty().bind(transfersTV.comparatorProperty());

        this.transfersTV.setItems(sortedList);
        
        this.transfers.setAll(transfers);
        
        if (!this.transfers.isEmpty()) {
            this.transfersTV.getSelectionModel().selectFirst();
        }
        
    }
    
    public void initColumns(TransferController transferCtrl) {
        
        this.formatColumn(this.initialTC, "initial");
        this.formatColumn(this.endTC, "end");
        this.formatColumn(this.initialSessionTC, "end");
        this.formatColumn(this.endSessionTC, "end");
        
        this.detailsTC.setCellFactory(new PropertyValueFactory("details"));
        this.detailsSessionTC.setCellFactory(new PropertyValueFactory("details"));
        
        Callback<TableColumn<Transfer, String>, TableCell<Transfer, String>> cellFactory =
                (TableColumn<Transfer, String> value) -> {
                    
                    TableCell<Transfer, String> cell = new TableCell<Transfer, String>() {
                        
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            
                            JFXButton button = new JFXButton("Ver detalles");
                            
                            button.setTextFill(Paint.valueOf("#ffffff"));

                            button.setStyle("-fx-background-color: #257ec2");
                            
                            super.updateItem(item, empty);
                            
                            if (empty) {
                                
                                super.setGraphic(null);
                                super.setText(null);
                                
                            } else {
                                
                                button.setOnAction((ActionEvent event) -> {
                                    
                                    Transfer transfer = getTableView().getItems().get(
                                            super.getIndex()
                                    );
                                    
                                    transferDetailController = initView(
                                            "/view/transfer/detail/TransferDetail.fxml",
                                            TransferDetailController.class,
                                            "Detalles de transferencia - Control de inventario",
                                            transferCtrl
                                    );
                                    
                                    transferDetailController.setTransfer(transfer);
                                    
                                });
                                
                                super.setGraphic(button);
                                super.setAlignment(Pos.CENTER);
                                super.setText(null);
                                
                            }
                            
                        }
                        
                    };
                    
                    return cell;
                    
                };
        
        this.detailsTC.setCellFactory(cellFactory);
        this.detailsSessionTC.setCellFactory(cellFactory);
        
    }
    
    private void formatColumn(TableColumn<Transfer, String> column, String type) {
        
        column.setCellValueFactory(
                
                transfer -> {
                    
                    SimpleDateFormat format =
                            new SimpleDateFormat("dd/MM/yyyy");
                    
                    if (type.equals("initial")) {
                        
                        if (transfer.getValue().getInitialDate() != null) 
                            return new SimpleStringProperty(
                                format.format(transfer.getValue().getInitialDate()));
                        else
                            return new SimpleStringProperty("Sin establecer");
                            
                    } else if (type.equals("end")) {
                        
                        if (transfer.getValue().getEndDate() != null) 
                            return new SimpleStringProperty(
                                    format.format(transfer.getValue().getEndDate()));
                        else
                            return new SimpleStringProperty("Sin establecer");
                        
                    }
                    
                    return null;
                    
                });
        
        
    }
    
    public void initCBs() {
        
        List<SortBy> sorts = new ArrayList<>(Arrays.asList(
                new SortBy("code", "Código"),
                new SortBy("source", "Origen"),
                new SortBy("destination", "Destino"),
                new SortBy("status", "Estado"),
                new SortBy("quantity", "Cantidad")
        ));
        
        this.searchByCB.getItems().addAll(sorts);
        this.searchBySessionCB.getItems().addAll(sorts);
        
        if (!sorts.isEmpty()) {
            
            this.searchByCB.getSelectionModel().selectFirst();
            this.searchBySessionCB.getSelectionModel().selectFirst();
            
        }
        
        this.searchByCB.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    
            String text = this.searchTF.getText();
            
            this.searchTF.clear();
            this.searchTF.appendText(text);
            
        });
        
    }
    
    private <T extends TransferInitializer> T initView(
            String fxml, Class<T> clazz, String title, TransferController transferCtrl) {
        
        T controller = null;
        
        try {
            
            FXMLLoader loader = new FXMLLoader(clazz.getResource(fxml));
        
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene((Pane) loader.load()));
            
            controller = loader.<T>getController();
            
            controller.init(transferCtrl);
            
            stage.setTitle(title);
            stage.show();
            
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        
        return controller;
        
    }
    
    public TableView<Transfer> getTransfersTV() {
        return transfersTV;
    }

    public ObservableList<Transfer> getTransfers() {
        return transfers;
    }

    public TransferDetailController getTransferDetailController() {
        return transferDetailController;
    }

}
