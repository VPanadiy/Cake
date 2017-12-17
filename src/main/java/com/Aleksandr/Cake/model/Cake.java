package com.Aleksandr.Cake.model;

import com.Aleksandr.Cake.model.enums.ProductCategory;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "Cake")
public class Cake extends AbstractCake<Cake> {

    public Cake() {
    }

    public Cake(String name, String description, BigDecimal price, ProductCategory productCategory, double rating, byte[] imageData, double weight) {
        super(name, description, price, productCategory, rating, imageData, weight);
    }

}
