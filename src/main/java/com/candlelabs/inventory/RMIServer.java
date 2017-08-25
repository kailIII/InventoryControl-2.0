package com.candlelabs.inventory;

import com.candlelabs.inventory.rmi.implementations.service.CallbackServerImpl;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//Servicios a publicar
import com.candlelabs.inventory.rmi.implementations.service.ProductServiceImpl;
import com.candlelabs.inventory.rmi.implementations.service.SupplierServiceImpl;
import com.candlelabs.inventory.rmi.implementations.service.CategoryServiceImpl;
import com.candlelabs.inventory.rmi.implementations.service.LoginServiceImpl;
import com.candlelabs.inventory.rmi.implementations.service.MeasurementServiceImpl;
import com.candlelabs.inventory.rmi.implementations.service.StoreServiceImpl;

/**
 *
 * @author Arturh
 */
public class RMIServer {

    public RMIServer() throws RemoteException {
        
        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        
        registry.rebind("productService", new ProductServiceImpl());
        registry.rebind("supplierService", new SupplierServiceImpl());
        registry.rebind("categoryService", new CategoryServiceImpl());
        registry.rebind("measurementService", new MeasurementServiceImpl());
        registry.rebind("StoreService", new StoreServiceImpl());
        
        registry.rebind("loginService", new LoginServiceImpl());
        
        registry.rebind("serverResponder", new CallbackServerImpl("serverResponder"));
        
        System.out.println("Server started");
    }
    
    public static void main(String[] args) throws RemoteException {
        
        RMIServer server = new RMIServer();
        
    }
    
    
}
