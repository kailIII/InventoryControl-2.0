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
import javax.persistence.UniqueConstraint;

@Entity
@Table( name="measurement",
        uniqueConstraints = {
            @UniqueConstraint(columnNames="abbreviation"), 
            @UniqueConstraint(columnNames="name")
        } 
)
public class Measurement implements Serializable {
    
    private Integer id;
    private String name;
    private String abbreviation;
    private List<Product> products = new ArrayList<>();
    
    public Measurement() {
    }

    public Measurement(String name, String abbreviation) {
       this.name = name;
       this.abbreviation = abbreviation;
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
    
    @Column(name="name", unique=true, nullable=false)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="abbreviation", unique=true, nullable=false)
    public String getAbbreviation() {
        return this.abbreviation;
    }
    
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="measurement")
    public List<Product> getProducts() {
        return this.products;
    }
    
    public void setProducts(List<Product> products) {
        this.products = products;
    }

}