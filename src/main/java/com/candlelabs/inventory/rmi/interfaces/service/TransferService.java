package com.candlelabs.inventory.rmi.interfaces.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.candlelabs.inventory.model.Transfer;

/**
 *
 * @author Arturh
 */
public interface TransferService extends Remote {
    
    public Transfer readTransfer(Integer transferId) throws RemoteException;
    public Integer createTransfer(Transfer transfer) throws RemoteException;
    public boolean updateTransfer(Transfer transfer) throws RemoteException;
    public boolean deleteTransfer(Transfer transfer) throws RemoteException;
    public List<Transfer> listTransfers() throws RemoteException;
    
}
