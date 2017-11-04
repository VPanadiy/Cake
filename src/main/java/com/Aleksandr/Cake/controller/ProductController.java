package com.Aleksandr.Cake.controller;

import com.Aleksandr.Cake.model.AbstractProduct;
import com.Aleksandr.Cake.model.Cake;
import com.Aleksandr.Cake.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;

import static com.Aleksandr.utils.CONST.URL_PRODUCTS;
import static com.Aleksandr.utils.ViewURLs.*;

@Controller
public class ProductController {

    private ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(ReadingListController.class);

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RequestMapping(value = URL_PRODUCTS, method = RequestMethod.GET)
    public String productList(Model model) {
        logger.info("Method productList executed -- my logger");
        model.addAttribute("products", productRepository.findAll());
        return PRODUCTS_VIEW;
    }

    @RequestMapping(PRODUCT_SHOW_BY_ID_VIEW)
    public String showProduct(@PathVariable Long id, Model model) {
        logger.info("Method showProduct executed -- my logger");
        model.addAttribute("product", productRepository.findOne(id));
        return PRODUCT_SHOW_VIEW;
    }

    @RequestMapping(NEW_PRODUCT_VIEW)
    public String newProduct(Model model) {
        logger.info("Method newProduct executed -- my logger");
        model.addAttribute("product", new AbstractProduct() {});
        Cake cake = new Cake();
        cake.setName("test");
        cake.setPrice(new BigDecimal(123));
        productRepository.save(cake);
        return PRODUCT_FORM_VIEW;
    }

    @RequestMapping(EDIT_PRODUCT_BY_ID_VIEW)
    public String editProduct(@PathVariable Long id, Model model) {
        logger.info("Method editProduct executed -- my logger");
        model.addAttribute("product", productRepository.findOne(id));
        return PRODUCT_FORM_VIEW;
    }

    @RequestMapping(value = PRODUCT_VIEW, method = RequestMethod.POST)
    public String saveProduct(AbstractProduct abstractProduct) {
        logger.info("Method saveProduct executed -- my logger");
        productRepository.save(abstractProduct);
        return PRODUCT_REDIRECT_VIEW + abstractProduct.getId();
    }

    @RequestMapping(DELETE_PRODUCT_BY_ID_VIEW)
    public String deleteProduct(@PathVariable Long id) {
        logger.info("Method deleteProduct executed -- my logger");
        productRepository.delete(id);
        return PRODUCTS_REDIRECT_VIEW;
    }

}
