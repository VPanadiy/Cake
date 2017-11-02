package com.Aleksandr.Cake.repositories;

import com.Aleksandr.Cake.configuration.RepositoryConfiguration;
import com.Aleksandr.Cake.model.AbstractCake;
import com.Aleksandr.Cake.model.AbstractProduct;
import com.Aleksandr.Cake.model.Cake;
import com.Aleksandr.Cake.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RepositoryConfiguration.class})
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    /* *************************************************************************/
    /* CAUTION: DO NOT RUN THIS TEST ON PRODUCTION. THIS TEST WORK WITH REAL DB*/
    /* *************************************************************************/
    @Test
    public void testCRUDProduct() {
        List<AbstractProduct> productList = new ArrayList<>(); //get current values from DB

        //setup cake
        AbstractCake cake = new Cake();
        cake.setName("Napoleon");
        cake.setDescription("Best wafer cake");
        cake.setPrice(new BigDecimal("360.95"));
        cake.setCategory("Wafer");
        cake.setWeight(0.950);

        //save cake, verify has ID value after save
        assertNull(cake.getId()); //null before save
        productRepository.save(cake);
        assertNotNull(cake.getId()); //not null after save

        //fetch from DB
        AbstractCake fetchedProduct = (Cake) productRepository.findOne(cake.getId());

        //should not be null
        assertNotNull(fetchedProduct);

        //should equal
        assertEquals(cake.getId(), fetchedProduct.getId());
        assertEquals(cake.getName(), fetchedProduct.getName());
        assertEquals(cake.getDescription(), fetchedProduct.getDescription());
        assertEquals(cake.getPrice(), fetchedProduct.getPrice());
        assertEquals(cake.getCategory(), fetchedProduct.getCategory());
        assertEquals(cake.getWeight(), fetchedProduct.getWeight(), 0.0);

        //update description and save
        fetchedProduct.setName("Super testy napoleon");
        fetchedProduct.setDescription("More testy then regular napoleon");
        fetchedProduct.setPrice(new BigDecimal("499.99"));
        fetchedProduct.setCategory("Sugar-Wafer");
        fetchedProduct.setWeight(1.250);
        productRepository.save(fetchedProduct);

        //get from DB, should be updated
        AbstractCake fetchedUpdatedProduct = (Cake) productRepository.findOne(fetchedProduct.getId());
        assertEquals(fetchedProduct.getId(), fetchedUpdatedProduct.getId());
        assertEquals(fetchedProduct.getName(), fetchedUpdatedProduct.getName());
        assertEquals(fetchedProduct.getDescription(), fetchedUpdatedProduct.getDescription());
        assertEquals(fetchedProduct.getPrice(), fetchedUpdatedProduct.getPrice());
        assertEquals(fetchedProduct.getCategory(), fetchedUpdatedProduct.getCategory());
        assertEquals(fetchedProduct.getWeight(), fetchedUpdatedProduct.getWeight(), 0.0);

        //verify count of products in DB
        long productCount = productRepository.count();
        assertEquals(productCount, 1);

        //get all products, list should only have one
        Iterable<AbstractProduct> products = productRepository.findAll();

        int count = 0;

        for (AbstractProduct p : products) {
            count++;
        }

        assertEquals(count, 1);

        //delete setup cake
        productRepository.delete(fetchedUpdatedProduct);

        List<AbstractProduct> updatedProductList = new ArrayList<>();

        //compare lists
        assertEquals(productList, updatedProductList);
    }

}
