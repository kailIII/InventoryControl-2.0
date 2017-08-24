package com.candlelabs.inventory.rmi.interfaces.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Hashtable;

/**
 *
 * @author VakSF
 */
public interface ServerResponder extends Remote {
    
    public void register(MessageResponder receiver) throws RemoteException;
    
    public void unregister(MessageResponder receiver) throws RemoteException;
    
    public void sendMessage(MessageResponder sender, String message)
            throws RemoteException;
    
    public void quit() throws RemoteException;
    
    public Hashtable<String, MessageResponder> getUsers()
            throws RemoteException;
    
    public boolean isOnline(MessageResponder receiver) throws RemoteException;
    
    public boolean isOnline(String receiver) throws RemoteException;
    
    public Collection<MessageResponder> getUser() throws RemoteException;
    
}