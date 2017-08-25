package com.candlelabs.inventory.rmi.implementations.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.candlelabs.inventory.model.Store;
import com.candlelabs.inventory.persistence.dao.StoreDao;
import com.candlelabs.inventory.rmi.interfaces.service.StoreService;

/**
 *
 * @author VakSF
 */
public class StoreServiceImpl extends UnicastRemoteObject implements StoreService {
    
    private final StoreDao storeDao = new StoreDao();
    
    public StoreServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public Integer createStore(Store store) throws RemoteException {
        return this.storeDao.create(store);
    }

    @Override
    public Store readStore(Integer storeId) throws RemoteException {
        return this.storeDao.read(storeId);
    }

    @Override
    public boolean updateStore(Store store) throws RemoteException {
        return this.storeDao.update(store);
    }

    @Override
    public boolean deleteStore(Store store) throws RemoteException {
        return this.storeDao.delete(store);
    }

    @Override
    public List<Store> listStores() throws RemoteException {
        return this.storeDao.readAll();
   }
    
}
