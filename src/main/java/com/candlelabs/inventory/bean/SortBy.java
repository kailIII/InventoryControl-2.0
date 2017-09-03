package com.candlelabs.inventory.bean;

import java.util.Objects;

/**
 *
 * @author VakSF
 */
public class SortBy {
    
    private String name;
    private String description;

    public SortBy() {
        
    }
    
    public SortBy(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.name);
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
        
        final SortBy other = (SortBy) obj;
        
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return this.description;
    }
    
}
