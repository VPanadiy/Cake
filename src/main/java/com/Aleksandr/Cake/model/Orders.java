package com.Aleksandr.Cake.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private Date orderDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userId;
    @OneToMany(mappedBy = "order")
    private List<OrderDetails> products;
    private BigDecimal price;
    private boolean isOpen;

    public Orders() {
    }

    public Orders(Date orderDate, User userId, List<OrderDetails> products, BigDecimal price, boolean isOpen) {
        this.orderDate = orderDate;
        this.userId = userId;
        this.products = products;
        this.price = price;
        this.isOpen = isOpen;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public List<OrderDetails> getProducts() {
        return products;
    }

    public void setProducts(List<OrderDetails> products) {
        this.products = products;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", userId=" + userId +
                ", products=" + products +
                ", price=" + price +
                ", isOpen=" + isOpen +
                '}';
    }

}