package com.candlelabs.inventory.rmi.implementations.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.candlelabs.inventory.model.Category;
import com.candlelabs.inventory.persistence.dao.CategoryDao;
import com.candlelabs.inventory.rmi.interfaces.service.CategoryService;

/**
 *
 * @author VakSF
 */
public class CategoryServiceImpl extends UnicastRemoteObject implements CategoryService {
    
    private final CategoryDao categoryDao = new CategoryDao();
    
    public CategoryServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public Integer createCategory(Category category) throws RemoteException {
        return this.categoryDao.create(category);
    }

    @Override
    public Category readCategory(Integer categoryId) throws RemoteException {
        return this.categoryDao.read(categoryId);
    }

    @Override
    public boolean updateCategory(Category category) throws RemoteException {
        return this.categoryDao.update(category);
    }

    @Override
    public boolean deleteCategory(Category category) throws RemoteException {
        return this.categoryDao.delete(category);
    }

    @Override
    public List<Category> listCategories() throws RemoteException {
        return this.categoryDao.readAll();
   }
    
}
