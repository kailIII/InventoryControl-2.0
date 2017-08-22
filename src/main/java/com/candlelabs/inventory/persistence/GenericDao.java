package com.candlelabs.inventory.persistence;

import java.io.Serializable;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author VakSF
 * @param <T>
 * @param <PK>
 */
public interface GenericDao <T, PK extends Serializable> {
    
    public Class<T> clazz();
    
    /** Persist the newInstance object into database
     * @param newInstance
     * @return @PK */
    PK create(T newInstance);
    
    /** Retrieve an object that was previously persisted to the database using
     *  the indicated id as primary key
     * @param id
     */
    T read(PK id);
    
    /** Retrieve an object list that was previously persisted to the database using
     * @return list
     */
    public List<T> readAll();
    
    /** Save changes made to a persistent object.
     * @param transientObject
     * @return  */
    boolean update(T transientObject);
    
    /** Remove an object from persistent storage in the database
     * @param persistentObject
     * @return  */
    boolean delete(T persistentObject);
    
    /**
     * @param criteria
     * @return 
     */
    public List<T> readByCriteria(Criteria criteria);
    
    /**
     * @param <T>
     * @param criteria
     * @return 
     */
    public <T> T readUniqueByCriteria(Criteria criteria);
    
    /** Retrieve a session
     * @return hibernate session
     */
    public Session getSession();
    
    public void rollback(Transaction transaction);
}