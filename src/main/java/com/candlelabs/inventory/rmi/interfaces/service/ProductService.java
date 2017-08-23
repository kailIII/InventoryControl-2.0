package com.candlelabs.inventory.rmi.interfaces.service;

import com.candlelabs.inventory.model.Product;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Arturh
 */
public interface ProductService extends Remote {
    
    public Product readProduct(Long productId) throws RemoteException;
    public Long createProduct(Product product) throws RemoteException;
    public boolean updateProduct(Product product) throws RemoteException;
    public boolean deleteProduct(Product product) throws RemoteException;
    public List<Product> listProducts() throws RemoteException;
    
}
