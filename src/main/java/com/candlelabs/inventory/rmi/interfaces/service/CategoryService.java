package com.candlelabs.inventory.rmi.interfaces.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.candlelabs.inventory.model.Category;

/**
 *
 * @author Arturh
 */
public interface CategoryService extends Remote {
    
    public Category readCategory(Integer categoryId) throws RemoteException;
    public Integer createCategory(Category category) throws RemoteException;
    public boolean updateCategory(Category category) throws RemoteException;
    public boolean deleteCategory(Category category) throws RemoteException;
    public List<Category> listCategories() throws RemoteException;
    
}
