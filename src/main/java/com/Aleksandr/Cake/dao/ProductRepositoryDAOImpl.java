package com.Aleksandr.Cake.dao;

import com.Aleksandr.Cake.model.Product;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryDAOImpl implements ProductRepositoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Product> getAllProductsAndCondiments() {
		return sessionFactory.getCurrentSession().createQuery("SELECT p FROM Product p ORDER BY p.id").list();
	}

	@Override
	public List<Product> getAllProducts() {
        return sessionFactory.getCurrentSession().createQuery("SELECT p FROM Product p WHERE isCondiment = false ORDER BY p.id").list();
	}

	@Override
	public List<Product> getAllCondiments() {
        return sessionFactory.getCurrentSession().createQuery("SELECT p FROM Product p WHERE isCondiment = true ORDER BY p.id").list();
	}

	@Override
	public Product getProductById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT p FROM Product p WHERE p.id = :id");
        query.setParameter("id", id);
        return (Product) query.uniqueResult();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT p FROM Product p WHERE  p.category =:category ORDER BY p.id");
        query.setParameter("category", category);
        return query.list();
	}

	@Override
	public void addProduct(Product product) {
        sessionFactory.getCurrentSession().save(product);
	}

	@Override
	public void deleteProduct(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE FROM Product p WHERE p.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
	}

	@Override
	public void updateProduct(Product product) {
        sessionFactory.getCurrentSession().saveOrUpdate(product);
	}

}
