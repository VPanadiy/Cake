package com.Aleksandr.Cake.controller;

import static com.Aleksandr.utils.URLs.ORDER_PAYMENT;
import static com.Aleksandr.utils.URLs.ORDER_PAYMENT_CONFIRM;
import static com.Aleksandr.utils.URLs.ORDER_PAYMENT_SUCCESS;
import static com.Aleksandr.utils.ViewURLs.ORDER_PAYMENT_FORM_VIEW;
import static com.Aleksandr.utils.ViewURLs.ORDER_PAYMENT_VIEW_SUCCESS;
import static com.Aleksandr.utils.ViewURLs.ORDER_PAYMENT_VIEW_SUCCESS_REDIRECT;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import com.Aleksandr.Cake.model.Product;
import com.Aleksandr.Cake.model.order.Customer;
import com.Aleksandr.Cake.model.order.Order;
import com.Aleksandr.Cake.model.order.PaymentFormData;
import com.Aleksandr.Cake.services.OrderService;
import com.Aleksandr.Cake.services.ProductCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


@Controller
@SessionAttributes(value = { "shoppingCart" })
public class PaymentController {

	@Autowired
	private ProductCartService productCartService;

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = ORDER_PAYMENT, method = RequestMethod.POST)
	public String payment(@ModelAttribute("paymentFormData") PaymentFormData paymentFormData,
						  @ModelAttribute("shoppingCart") Map<Integer, TreeMap<Integer, Product>> shoppingCart, Model model) {
		BigDecimal calculatedAmount = productCartService.calculateDiscount(shoppingCart);
		model.addAttribute("calculatedAmount", calculatedAmount);
		model.addAttribute("paymentFormData", paymentFormData);
		return ORDER_PAYMENT_FORM_VIEW;

	}

	@RequestMapping(value = ORDER_PAYMENT_CONFIRM, method = RequestMethod.POST)
	public String paymentConfirm(@ModelAttribute("paymentFormData") PaymentFormData paymentFormData,
			@ModelAttribute("shoppingCart") Map<Integer, TreeMap<Integer, Product>> shoppingCart) {

		Customer customer = new Customer(paymentFormData.getCustomerName());
		Order order = new Order();
		BigDecimal calculatedAmount = productCartService.calculateDiscount(shoppingCart);
		order.setAmount(calculatedAmount);
		order.setCustomer(customer);
		orderService.insertOrder(order);

		return ORDER_PAYMENT_VIEW_SUCCESS_REDIRECT;

	}

	@RequestMapping(value = ORDER_PAYMENT_SUCCESS, method = RequestMethod.GET)
	public String paymentSuccess(SessionStatus status) {
		status.setComplete();
		return ORDER_PAYMENT_VIEW_SUCCESS;

	}


}
