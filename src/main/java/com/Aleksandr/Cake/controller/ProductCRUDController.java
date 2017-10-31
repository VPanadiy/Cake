package com.Aleksandr.Cake.controller;

import com.Aleksandr.Cake.model.Product;
import com.Aleksandr.Cake.services.ProductService;
import com.Aleksandr.Cake.validator.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

import static com.Aleksandr.utils.URLs.*;
import static com.Aleksandr.utils.ViewURLs.PRODUCT_ADD_VIEW;
import static com.Aleksandr.utils.ViewURLs.PRODUCT_ADD_VIEW_REDIRECT;

@Controller
public class ProductCRUDController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductValidator productValidator;

    @ModelAttribute("productCategories")
    public Map<String, String> prepareCategoryList() {
        Map<String, String> categories = new HashMap<String, String>();
        categories.put("hot", "Hot Beverage");
        categories.put("cold", "Cold Beverage");
        categories.put("other", "Other");
        return categories;
    }

    @RequestMapping(value = PRODUCT_ADD, method = RequestMethod.GET)
    public String getAddNewProductForm(@ModelAttribute("newProduct") Product newProduct, Model model) {

        model.addAttribute("allProducts", productService.getAllProductsAndCondiments());
        return PRODUCT_ADD_VIEW;
    }

    @RequestMapping(value = PRODUCT_ADD, method = RequestMethod.POST)
    public String processAddNewProductForm(@ModelAttribute("newProduct") Product productToBeAdded,
                                           BindingResult bindingResult, Model model) {

        productValidator.validate(productToBeAdded, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("allProducts", productService.getAllProductsAndCondiments());
            return PRODUCT_ADD_VIEW;
        }

        productService.addProduct(productToBeAdded);
        return PRODUCT_ADD_VIEW_REDIRECT;
    }

    @RequestMapping(value = PRODUCT_EDIT, method = RequestMethod.GET)
    public String editProduct(@RequestParam("id") int id, Model model) {

        model.addAttribute("newProduct", productService.getProductById(id));
        model.addAttribute("allProducts", productService.getAllProductsAndCondiments());
        model.addAttribute("update", "update");

        return PRODUCT_ADD_VIEW;
    }

    @RequestMapping(value = PRODUCT_UPDATE, method = RequestMethod.POST)
    public String updatePerson(@ModelAttribute("newProduct") Product productToBeUpdated, BindingResult bindingResult,
                               Model model) {

        productValidator.validate(productToBeUpdated, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("newProduct", productToBeUpdated);
            model.addAttribute("allProducts", productService.getAllProductsAndCondiments());
            model.addAttribute("update", "update");
            return PRODUCT_ADD_VIEW;
        }

        productService.updateProduct(productToBeUpdated);

        return PRODUCT_ADD_VIEW_REDIRECT;
    }

    @RequestMapping(value = PRODUCT_DELETE, method = RequestMethod.GET)
    public String deleteProduct(@ModelAttribute("newProduct") Product newProduct, @RequestParam("id") int id) {

        productService.deleteProduct(id);
        return PRODUCT_ADD_VIEW_REDIRECT;

    }

}
