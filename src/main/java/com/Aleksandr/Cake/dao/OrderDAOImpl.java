package com.Aleksandr.Cake.dao;

import java.math.BigDecimal;
import java.util.List;

import com.Aleksandr.Cake.model.order.Order;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAOImpl implements OrderDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void insertOrder(Order order) {
		sessionFactory.getCurrentSession().save(order);
	}

	@Override
	public List<Order> getOrderList() {
		return sessionFactory.getCurrentSession().createQuery("SELECT o FROM CustomerOrder o").list();
	}

	@Override
	public BigDecimal getOrdersTotalAmount() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("SELECT sum(o.amount) FROM CustomerOrder o");
		return (BigDecimal) query.uniqueResult();
	}

	public List<Object[]> getOrdersTotalAmountByCustomerName() {
		return sessionFactory.getCurrentSession().createQuery("SELECT o.customer.customerName , sum(o.amount) from CustomerOrder o group by o.customer.customerName").list();
	}
}
