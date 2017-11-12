package com.Aleksandr.Cake.controller;

import com.Aleksandr.Cake.model.*;
import com.Aleksandr.Cake.model.enums.ProductCategory;
import com.Aleksandr.Cake.repository.CakeBaseRepository;
import com.Aleksandr.Cake.repository.CandiesBaseRepository;
import com.Aleksandr.Cake.repository.ProductBaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.EnumSet;

import static com.Aleksandr.utils.CONST.URL_PRODUCTS;
import static com.Aleksandr.utils.ViewURLs.*;

@Controller
public class ProductController {

    @Autowired
    @Qualifier("productBaseRepository")
    private ProductBaseRepository<AbstractProduct<?>> productBaseRepository;

    @Autowired
    @Qualifier("cakeBaseRepository")
    private CakeBaseRepository<AbstractCake<?>> cakeBaseRepository;

    @Autowired
    @Qualifier("candiesBaseRepository")
    private CandiesBaseRepository<AbstractCandies<?>> candiesBaseRepository;

    private final Logger logger = LoggerFactory.getLogger(ReadingListController.class);

    @RequestMapping(value = URL_PRODUCTS, method = RequestMethod.GET)
    public String productList(Model model) {
        logger.info("Method productList executed -- my logger");
        model.addAttribute("products", productBaseRepository.findAll());
        return PRODUCTS_VIEW;
    }

    @RequestMapping(PRODUCT_SHOW_BY_ID_VIEW)
    public String showProduct(@PathVariable Long id, Model model) {
        logger.info("Method showProduct executed -- my logger");
        model.addAttribute("product", productBaseRepository.findOne(id));
        return PRODUCT_SHOW_VIEW;
    }

    @RequestMapping(NEW_PRODUCT_VIEW)
    public String newProduct(Model model) {
        logger.info("Method newProduct executed -- my logger");
        model.addAttribute("product", new AbstractProduct() {
        });
        model.addAttribute("productType", EnumSet.allOf(ProductCategory.class));
        return PRODUCT_FORM_VIEW;
    }

    @RequestMapping(EDIT_PRODUCT_BY_ID_VIEW)
    public String editProduct(@PathVariable Long id, Model model) {
        logger.info("Method editProduct executed -- my logger");
        model.addAttribute("product", productBaseRepository.findOne(id));
        model.addAttribute("productType", productBaseRepository.findOne(id).getProductCategory());
        return PRODUCT_FORM_VIEW;
    }

    @RequestMapping(value = PRODUCT_CAKE_VIEW, method = RequestMethod.POST)
    public String saveCake(Cake cake) {
        logger.info("Method saveCake executed -- my logger");
        cake.setProductCategory(ProductCategory.Cake);
        cakeBaseRepository.save(cake);
        return PRODUCT_REDIRECT_VIEW + cake.getId();
    }

    @RequestMapping(value = PRODUCT_CANDIES_VIEW, method = RequestMethod.POST)
    public String saveCandies(Candies candies) {
        logger.info("Method saveCandies executed -- my logger");
        candies.setProductCategory(ProductCategory.Candies);
        candiesBaseRepository.save(candies);
        return PRODUCT_REDIRECT_VIEW + candies.getId();
    }

    @RequestMapping(DELETE_PRODUCT_BY_ID_VIEW)
    public String deleteProduct(@PathVariable Long id) {
        logger.info("Method deleteProduct executed -- my logger");
        productBaseRepository.delete(id);
        return PRODUCTS_REDIRECT_VIEW;
    }

}
