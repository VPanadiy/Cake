package com.Aleksandr.Cake.model;

import com.Aleksandr.Cake.model.enums.ProductType;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "Candies")
public class Candies extends AbstractCandies {

    public Candies() {
    }

    public Candies(String name, String description, BigDecimal price, String category, long count) {
        super(name, description, price, category, count);
    }
}
