package com.candlelabs.inventory.controller.mastermind;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author VakSF
 */
public class MastermindContainer {
    
    @FXML
    private AnchorPane productPane;
    
    @FXML
    private AnchorPane transferPane;
    
    public MastermindContainer() {
        
    }

    public AnchorPane getProductPane() {
        return productPane;
    }

    public AnchorPane getTransferPane() {
        return transferPane;
    }
    
    
    
}
