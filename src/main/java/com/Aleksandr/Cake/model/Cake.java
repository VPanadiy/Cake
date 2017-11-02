package com.Aleksandr.Cake.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "Cake")
public class Cake extends AbstractCake {

    public Cake() {
    }

    public Cake(String name, String description, BigDecimal price, String category, double weight) {
        super(name, description, price, category, weight);
    }
}
