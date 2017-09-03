package com.candlelabs.inventory.model;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="transfer", 
        uniqueConstraints = {
            @UniqueConstraint(columnNames="code") 
        }
)
public class Transfer implements Serializable {
    
    private Integer id;
    private Product product;
    private Store fromStore;
    private Store toStore;
    private String code;
    private Long quantity;
    private String description;
    private Date initialDate;
    private Date endDate;
    private Status status;
    
    public Transfer() {
        
    }
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Transfer(Product product, Store fromStore, Store toStore, String code, 
            Date initialDate, Date endDate) {
        
        this.product = product;
        this.fromStore = fromStore;
        this.toStore = toStore;
        this.code = code;
        this.initialDate = initialDate;
        this.endDate = endDate;
    }
    public Transfer(Product product, Store fromStore, Store toStore, String code, 
            Long quantity, String description, Date initialDate, Date endDate, Status status) {
        
        this.product = product;
        this.fromStore = fromStore;
        this.toStore = toStore;
        this.code = code;
        this.quantity = quantity;
        this.description = description;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.status = status;
    }
    
    @ManyToOne
    @JoinColumn(name="product_id", nullable=false, insertable=false, updatable=false)
    public Product getProduct() {
        return this.product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    @ManyToOne
    @JoinColumn(name = "from_store")
    public Store getFromStore() {
        return this.fromStore;
    }
    
    public void setFromStore(Store fromStore) {
        this.fromStore = fromStore;
    }
    
    @ManyToOne
    @JoinColumn(name = "to_store")
    public Store getToStore() {
        return this.toStore;
    }
    
    public void setToStore(Store toStore) {
        this.toStore = toStore;
    }
    
    @Column(name="code", unique=true, nullable=false)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Column(name="quantity")
    public Long getQuantity() {
        return this.quantity;
    }
    
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    
    @Column(name="description")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="initial_date")
    public Date getInitialDate() {
        return this.initialDate;
    }
    
    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_date")
    public Date getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    @ManyToOne
    @JoinColumn(name = "status_id")
    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return this.code;
    }

}