package com.Aleksandr.Cake.repositories;

import com.Aleksandr.Cake.configuration.RepositoryConfiguration;
import com.Aleksandr.Cake.model.AbstractProduct;
import com.Aleksandr.Cake.model.Cake;
import com.Aleksandr.Cake.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RepositoryConfiguration.class})
public class ProductRepositoryTest {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Test
    public void testSaveProduct(){
        //setup cake
        AbstractProduct cake = new Cake();
        cake.setDescription("Spring Framework Guru Shirt");
        cake.setPrice(new BigDecimal("18.95"));

        //save cake, verify has ID value after save
        assertNull(cake.getId()); //null before save
        productRepository.save(cake);
        assertNotNull(cake.getId()); //not null after save

        //fetch from DB
        AbstractProduct fetchedProduct = productRepository.findOne(cake.getId());

        //should not be null
        assertNotNull(fetchedProduct);

        //should equal
        assertEquals(cake.getId(), fetchedProduct.getId());
        assertEquals(cake.getDescription(), fetchedProduct.getDescription());

        //update description and save
        fetchedProduct.setDescription("New Description");
        productRepository.save(fetchedProduct);

        //get from DB, should be updated
        AbstractProduct fetchedUpdatedProduct = productRepository.findOne(fetchedProduct.getId());
        assertEquals(fetchedProduct.getDescription(), fetchedUpdatedProduct.getDescription());

        //verify count of products in DB
        long productCount = productRepository.count();
        assertEquals(productCount, 1);

        //get all products, list should only have one
        Iterable<AbstractProduct> products = productRepository.findAll();

        int count = 0;

        for(AbstractProduct p : products){
            count++;
        }

        assertEquals(count, 1);
    }
}
