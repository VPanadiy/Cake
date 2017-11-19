package com.Aleksandr.Cake.repository.productRepository;

import com.Aleksandr.Cake.model.Cake;
import org.springframework.stereotype.Repository;

@Repository("cakeRepository")
public interface CakeRepository extends CakeBaseRepository<Cake> {

}
