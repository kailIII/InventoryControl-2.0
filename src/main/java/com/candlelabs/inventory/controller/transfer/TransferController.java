package com.candlelabs.inventory.controller.transfer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.candlelabs.inventory.RMIClient;
import com.candlelabs.inventory.controller.interfaces.MastermindInitializer;
import com.candlelabs.inventory.controller.mastermind.MastermindController;
import com.candlelabs.inventory.rmi.interfaces.service.TransferService;

/**
 *
 * @author VakSF
 */
public class TransferController extends TransferContainer 
        implements Initializable, MastermindInitializer {
    
    // Belongs to
    private MastermindController mastermindController;
    
    private TransferService transferService;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            
            this.initServices();
            
            this.initTransfers(this.transferService.listTransfers());
            
            this.initColumns(this);
            
            this.initCBs();
            
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
            
            this.transferService = (TransferService) RMIClient.getRegistry().lookup("transferService");
            
        } catch (RemoteException | NotBoundException ex) {
            
            System.out.println("Exception: " + ex.toString());
            
        }
        
    }
    
}
