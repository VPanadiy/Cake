package com.Aleksandr.Cake.model;

import com.Aleksandr.Cake.model.enums.ProductCategory;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "Candies")
public class Candies extends AbstractCandies<Candies> {

    public Candies() {
    }

    public Candies(String name, String description, BigDecimal price, ProductCategory productCategory, long count) {
        super(name, description, price, productCategory, count);
    }

}
