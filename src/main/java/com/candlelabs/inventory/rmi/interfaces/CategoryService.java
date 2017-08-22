package com.candlelabs.inventory.rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.candlelabs.inventory.model.Category;

/**
 *
 * @author Arturh
 */
public interface CategoryService extends Remote {
    
    public Category readCategory(Long categoryId) throws RemoteException;
    public Long createCategory(Category category) throws RemoteException;
    public boolean updateCategory(Category category) throws RemoteException;
    public boolean deleteCategory(Category category) throws RemoteException;
    public List<Category> listCategories() throws RemoteException;
    
}
