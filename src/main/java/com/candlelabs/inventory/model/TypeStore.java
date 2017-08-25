package com.candlelabs.inventory.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author VakSF
 */
@Entity
@Table (name = "type_store")
public class TypeStore implements Serializable {
    
    private Integer id;
    private String description;
    
    public TypeStore() {
        
    }

    public TypeStore(String description) {
        this.description = description;
    }

    public TypeStore(Integer id, String description) {
        this.id = id;
        this.description = description;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column (name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
