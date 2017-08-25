package com.candlelabs.inventory.rmi.interfaces.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.candlelabs.inventory.model.Store;

/**
 *
 * @author Arturh
 */
public interface StoreService extends Remote {
    
    public Store readStore(Integer storeId) throws RemoteException;
    public Integer createStore(Store store) throws RemoteException;
    public boolean updateStore(Store store) throws RemoteException;
    public boolean deleteStore(Store store) throws RemoteException;
    public List<Store> listStores() throws RemoteException;
    
}
