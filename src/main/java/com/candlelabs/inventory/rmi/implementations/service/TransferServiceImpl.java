package com.candlelabs.inventory.rmi.implementations.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.candlelabs.inventory.model.Transfer;
import com.candlelabs.inventory.persistence.dao.TransferDao;
import com.candlelabs.inventory.rmi.interfaces.service.TransferService;

/**
 *
 * @author VakSF
 */
public class TransferServiceImpl extends UnicastRemoteObject implements TransferService {
    
    private final TransferDao transferDao = new TransferDao();
    
    public TransferServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public Integer createTransfer(Transfer Transfer) throws RemoteException {
        return this.transferDao.create(Transfer);
    }

    @Override
    public Transfer readTransfer(Integer TransferId) throws RemoteException {
        return this.transferDao.read(TransferId);
    }

    @Override
    public boolean updateTransfer(Transfer Transfer) throws RemoteException {
        return this.transferDao.update(Transfer);
    }

    @Override
    public boolean deleteTransfer(Transfer Transfer) throws RemoteException {
        return this.transferDao.delete(Transfer);
    }

    @Override
    public List<Transfer> listTransfers() throws RemoteException {
        return this.transferDao.readAll();
   }
    
}
