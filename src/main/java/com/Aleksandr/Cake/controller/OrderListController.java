package com.Aleksandr.Cake.controller;

import static com.Aleksandr.utils.URLs.ORDER_LIST;
import static com.Aleksandr.utils.ViewURLs.ORDER_LIST_VIEW;

import com.Aleksandr.Cake.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class OrderListController {
	
	@Autowired
	private OrderService orderService;

	private final Logger LOGGER = LoggerFactory.getLogger(OrderListController.class);

	@RequestMapping(value = ORDER_LIST, method = RequestMethod.GET)
	public String getCustomerOrderList(Model model,
			@RequestParam(value = "groupBy", required = false) String groupByCustomerName) {
        LOGGER.info("-- method getCustomerOrderList execute");

		model.addAttribute("ordersTotals", orderService.getOrdersTotalAmount());
		model.addAttribute("allCustomerOrders", orderService.getOrderList());

		if (groupByCustomerName != null && groupByCustomerName.equals("customerName")) {
			model.addAttribute("ordersAmountByCustomerName", orderService.getOrdersTotalAmountByCustomerName());
		}
		return ORDER_LIST_VIEW;
	}
}
