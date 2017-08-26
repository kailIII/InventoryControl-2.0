package com.candlelabs.inventory.rmi.interfaces.service;


import com.candlelabs.inventory.model.Category;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author VakSF
 */
public interface MessageResponder extends Remote, Serializable {
    
    
    public void categoryAction(Category category, String action, int index) throws RemoteException;
    
    
    public void sendMessageToClient(String message)throws RemoteException;
    
    public String getName()throws RemoteException;
    
    public void setName(String name)throws RemoteException;
    
    public void exit()throws RemoteException;
    
}
