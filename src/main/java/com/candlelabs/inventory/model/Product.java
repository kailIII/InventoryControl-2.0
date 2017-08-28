package com.candlelabs.inventory.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product implements Serializable {

    private Integer id;
    private String code;
    private String description;
    private String name;
    private double unitPrice;
    private String brand;
    private Integer reorderLevel;
    private Integer unitsInStock;
    
    private Category category;
    private Supplier supplier;
    private Measurement measurement;
    
    public Product() {
        
    }
    
    public Product(String code, String name, double unitPrice) {
        this.code = code;
        this.name = name;
        this.unitPrice = unitPrice;
    }
    
    public Product(
            String code, String description, 
            String name, double unitPrice, String brand,
            Integer reorderLevel, Integer unitsInStock, 
            Category category, Supplier supplier, Measurement measurement) {
        
        this.code = code;
        this.description = description;
        this.name = name;
        this.unitPrice = unitPrice;
        this.brand = brand;
        this.reorderLevel = reorderLevel;
        this.unitsInStock = unitsInStock;
        this.category = category;
        this.supplier = supplier;
        this.measurement = measurement;
    }
    
    public void editProduct(Product product) {
        
        this.code = product.getCode();
        this.description = product.getDescription();
        this.name = product.getName();
        this.unitPrice = product.getUnitPrice();
        this.brand = product.getBrand();
        this.reorderLevel = product.getReorderLevel();
        this.unitsInStock = product.getUnitsInStock();
        this.category = product.getCategory();
        this.supplier = product.getSupplier();
        this.measurement = product.getMeasurement();
        
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
    
    @Column(name="code", nullable=false)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Column(name="description")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="name", nullable=false)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="unitPrice", nullable=false, precision=22, scale=0)
    public double getUnitPrice() {
        return this.unitPrice;
    }
    
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    @Column(name="brand")
    public String getBrand() {
        return this.brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    @Column(name="reorderLevel")
    public Integer getReorderLevel() {
        return this.reorderLevel;
    }
    
    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }
    
    @Column(name="unitsInStock")
    public Integer getUnitsInStock() {
        return this.unitsInStock;
    }
    
    public void setUnitsInStock(Integer unitsInStock) {
        this.unitsInStock = unitsInStock;
    }
    
    @ManyToOne
    @JoinColumn(name="category_id")
    public Category getCategory() {
        return this.category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
    
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    public Supplier getSupplier() {
        return this.supplier;
    }
    
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    
    @ManyToOne
    @JoinColumn(name = "measurement_id")
    public Measurement getMeasurement() {
        return this.measurement;
    }
    
    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", code=" + code + ", description=" + description + ", "
                + "name=" + name + ", unitPrice=" + unitPrice + ", brand=" + brand + ", "
                + "reorderLevel=" + reorderLevel + ", unitsInStock=" + unitsInStock + ", "
                + "category=" + category + ", supplier=" + supplier + ", measurement=" + measurement + '}';
    }
    
}
