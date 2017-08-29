package com.candlelabs.inventory.rmi.interfaces.service;

import java.io.Serializable;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.candlelabs.inventory.model.Product;
import com.candlelabs.inventory.model.Category;
import com.candlelabs.inventory.model.Measurement;
import com.candlelabs.inventory.model.Supplier;

/**
 *
 * @author VakSF
 */
public interface MessageResponder extends Remote, Serializable {
    
    
    public void productAction(Product product, String action, int index) throws RemoteException;
    
    public void categoryAction(Category category, String action, int index) throws RemoteException;
    
    public void measurementAction(Measurement measurement, String action, int index) throws RemoteException;
    
    public void supplierAction(Supplier supplier, String action, int index) throws RemoteException;
    
    
    public void sendMessageToClient(String message)throws RemoteException;
    
    public String getName()throws RemoteException;
    
    public void setName(String name)throws RemoteException;
    
    public void exit()throws RemoteException;
    
}
