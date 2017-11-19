package com.Aleksandr.Cake.services;

import com.Aleksandr.Cake.model.Orders;
import com.Aleksandr.Cake.model.User;

import java.util.List;

public interface OrdersService {

    List<Orders> findOrderByUser(User user);

}
