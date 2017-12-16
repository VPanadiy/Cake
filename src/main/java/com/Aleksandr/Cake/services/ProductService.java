package com.Aleksandr.Cake.services;

import com.Aleksandr.Cake.model.AbstractProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Iterable<AbstractProduct<?>> listAllProducts();

    AbstractProduct<?> getProductById(Long id);

    AbstractProduct<?> saveProduct(AbstractProduct<?> product);

    void deleteProduct(Long id);

    Page<AbstractProduct<?>> findAll(Pageable pageable);

}
