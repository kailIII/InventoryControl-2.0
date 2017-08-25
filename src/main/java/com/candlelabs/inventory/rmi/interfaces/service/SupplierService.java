package com.candlelabs.inventory.rmi.interfaces.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.candlelabs.inventory.model.Supplier;

/**
 *
 * @author Arturh
 */
public interface SupplierService extends Remote {
    
    public Supplier readSupplier(Integer supplierId) throws RemoteException;
    public Integer createSupplier(Supplier supplier) throws RemoteException;
    public boolean updateSupplier(Supplier supplier) throws RemoteException;
    public boolean deleteSupplier(Supplier supplier) throws RemoteException;
    public List<Supplier> listSuppliers() throws RemoteException;
    
}
