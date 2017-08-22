package com.candlelabs.inventory.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="category")
public class Category  implements Serializable {
    
    private Integer id;
    private String name;
    private List<Product> products = new ArrayList<>();
    
    public Category() {
        
    }

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }
    
    public Category(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="name")
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="category")
    public List<Product> getProducts() {
        return this.products;
    }
    
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
}
