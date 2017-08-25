package com.candlelabs.inventory.controller.store.selection;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import com.candlelabs.inventory.model.Store;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;

/**
 *
 * @author VakSF
 */
public class StoreSelectionContainer {
    
    @FXML
    private TableView<Store> storesTV;
    
    private ObservableList<Store> stores;
    
    public StoreSelectionContainer() {
        
    }
    
    protected void initTV(List<Store> stores) {
        
        this.stores = FXCollections.observableArrayList();
        
        this.storesTV.setItems(this.stores);
        
        this.stores.addAll(stores);
        
        if (this.stores.isEmpty())
            this.storesTV.getSelectionModel().selectFirst();
        
    }
    
    protected void close(ActionEvent event) {
        ((Node) this.getStoresTV()).getScene().getWindow().hide();
    }

    public TableView<Store> getStoresTV() {
        return storesTV;
    }

    public ObservableList<Store> getStores() {
        return stores;
    }
    
}
