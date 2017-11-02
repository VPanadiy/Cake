package com.Aleksandr.Cake.model;

import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class AbstractCake extends AbstractProduct {

    private double weight;

    public AbstractCake() {
    }

    public AbstractCake(String name, String description, BigDecimal price, String category, double weight) {
        super(name, description, price, category);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
