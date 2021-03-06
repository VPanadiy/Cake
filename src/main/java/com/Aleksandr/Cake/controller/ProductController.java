package com.Aleksandr.Cake.controller;

import com.Aleksandr.Cake.controller.wrapper.PageWrapper;
import com.Aleksandr.Cake.model.*;
import com.Aleksandr.Cake.model.enums.ProductCategory;
import com.Aleksandr.Cake.repository.OrderDetailsRepository;
import com.Aleksandr.Cake.repository.OrdersRepository;
import com.Aleksandr.Cake.repository.UserCommentsRepository;
import com.Aleksandr.Cake.repository.productRepository.CakeBaseRepository;
import com.Aleksandr.Cake.repository.productRepository.CandiesBaseRepository;
import com.Aleksandr.Cake.repository.productRepository.ProductBaseRepository;
import com.Aleksandr.Cake.serviceSecurity.UserService;
import com.Aleksandr.Cake.services.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    @Qualifier("ordersRepository")
    private OrdersRepository ordersRepository;

    @Autowired
    @Qualifier("orderDetailsRepository")
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    @Qualifier("userCommentsRepository")
    private UserCommentsRepository userCommentsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private OrdersService ordersService;

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @RequestMapping(value = URL_PRODUCTS, method = RequestMethod.GET)
    public String productList(Model model, Pageable pageable, HttpServletRequest request) {
        logger.info("Method productList executed -- my logger");

        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
        if (map != null) {
            logger.info("Method productList Redirect! -- my logger");
        } else {
            logger.info("Method productList Update! -- my logger");
        }

        Page<AbstractProduct<?>> productPage = productBaseRepository.findAll(pageable);
        PageWrapper<AbstractProduct<?>> page = new PageWrapper<>(productPage, "/products");

        model.addAttribute("products", page.getContent());
        model.addAttribute("page", page);
        return PRODUCTS_VIEW;
    }

    @RequestMapping(PRODUCT_SHOW_BY_ID_VIEW)
    public String showProduct(@PathVariable Long id, Model model) {
        logger.info("Method showProduct executed -- my logger");
        model.addAttribute("product", productBaseRepository.findOne(id));
        model.addAttribute("comments", userCommentsRepository.findByProductId(productBaseRepository.findOne(id)));
        return PRODUCT_SHOW_VIEW;
    }

    @RequestMapping(NEW_PRODUCT_VIEW)
    public String newProduct(Model model) {
        logger.info("Method newProduct executed -- my logger");
        model.addAttribute("product", new AbstractProduct() {
        });
        model.addAttribute("cake", new Cake());
        model.addAttribute("candies", new Candies());
        model.addAttribute("productType", EnumSet.allOf(ProductCategory.class));
        return PRODUCT_FORM_VIEW;
    }

    @RequestMapping(EDIT_PRODUCT_BY_ID_VIEW)
    public String editProduct(@PathVariable Long id, Model model) {
        logger.info("Method editProduct executed -- my logger");
        String productCategory = String.valueOf(productBaseRepository.findOne(id).getProductCategory());
        model.addAttribute("product", productBaseRepository.findOne(id));
        model.addAttribute("cake", productBaseRepository.findOne(id));
        model.addAttribute("candies", productBaseRepository.findOne(id));
        model.addAttribute("productType", productCategory);
        if (productCategory.equalsIgnoreCase(String.valueOf(ProductCategory.Cake))) {
            Cake editCake = (Cake) productBaseRepository.findOne(id);
            double weight = editCake.getWeight();
            logger.info("Edit cakes executed, where cake weight = " + String.valueOf(weight));
            model.addAttribute("weight", weight);
        } else if (productCategory.equalsIgnoreCase(String.valueOf(ProductCategory.Candies))) {
            Candies editCandies = (Candies) productBaseRepository.findOne(id);
            long count = editCandies.getCount();
            logger.info("Edit candies executed, where candies count = " + String.valueOf(count));
            model.addAttribute("count", count);
        }
        return PRODUCT_FORM_VIEW;
    }

    @RequestMapping(value = PRODUCT_CAKE_VIEW, method = RequestMethod.POST)
    public String saveCake(@ModelAttribute("file") MultipartFile file, @ModelAttribute("stringURL") String stringURL, @Valid @ModelAttribute("cake") Cake cake, BindingResult cakeBindingResult, @Valid @ModelAttribute("candies") Candies candies, BindingResult candiesBindingResult) throws IOException {
        logger.info("Method saveCake executed -- my logger");

        stringURL = getURL(stringURL);

        if (cakeBindingResult.hasErrors() || candiesBindingResult.hasErrors()) {
            return "redirect:/" + stringURL;
        }

        cake.setProductCategory(ProductCategory.Cake);
        if (file.getSize() != 0) {
            cake.setImageData(file.getBytes());
        }
        cakeBaseRepository.save(cake);
        return PRODUCT_REDIRECT_VIEW + cake.getId();
    }

    @RequestMapping(value = PRODUCT_CANDIES_VIEW, method = RequestMethod.POST)
    public String saveCandies(@ModelAttribute("file") MultipartFile file, @ModelAttribute("stringURL") String stringURL, @Valid @ModelAttribute("candies") Candies candies, BindingResult candiesBindingResult, @Valid @ModelAttribute("cake") Cake cake, BindingResult cakeBindingResult) throws IOException {
        logger.info("Method saveCandies executed -- my logger");

        stringURL = getURL(stringURL);

        if (candiesBindingResult.hasErrors() || cakeBindingResult.hasErrors()) {
            return "redirect:/" + stringURL;
        }

        candies.setProductCategory(ProductCategory.Candies);
        if (file.getSize() != 0) {
            candies.setImageData(file.getBytes());
        }
        candiesBaseRepository.save(candies);
        return PRODUCT_REDIRECT_VIEW + candies.getId();
    }

    private String getURL(@ModelAttribute("stringURL") String stringURL) {
        if (stringURL.contains("product/new")) {
            stringURL = "product/new";
        } else if (stringURL.contains("product/edit")) {
            int startString = stringURL.indexOf("product/edit");
            stringURL = stringURL.substring(startString);
        }
        return stringURL;
    }

    @RequestMapping(DELETE_PRODUCT_BY_ID_VIEW)
    public String deleteProduct(@PathVariable Long id) {
        logger.info("Method deleteProduct executed -- my logger");
        userCommentsRepository.removeByProductId(productBaseRepository.findOne(id));
        productBaseRepository.delete(id);
        return PRODUCTS_REDIRECT_VIEW;
    }

    @RequestMapping(value = "/AddProductToShoppingCart", method = RequestMethod.POST)
    public String addProductToShoppingCart(@ModelAttribute(value = "productId") Long id, @ModelAttribute("productCounter") Integer count, RedirectAttributes redirectAttributes) {
        logger.info("Method addProductToShoppingCart executed -- my logger");
        AbstractProduct product = productBaseRepository.findOne(id);
        logger.info("Product found!  Product id: " + product.getId());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        logger.info("User found! User id: " + user.getId());
        List<Orders> findOrdersList = ordersService.findOrderByUser(user);
        logger.info("FindOrdersList size = " + findOrdersList.size());

        Orders orders = new Orders();
        boolean isNewOrder = false;
        if (findOrdersList.size() == 0) {
            isNewOrder = true;
            BigDecimal cost = product.getPrice().multiply(new BigDecimal(count));
            logger.info("Counted cost = : " + cost);

            orders.setOrderDate(new Date());
            orders.setUserId(user);
            orders.setPrice(cost);
            orders.setOpen(true);
            ordersRepository.save(orders);
        } else {

            Date lastOrderDate = findOrdersList.stream().map(Orders::getOrderDate).max(Date::compareTo).get();
            logger.info("LastOrderDate = " + lastOrderDate);

            for (Orders order : findOrdersList) {
                if (order.getOrderDate() == lastOrderDate && order.isOpen()) {
                    orders = order;
                    logger.info("Last open order id: " + orders.getOrderId());
                }
            }

        }

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrder(orders);
        orderDetails.setAbstractProduct(product);
        orderDetails.setCount(count);
        orderDetails.setPayment(false);
        orderDetailsRepository.save(orderDetails);

        if (orders.getPrice().compareTo(BigDecimal.ZERO) != 0 && !isNewOrder) {
            BigDecimal newProductCost = product.getPrice().multiply(new BigDecimal(count));
            orders.setPrice(orders.getPrice().add(newProductCost));
            logger.info("Counted cost updated to = : " + newProductCost);
            ordersRepository.save(orders);
        }

        redirectAttributes.addFlashAttribute("flashProduct", orderDetails.getAbstractProduct().getName());
        redirectAttributes.addFlashAttribute("flashProductMessage", " added to shopping cart!");

        return PRODUCTS_REDIRECT_VIEW;
    }

    @RequestMapping(value = "/addPostToProduct", method = RequestMethod.POST)
    public String addPostToProduct(@ModelAttribute(value = "productId") Long id, @ModelAttribute(value = "comment") String comment, UserComments userComments, HttpServletRequest request) {
        logger.info("Method addPostToProduct executed -- my logger");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        logger.info("User found! User id: " + user.getId());
        userComments.setUserId(user);
        userComments.setProductId(productBaseRepository.findOne(id));
        userComments.setPost(comment);

//        Map of information from user request

//        Map<String, String> result = new HashMap<>();
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            result.put(key, value);
//        }

        String userIP = "";

        if (request != null) {
            userIP = request.getHeader("X-FORWARDED-FOR");
            if (userIP == null || "".equals(userIP)) {
                userIP = request.getRemoteAddr();
            }
        }

        userComments.setUserIP(userIP);
        userComments.setLocalDateTime(LocalDateTime.now());
        userCommentsRepository.save(userComments);
        return PRODUCT_REDIRECT_VIEW + id;
    }

    @RequestMapping("/shoppingCart")
    public String shoppingCart(Model model) {
        logger.info("Method shoppingCart executed -- my logger");
        model.addAttribute("orders", ordersRepository.findAll());
        return "shoppingCart";
    }

}
