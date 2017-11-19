package com.Aleksandr.Cake.repository.productRepository;

import com.Aleksandr.Cake.model.AbstractProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("productBaseRepository")
public interface ProductBaseRepository<T extends AbstractProduct<?>> extends JpaRepository<T, Long> {

}

