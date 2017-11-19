package com.Aleksandr.Cake.services;

import com.Aleksandr.Cake.model.Orders;
import com.Aleksandr.Cake.model.User;
import com.Aleksandr.Cake.repository.OrdersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    @Qualifier("ordersRepository")
    private OrdersRepository ordersRepository;

    private final Logger logger = LoggerFactory.getLogger(OrdersServiceImpl.class);

    @Override
    public List<Orders> findOrderByUser(User user) {
        logger.info("Method findOrderByUserId of ordersService executed -- my logger");
        List<Orders> ordersList = ordersRepository.findByUserId(user);
        logger.info("-- List of findOrderByUser, with user id: " + + user.getId() + ", contains: " + ordersList.size() + " entry");
        return ordersList;
    }

}
