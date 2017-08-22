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
@Table(name="supplier")
public class Supplier implements Serializable {
    
    private Integer id;
    private String companyName;
    private String contactName;
    private String contactTitle;
    private String address;
    private String city;
    private String country;
    private String phone;
    private List<Product> products = new ArrayList<>();
    
    public Supplier() {
        
    }
    
    public Supplier(String companyName, String contactName, String contactTitle, String address, String city, String country, String phone) {
        this.companyName = companyName;
        this.contactName = contactName;
        this.contactTitle = contactTitle;
        this.address = address;
        this.city = city;
        this.country = country;
        this.phone = phone;
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
    
    @Column(name="companyName")
    public String getCompanyName() {
        return this.companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    @Column(name="contactName")
    public String getContactName() {
        return this.contactName;
    }
    
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    
    @Column(name="contactTitle")
    public String getContactTitle() {
        return this.contactTitle;
    }
    
    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }
    
    @Column(name="address")
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Column(name="city")
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    @Column(name="country")
    public String getCountry() {
        return this.country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    @Column(name="phone")
    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="supplier")
    public List<Product> getProducts() {
        return this.products;
    }
    
    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
