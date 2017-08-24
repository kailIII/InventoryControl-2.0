package com.candlelabs.inventory.rmi.interfaces.service;


import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author VakSF
 */
public interface MessageResponder extends Remote, Serializable{
    public void sendMessageToClient(String message)throws RemoteException;
    public String getName()throws RemoteException;
    public void renameIfExist(String name)throws RemoteException;
    public void exit()throws RemoteException;
}
