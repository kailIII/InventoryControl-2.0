package com.candlelabs.inventory.controller.interfaces;

import com.candlelabs.inventory.controller.store.selection.StoreSelectionController;
import com.candlelabs.inventory.rmi.implementations.service.CallbackClientImpl;

/**
 *
 * @author VakSF
 */
public interface StoreSelectionInitializer {
    
    public void init(
            StoreSelectionController controller,
            CallbackClientImpl client
    );
    
    public boolean unregister();
    
}
