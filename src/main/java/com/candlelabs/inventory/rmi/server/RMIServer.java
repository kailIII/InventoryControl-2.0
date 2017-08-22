package com.candlelabs.inventory.rmi.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//Servicios a publicar
import com.candlelabs.inventory.rmi.implementations.service.ProductServiceImpl;


/**
 *
 * @author Arturh
 */
public class RMIServer {

    public RMIServer() throws RemoteException {
        
        Registry registry = LocateRegistry.createRegistry(1099);
        
        registry.rebind("productService", new ProductServiceImpl());
        
        System.out.println("Server started");
        
    }
    
    public static void main(String[] args) throws RemoteException {
        
        RMIServer server = new RMIServer();
        
    }
    
    
}
