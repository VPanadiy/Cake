package com.Aleksandr.Cake.services;

import com.Aleksandr.Cake.model.AbstractProduct;
import com.Aleksandr.Cake.repository.productRepository.ProductBaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    
    @Autowired
    @Qualifier("productBaseRepository")
    private ProductBaseRepository<AbstractProduct<?>> productBaseRepository;

    @Override
    public Iterable<AbstractProduct<?>> listAllProducts() {
        LOGGER.info("Method listAllProducts of productService executed -- my logger");
        return productBaseRepository.findAll();
    }

    @Override
    public AbstractProduct<?> getProductById(Long id) {
        LOGGER.info("Method getProductById of productService executed -- my logger");
        return productBaseRepository.findOne(id);
    }

    @Override
    public AbstractProduct<?> saveProduct(AbstractProduct<?> product) {
        LOGGER.info("Method saveProduct of productService executed -- my logger");
        return productBaseRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        LOGGER.info("Method deleteProduct of productService executed -- my logger");
        productBaseRepository.delete(id);
    }

    @Override
    public Page<AbstractProduct<?>> findAll(Pageable pageable) {
        LOGGER.info("Method findAll of productService executed -- my logger");
        return productBaseRepository.findAll(pageable);
    }

}
