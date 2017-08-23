package com.candlelabs.inventory;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//Servicios a publicar
import com.candlelabs.inventory.rmi.implementations.service.ProductServiceImpl;
import com.candlelabs.inventory.rmi.implementations.service.SupplierServiceImpl;
import com.candlelabs.inventory.rmi.implementations.service.CategoryServiceImpl;
import com.candlelabs.inventory.rmi.implementations.service.MeasurementServiceImpl;

/**
 *
 * @author Arturh
 */
public class RMIServer {

    public RMIServer() throws RemoteException {
        
        Registry registry = LocateRegistry.createRegistry(1099);
        
        registry.rebind("productService", new ProductServiceImpl());
        registry.rebind("supplierService", new SupplierServiceImpl());
        registry.rebind("categoryService", new CategoryServiceImpl());
        registry.rebind("measurementService", new MeasurementServiceImpl());
        
        System.out.println("Server started");
        
    }
    
    public static void main(String[] args) throws RemoteException {
        
        RMIServer server = new RMIServer();
        
    }
    
    
}
