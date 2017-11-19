package com.Aleksandr.Cake.repository.productRepository;

import com.Aleksandr.Cake.model.AbstractCandies;
import org.springframework.stereotype.Repository;

@Repository("candiesBaseRepository")
public interface CandiesBaseRepository<T extends AbstractCandies<?>> extends ProductBaseRepository<AbstractCandies<?>> {
    
}
