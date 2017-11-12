package com.Aleksandr.Cake.repository;

import com.Aleksandr.Cake.model.Candies;
import org.springframework.stereotype.Repository;

@Repository("candiesRepository")
public interface CandiesRepository extends CandiesBaseRepository<Candies> {

}
