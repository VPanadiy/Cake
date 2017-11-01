package com.Aleksandr.Cake.controller;


import java.math.BigDecimal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.Aleksandr.Cake.model.Product;
import com.Aleksandr.Cake.model.ShoppingCartData;
import com.Aleksandr.Cake.services.ProductCartService;
import com.Aleksandr.Cake.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.Aleksandr.utils.URLs.*;
import static com.Aleksandr.utils.ViewURLs.*;


@Controller
//@RequestMapping(value = { MENU, DEFAULT_URL })
@SessionAttributes(value = {"product", "allCondimentsInShop", "shoppingCart"})

public class ProductMenuController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCartService productCartService;

    private final Logger LOGGER = LoggerFactory.getLogger(ProductMenuController.class);

    @ModelAttribute("shoppingCart")
    public void initializeShoppingCart(Model model) {

        Map<Integer, TreeMap<Integer, Product>> shoppingCart = new HashMap<Integer, TreeMap<Integer, Product>>();
        model.addAttribute("shoppingCart", shoppingCart);

    }

    @RequestMapping(value = {ALL, DEFAULT_URL}, method = RequestMethod.GET)
    public String listAllProducts(Model model) {
        LOGGER.info("-- method listAllProducts execute");
        model.addAttribute("products", productService.getAllProducts());
        return PRODUCT_LIST_VIEW;

    }

//    @RequestMapping(value = "/shoppingCart")
//    public ModelAndView shoppingCart() {
//
//        LOGGER.info("-- open page shoppingCart");
//        ModelAndView model = new ModelAndView();
//        List<Product> products = productService.getAllProducts();
//        LOGGER.info("-- product" + products.get(0).toString());
//        model.addObject("products", productService.getAllProducts());
//        model.setViewName("shoppingCart");
//        return model;
//    }

    @RequestMapping(value = CATEGORY, method = RequestMethod.GET)
    public String listAllProductsByCategory(@PathVariable("category") String productCategory, Model model) {

        model.addAttribute("products", productService.getProductsByCategory(productCategory));
        return PRODUCT_LIST_VIEW;

    }

    @RequestMapping(value = PRODUCT_DETAIL, method = RequestMethod.GET)
    public String getProductDetail(@RequestParam("productId") int productId, Model model) {

        Map<Integer, Product> allCondimentsInShop = productService.getAllCondiments();
        model.addAttribute("product", productService.getProductById(productId));
        model.addAttribute("allCondimentsInShop", allCondimentsInShop);
        return PRODUCT_DETAIL_VIEW;
    }

    @RequestMapping(value = PRODUCT_ADD_TO_CART, method = RequestMethod.GET)
    public String addProductToCart(@ModelAttribute("product") Product product,
                                   @ModelAttribute("shoppingCart") Map<Integer, TreeMap<Integer, Product>> shoppingCart) {

        ShoppingCartData data = new ShoppingCartData.ShoppingCartDataBuilder(shoppingCart, product).build();
        productCartService.addProductToShoppingCart(data);

        return PRODUCT_DETAIL_VIEW_REDIRECT + product.getId();
    }

    @RequestMapping(value = PRODUCT_DELETE_FROM_CART, method = RequestMethod.GET)
    public String deleteProductFromCart(@RequestParam("indexId") int productIndexIdInShoppingCart,
                                        @ModelAttribute("product") Product product,
                                        @ModelAttribute("shoppingCart") Map<Integer, TreeMap<Integer, Product>> shoppingCart) {

        ShoppingCartData data = new ShoppingCartData.ShoppingCartDataBuilder(shoppingCart, product)
                .orderIndexId(productIndexIdInShoppingCart).build();

        productCartService.deleteProductFromShoppingCart(data);

        return PRODUCT_DETAIL_VIEW_REDIRECT + product.getId();
    }

    @RequestMapping(value = PRODUCT_DELETE_ALL_CART, method = RequestMethod.GET)
    public String deleteAllProductFromCart(@ModelAttribute("product") Product product,
                                           @ModelAttribute("shoppingCart") Map<Integer, TreeMap<Integer, Product>> shoppingCart) {

        ShoppingCartData data = new ShoppingCartData.ShoppingCartDataBuilder(shoppingCart, product).build();
        productCartService.deleteAllProductFromShoppingCartByProductId(data);

        return PRODUCT_DETAIL_VIEW_REDIRECT + product.getId();
    }

    @RequestMapping(value = PRODUCT_ADD_CONDIMENT, method = RequestMethod.GET)
    public String addNewCondimentToProduct(@RequestParam("indexId") int orderIndexId,
                                           @RequestParam("condimentId") int condimentId, @ModelAttribute("product") Product product,
                                           @ModelAttribute("allCondimentsInShop") Map<Integer, Product> allCondimentsInShop,
                                           @ModelAttribute("shoppingCart") Map<Integer, TreeMap<Integer, Product>> shoppingCart) {

        ShoppingCartData data = new ShoppingCartData.ShoppingCartDataBuilder(shoppingCart, product)
                .orderIndexId(orderIndexId).allCondimentsInShop(allCondimentsInShop).condimentId(condimentId).build();

        productCartService.addCondimentToProductInShoppingCart(data);

        return PRODUCT_DETAIL_VIEW_REDIRECT + product.getId();
    }

    @RequestMapping(value = PRODUCT_DELETE_CONDIMENT, method = RequestMethod.GET)
    public String deleteCondimentFromProduct(@RequestParam("indexId") int orderIndexId,
                                             @RequestParam("condimentId") int condimentId, @ModelAttribute("product") Product product,
                                             @ModelAttribute("allCondimentsInShop") Map<Integer, Product> allCondimentsInShop,
                                             @ModelAttribute("shoppingCart") Map<Integer, TreeMap<Integer, Product>> shoppingCart) {

        ShoppingCartData data = new ShoppingCartData.ShoppingCartDataBuilder(shoppingCart, product)
                .orderIndexId(orderIndexId).allCondimentsInShop(allCondimentsInShop).condimentId(condimentId).build();

        productCartService.deleteCondimentFromProductInShoppingCart(data);

        return PRODUCT_DETAIL_VIEW_REDIRECT + product.getId();
    }

    @RequestMapping(value = PRODUCT_SHOPPING_CART, method = RequestMethod.GET)
    public String goToShoppingCart(@ModelAttribute("shoppingCart") Map<Integer, TreeMap<Integer, Product>> shoppingCart,
                                   Model model, final RedirectAttributes redirectAttributes) {

        TreeMap<Integer, BigDecimal> calculatedPrices = productCartService
                .calculateProductsSumByIdInShoppingCart(shoppingCart);

        BigDecimal totalAll = productCartService.calculateProductsSumAllInShoppingCart(calculatedPrices);

        Map<Integer, Product> products = new HashMap<Integer, Product>();

        for (Product product : productService.getAllProducts()) {
            products.put(product.getId(), product);
        }

        model.addAttribute("products", products);
        model.addAttribute("calculatedPrices", calculatedPrices);
        model.addAttribute("totalAll", totalAll);
        model.addAttribute("reducedPrice", productCartService.calculateDiscount(shoppingCart));
        return PRODUCT_SHOPPING_CART_VIEW;
    }

    @RequestMapping(value = ORDER_DELETE_FROM_CART, method = RequestMethod.GET)
    public String deleteOrderFromCart(@RequestParam("productId") int productId,
                                      @ModelAttribute("shoppingCart") Map<Integer, TreeMap<Integer, Product>> shoppingCart) {

        shoppingCart.remove(productId);

        return PRODUCT_SHOPPING_CART_VIEW_REDIRECT;
    }

}
