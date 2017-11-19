package com.Aleksandr.Cake.repository.productRepository;

import com.Aleksandr.Cake.model.AbstractCake;
import org.springframework.stereotype.Repository;

@Repository("cakeBaseRepository")
public interface CakeBaseRepository<T extends AbstractCake<?>> extends ProductBaseRepository<AbstractCake<?>> {

}
