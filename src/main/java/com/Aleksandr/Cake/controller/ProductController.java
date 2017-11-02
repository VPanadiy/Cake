package com.Aleksandr.Cake.controller;

import com.Aleksandr.Cake.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.Aleksandr.utils.CONST.URL_PRODUCTS;
import static com.Aleksandr.utils.ViewURLs.PRODUCTS_VIEW;

@Controller
public class ProductController {

    private ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(ReadingListController.class);

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RequestMapping(URL_PRODUCTS)
    public String productList(Model model) {
        logger.info("Open the page product.html -- my logger");
        model.addAttribute("products", productRepository.findAll());
        return PRODUCTS_VIEW;
    }

}
