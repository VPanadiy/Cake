package com.Aleksandr.Cake.repository;

import com.Aleksandr.Cake.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("orderDetailsRepository")
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    
}
