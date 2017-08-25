package com.candlelabs.inventory.rmi.interfaces.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.candlelabs.inventory.model.User;

/**
 *
 * @author VakSF
 */
public interface LoginService extends Remote {
    
    public User login(String user, String password) throws RemoteException;
    
}
