package com.Aleksandr.Cake.services;

import com.Aleksandr.Cake.model.order.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

	public void insertOrder(Order order);

	public List<Order> getOrderList();

	public BigDecimal getOrdersTotalAmount();

	public List<Object[]> getOrdersTotalAmountByCustomerName();
}
