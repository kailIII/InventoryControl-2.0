package com.candlelabs.inventory.rmi.interfaces.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author VakSF
 */
public interface LoginService extends Remote {
    
    public boolean login(String user, String password) throws RemoteException;
    
}
