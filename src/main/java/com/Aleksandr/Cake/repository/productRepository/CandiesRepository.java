package com.Aleksandr.Cake.repository.productRepository;

import com.Aleksandr.Cake.model.Candies;
import org.springframework.stereotype.Repository;

@Repository("candiesRepository")
public interface CandiesRepository extends CandiesBaseRepository<Candies> {

}
