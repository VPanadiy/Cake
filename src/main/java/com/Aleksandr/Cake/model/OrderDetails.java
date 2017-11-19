package com.Aleksandr.Cake.model;

import javax.persistence.*;

@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Orders order;
    @OneToOne
    @JoinColumn(name = "product_id")
    private AbstractProduct<?> abstractProduct;
    private int count;
    boolean payment;

    public OrderDetails() {
    }

    public OrderDetails(Orders order, AbstractProduct<?> abstractProduct, int count, boolean payment) {
        this.order = order;
        this.abstractProduct = abstractProduct;
        this.count = count;
        this.payment = payment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public AbstractProduct<?> getAbstractProduct() {
        return abstractProduct;
    }

    public void setAbstractProduct(AbstractProduct<?> abstractProduct) {
        this.abstractProduct = abstractProduct;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "id=" + id +
                ", order=" + order +
                ", abstractProduct=" + abstractProduct +
                ", count=" + count +
                ", payment=" + payment +
                '}';
    }

}
