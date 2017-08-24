package com.candlelabs.inventory.rmi.implementations.service;

import com.candlelabs.inventory.persistence.dao.UserDao;
import com.candlelabs.inventory.rmi.interfaces.service.LoginService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author VakSF
 */
public class LoginServiceImpl extends UnicastRemoteObject implements LoginService {
    
    private final UserDao userDao = new UserDao();

    public LoginServiceImpl() throws RemoteException {
        super();
    }
    
    @Override
    public boolean login(String user, String password) throws RemoteException {
        
        Criteria loginCriteria = this.userDao.getSession().createCriteria(
                this.userDao.clazz());
        
        loginCriteria.add(Restrictions.eq("username", user));
        loginCriteria.add(Restrictions.eq("password", password));
        
        return this.userDao.readUniqueByCriteria(loginCriteria) != null;
        
    }
    
}
