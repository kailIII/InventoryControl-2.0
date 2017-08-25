package com.candlelabs.inventory.rmi.implementations.service;

import com.candlelabs.inventory.model.Category;
import com.candlelabs.inventory.model.Measurement;
import com.candlelabs.inventory.model.Product;
import com.candlelabs.inventory.model.Supplier;

import com.candlelabs.inventory.persistence.dao.ProductDao;
import com.candlelabs.inventory.persistence.dao.CategoryDao;
import com.candlelabs.inventory.persistence.dao.SupplierDao;
import com.candlelabs.inventory.persistence.dao.MeasurementDao;

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
    private final CategoryDao categoryDao = new CategoryDao();
    private final SupplierDao supplierDao = new SupplierDao();
    private final MeasurementDao measurementDao = new MeasurementDao();
    
    public ProductServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public Integer createProduct(Product product) throws RemoteException {
        return this.productDao.create(product);
    }

    @Override
    public Product readProduct(Integer productId) throws RemoteException {
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

    @Override
    public List<Category> listCategories() throws RemoteException {
        return this.categoryDao.readAll();
    }

    @Override
    public List<Supplier> listSuppliers() throws RemoteException {
        return this.supplierDao.readAll();
    }

    @Override
    public List<Measurement> listMeasurements() throws RemoteException {
        return this.measurementDao.readAll();
    }
    
}
