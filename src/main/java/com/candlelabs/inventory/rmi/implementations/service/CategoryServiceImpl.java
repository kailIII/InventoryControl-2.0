package com.candlelabs.inventory.rmi.implementations.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.candlelabs.inventory.model.Category;
import com.candlelabs.inventory.persistence.dao.CategoryDao;
import com.candlelabs.inventory.rmi.interfaces.CategoryService;

/**
 *
 * @author VakSF
 */
public class CategoryServiceImpl extends UnicastRemoteObject implements CategoryService {
    
    private final CategoryDao category = new CategoryDao();
    
    public CategoryServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public Long createCategory(Category category) throws RemoteException {
        return this.category.create(category);
    }

    @Override
    public Category readCategory(Long categoryId) throws RemoteException {
        return this.category.read(categoryId);
    }

    @Override
    public boolean updateCategory(Category category) throws RemoteException {
        return this.category.update(category);
    }

    @Override
    public boolean deleteCategory(Category category) throws RemoteException {
        return this.category.delete(category);
    }

    @Override
    public List<Category> listCategories() throws RemoteException {
        return this.category.readAll();
   }
    
}
