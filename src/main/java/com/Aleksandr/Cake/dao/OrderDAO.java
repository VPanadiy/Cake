package com.Aleksandr.Cake.dao;

import com.Aleksandr.Cake.model.order.Order;

import java.math.BigDecimal;
import java.util.List;


public interface OrderDAO {

	public void insertOrder(Order order);

	public List<Order> getOrderList();

	public BigDecimal getOrdersTotalAmount();

	public List<Object[]> getOrdersTotalAmountByCustomerName();
}
