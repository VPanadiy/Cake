package com.Aleksandr.Cake.repository;

import com.Aleksandr.Cake.model.AbstractProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("cakeRepository")
public interface ProductRepository extends JpaRepository<AbstractProduct, Long> {

}
