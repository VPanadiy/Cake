package com.Aleksandr.Cake.repositories;

import com.Aleksandr.Cake.configuration.RepositoryConfiguration;
import com.Aleksandr.Cake.model.AbstractCake;
import com.Aleksandr.Cake.model.AbstractProduct;
import com.Aleksandr.Cake.model.Cake;
import com.Aleksandr.Cake.model.enums.ProductCategory;
import com.Aleksandr.Cake.repository.productRepository.CakeBaseRepository;
import com.Aleksandr.Cake.repository.productRepository.ProductBaseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RepositoryConfiguration.class})
public class ProductBaseRepositoryTest {

    @Autowired
    @Qualifier("productBaseRepository")
    private ProductBaseRepository<AbstractProduct<?>> productBaseRepository;

    @Autowired
    @Qualifier("cakeBaseRepository")
    private CakeBaseRepository<AbstractCake<?>> cakeBaseRepository;

    /* *************************************************************************/
    /* CAUTION: DO NOT RUN THIS TEST ON PRODUCTION. THIS TEST WORK WITH REAL DB*/
    /* *************************************************************************/
    @Test
    public void testCRUDProduct() {
        List<AbstractProduct<?>> productList = productBaseRepository.findAll(); //get current values from DB
        int countElementsOnListAtTheBeginning = productList.size();

        //setup cake
        AbstractCake cake = new Cake();
        cake.setName("Napoleon");
        cake.setDescription("Best wafer cake");
        cake.setPrice(new BigDecimal("360.95"));
        cake.setProductCategory(ProductCategory.Cake);
        cake.setWeight(0.950);

        //save cake, verify has ID value after save
        assertNull(cake.getId()); //null before save
        cakeBaseRepository.save(cake);
        assertNotNull(cake.getId()); //not null after save

        //fetch from DB
        AbstractCake fetchedProduct = (Cake) productBaseRepository.findOne(cake.getId());

        //should not be null
        assertNotNull(fetchedProduct);

        //should equal
        assertEquals(cake.getId(), fetchedProduct.getId());
        assertEquals(cake.getName(), fetchedProduct.getName());
        assertEquals(cake.getDescription(), fetchedProduct.getDescription());
        assertEquals(cake.getPrice(), fetchedProduct.getPrice());
        assertEquals(cake.getProductCategory(), fetchedProduct.getProductCategory());
        assertEquals(cake.getWeight(), fetchedProduct.getWeight(), 0.0);

        //update description and save
        fetchedProduct.setName("Super testy napoleon");
        fetchedProduct.setDescription("More testy then regular napoleon");
        fetchedProduct.setPrice(new BigDecimal("499.99"));
        fetchedProduct.setProductCategory(ProductCategory.Candies);
        fetchedProduct.setWeight(1.250);
        cakeBaseRepository.save(fetchedProduct);

        //get from DB, should be updated
        AbstractCake fetchedUpdatedProduct = (Cake) productBaseRepository.findOne(fetchedProduct.getId());
        assertEquals(fetchedProduct.getId(), fetchedUpdatedProduct.getId());
        assertEquals(fetchedProduct.getName(), fetchedUpdatedProduct.getName());
        assertEquals(fetchedProduct.getDescription(), fetchedUpdatedProduct.getDescription());
        assertEquals(fetchedProduct.getPrice(), fetchedUpdatedProduct.getPrice());
        assertEquals(fetchedProduct.getProductCategory(), fetchedUpdatedProduct.getProductCategory());
        assertEquals(fetchedProduct.getWeight(), fetchedUpdatedProduct.getWeight(), 0.0);

        //verify count of products in DB
        long productCount = productBaseRepository.count();
        assertEquals(productCount, countElementsOnListAtTheBeginning + 1);

        //get all products, list should only have one
        Iterable<AbstractProduct<?>> products = productBaseRepository.findAll();

        int count = 0;

        for (AbstractProduct p : products) {
            count++;
        }

        assertEquals(count, countElementsOnListAtTheBeginning + 1);

        //delete setup cake
        productBaseRepository.delete(fetchedUpdatedProduct.getId());

        List<AbstractProduct<?>> updatedProductList = productBaseRepository.findAll();

        //compare lists
        assertEquals(productList, updatedProductList);
    }

}
