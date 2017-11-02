package com.Aleksandr.Cake.model;

import com.Aleksandr.Cake.model.enums.ProductType;

import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class AbstractCandies extends AbstractProduct {

    private long count;

    public AbstractCandies() {
    }

    public AbstractCandies(String name, String description, BigDecimal price, String category, long count) {
        super(name, description, price, category);
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
