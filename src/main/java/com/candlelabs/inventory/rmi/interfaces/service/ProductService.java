package com.candlelabs.inventory.rmi.interfaces.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.candlelabs.inventory.model.Category;
import com.candlelabs.inventory.model.Measurement;
import com.candlelabs.inventory.model.Product;
import com.candlelabs.inventory.model.Supplier;

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
    
    public List<Category> listCategories() throws RemoteException;
    public List<Supplier> listSuppliers() throws RemoteException;
    public List<Measurement> listMeasurements() throws RemoteException;
    
}
