package com.candlelabs.inventory.persistence;

import java.io.Serializable;

import com.candlelabs.inventory.util.HibernateUtil;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author VakSF
 * @param <T>
 * @param <PK>
 */
public class GenericDaoHibernateImpl<T, PK extends Serializable> 
        implements GenericDao<T, PK> {
    
    private Class<T> clazz;
    private SessionFactory sessionFactory;

    public GenericDaoHibernateImpl() {
        this.clazz();
    }
    
    @Override
    public Class<T> clazz() {
        
        if (this.clazz == null) {
            
            Class<?> actualClass = this.getClass();
            
            ParameterizedType pt = 
                    (ParameterizedType) actualClass.getGenericSuperclass();
            
            this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
        }
        
        return this.clazz;
    }
    
    @Override
    public PK create(T entity) {
        
        this.sessionFactory = HibernateUtil.getSessionFactory();
        
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();
        
        PK saved;
        
        try {
            
            saved = (PK) session.save(entity);
            transaction.commit();
             
        } catch (HibernateException ex) {
            
            System.out.println("Exception: " + ex.toString());
            
            this.rollback(transaction);
            saved = null;
            
        } finally {
            session.close();
        }
        
        return saved;
    }

    @Override
    public T read(PK id) {
        
        this.sessionFactory = HibernateUtil.getSessionFactory();
        
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();
        
        T read;
        
        try {
            
            read = (T) session.get(clazz, id);
            transaction.commit();
             
        } catch (HibernateException ex) {
            
            System.out.println("Exception: " + ex.toString());
            
            this.rollback(transaction);
            read = null;
            
        } finally {
            session.close();
        }
        
        return read;
    }

    @Override
    public boolean update(T entity) {
        
        this.sessionFactory = HibernateUtil.getSessionFactory();
        
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();
        
        boolean updated = false;
        
        try {
            
            session.update(entity);
            transaction.commit();
            
            updated = true;
             
        } catch (HibernateException ex) {
            
            System.out.println("Exception: " + ex.toString());
            
            this.rollback(transaction);
            updated = false;
            
        } finally {
            session.close();
        }
        
        return updated;
        
    }

    @Override
    public boolean delete(T entity) {
        
        this.sessionFactory = HibernateUtil.getSessionFactory();
        
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        
        boolean deleted = false;
        
        try {
            
            session.delete(entity);
            transaction.commit();
            
            deleted = true;
             
        } catch (HibernateException ex) {
            
            this.rollback(transaction);
            
            deleted = false;
            
        } finally {
            
            session.flush();
            session.close();
            
        }
        
        return deleted;
        
    }

    @Override
    public List<T> readAll() {
        
        this.sessionFactory = HibernateUtil.getSessionFactory();
        
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        List<T> list = new ArrayList<>();
        
        try {
            
            System.out.println(this.clazz.getName());
            
            System.out.println(session == null);
            
            list = session.createCriteria(this.clazz).list();
            
            transaction.commit();
             
        } catch (HibernateException ex) {
            
            System.out.println("Error = " + ex.toString());
            
            if (transaction != null) transaction.rollback();
            
        } finally {
            
            session.close();
            
        }
        
        return list;
        
    }
    
    @Override
    public List<T> readByCriteria(Criteria criteria) {
        return (List<T>) criteria.list();
    }
    
    @Override
    public <T> T readUniqueByCriteria(Criteria criteria) {
        return (T) criteria.uniqueResult();
    }
    
    @Override
    public Session getSession() {
        
        this.sessionFactory = HibernateUtil.getSessionFactory();
        
        return this.sessionFactory.openSession();
    }

    @Override
    public void rollback(Transaction transaction) {
        if (transaction != null) transaction.rollback();
    }

}
