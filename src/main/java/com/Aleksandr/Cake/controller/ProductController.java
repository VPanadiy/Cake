package com.Aleksandr.Cake.controller;

import com.Aleksandr.Cake.model.User;
import com.Aleksandr.Cake.repository.ProductRepository;
import com.Aleksandr.Cake.serviceSecurity.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.Aleksandr.utils.CONST.URL_ADD_PRODUCTS;
import static com.Aleksandr.utils.CONST.URL_PRODUCTS;
import static com.Aleksandr.utils.ViewURLs.PRODUCTS_VIEW;

@Controller
public class ProductController {

    private UserService userService;
    private ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(ReadingListController.class);

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/include/header")
    @ResponseBody
    public String header(Model model) {
        logger.info("Open the page header.html -- my logger");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute("userName",
                "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        return PRODUCTS_VIEW;
    }

    @RequestMapping(URL_PRODUCTS)
    public String productList(Model model) {
        logger.info("Open the page products.html -- my logger");
        model.addAttribute("products", productRepository.findAll());
        return PRODUCTS_VIEW;
    }

    @RequestMapping(URL_ADD_PRODUCTS)
    public String addProduct(Model model) {
        logger.info("Open the page addProduct.html -- my logger");
        model.addAttribute("products", productRepository.findAll());
        return PRODUCTS_VIEW;
    }

}
