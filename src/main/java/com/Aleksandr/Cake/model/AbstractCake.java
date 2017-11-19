package com.Aleksandr.Cake.model;

import com.Aleksandr.Cake.model.enums.ProductCategory;

import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class AbstractCake<T extends AbstractCake<?>> extends AbstractProduct<T> {

    private double weight;

    public AbstractCake() {
    }

    public AbstractCake(String name, String description, BigDecimal price, ProductCategory productCategory, byte[] imageData, double weight) {
        super(name, description, price, productCategory, imageData);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}
