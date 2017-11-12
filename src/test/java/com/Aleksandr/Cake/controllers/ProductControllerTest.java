package com.Aleksandr.Cake.controllers;

import com.Aleksandr.Cake.controller.ProductController;
import com.Aleksandr.Cake.model.AbstractProduct;
import com.Aleksandr.Cake.model.Cake;
import com.Aleksandr.Cake.model.Candies;
import com.Aleksandr.Cake.repository.ProductBaseRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

import static com.Aleksandr.utils.CONST.URL_PRODUCTS;
import static com.Aleksandr.utils.ViewURLs.PRODUCTS_VIEW;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ProductControllerTest {

    @Mock
    @Qualifier("productBaseRepository")
    private ProductBaseRepository<AbstractProduct<?>> productBaseRepository;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        InternalResourceViewResolver templateResolver = new InternalResourceViewResolver();
        templateResolver.setPrefix("/static/templates/");
        templateResolver.setSuffix(".html");

        mockMvc = MockMvcBuilders.standaloneSetup(productController).setViewResolvers(templateResolver).build();
    }

    @Test
    public void testList() throws Exception {
        List<AbstractProduct<?>> products = new ArrayList<>();
        products.add(new Cake());
        products.add(new Candies());

        when(productBaseRepository.findAll()).thenReturn(products);

        mockMvc.perform(get(URL_PRODUCTS))
                .andExpect(status().isOk())
                .andExpect(view().name(PRODUCTS_VIEW))
                .andExpect(model().attribute("products", hasSize(2)));
    }

//    @Test
//    public void testShow() throws Exception {
//        Integer id=1;
//
//        when(productBaseRepository.getProductById(id)).thenReturn(new Product());
//
//        mockMvc.perform(get(PRODUCT_DETAIL_VIEW))
//                .andExpect(status().isOk())
//                .andExpect(view().name("menu.product.detail"))
//                .andExpect(model().attribute("product", instanceOf(Product.class)));
//    }
//
//    @Test
//    public void testNewProduct() throws Exception {
//        verifyZeroInteractions(productBaseRepository);
//
//        mockMvc.perform(get(URL_PRODUCTS))
//                .andExpect(status().isOk())
//                .andExpect(view().name(PRODUCTS_VIEW))
//                .andExpect(model().attribute("products", instanceOf(AbstractProduct.class)));
//    }
}
