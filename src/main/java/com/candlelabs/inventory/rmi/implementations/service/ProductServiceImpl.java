package com.candlelabs.inventory.rmi.implementations.service;

import com.candlelabs.inventory.model.Product;
import com.candlelabs.inventory.persistence.dao.ProductDao;
import com.candlelabs.inventory.rmi.interfaces.service.ProductService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author VakSF
 */
public class ProductServiceImpl extends UnicastRemoteObject implements ProductService {
    
    private final ProductDao productDao = new ProductDao();
    
    public ProductServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public Long createProduct(Product product) throws RemoteException {
        return this.productDao.create(product);
    }

    @Override
    public Product readProduct(Long productId) throws RemoteException {
        return this.productDao.read(productId);
    }

    @Override
    public boolean updateProduct(Product product) throws RemoteException {
        return this.productDao.update(product);
    }

    @Override
    public boolean deleteProduct(Product product) throws RemoteException {
        return this.productDao.delete(product);
    }

    @Override
    public List<Product> listProducts() throws RemoteException {
        return this.productDao.readAll();
   }
    
}
