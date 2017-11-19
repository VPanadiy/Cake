package com.Aleksandr.Cake.services;

import com.Aleksandr.Cake.model.AbstractProduct;
import com.Aleksandr.Cake.repository.productRepository.ProductBaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    
    @Autowired
    @Qualifier("productBaseRepository")
    private ProductBaseRepository<AbstractProduct<?>> productBaseRepository;

}
