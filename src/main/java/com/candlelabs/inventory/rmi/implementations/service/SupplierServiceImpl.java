package com.candlelabs.inventory.rmi.implementations.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.candlelabs.inventory.model.Supplier;
import com.candlelabs.inventory.persistence.dao.SupplierDao;
import com.candlelabs.inventory.rmi.interfaces.service.SupplierService;

/**
 *
 * @author VakSF
 */
public class SupplierServiceImpl extends UnicastRemoteObject implements SupplierService {
    
    private final SupplierDao supplier = new SupplierDao();
    
    public SupplierServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public Integer createSupplier(Supplier supplier) throws RemoteException {
        return this.supplier.create(supplier);
    }

    @Override
    public Supplier readSupplier(Integer supplierId) throws RemoteException {
        return this.supplier.read(supplierId);
    }

    @Override
    public boolean updateSupplier(Supplier supplier) throws RemoteException {
        return this.supplier.update(supplier);
    }

    @Override
    public boolean deleteSupplier(Supplier supplier) throws RemoteException {
        return this.supplier.delete(supplier);
    }

    @Override
    public List<Supplier> listSuppliers() throws RemoteException {
        return this.supplier.readAll();
   }
    
}
