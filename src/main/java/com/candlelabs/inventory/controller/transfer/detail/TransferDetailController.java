package com.candlelabs.inventory.controller.transfer.detail;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.candlelabs.inventory.controller.interfaces.TransferInitializer;
import com.candlelabs.inventory.controller.transfer.TransferController;
import com.candlelabs.inventory.model.Transfer;

/**
 *
 * @author VakSF
 */
public class TransferDetailController extends TranferDetailContainer
        implements Initializable, TransferInitializer {
    
    // Belongs to
    private TransferController transferController;
    
    private Transfer transfer;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
    }

    @Override
    public void init(TransferController controller) {
        this.transferController = controller;
    }
    
    public void setTransfer(Transfer transfer) {
        
        this.transfer = transfer;
        
    }
    
}
