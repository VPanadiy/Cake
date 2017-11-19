package com.Aleksandr.Cake.repository;

import com.Aleksandr.Cake.model.Orders;
import com.Aleksandr.Cake.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ordersRepository")
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByUserId(User userId);

}
