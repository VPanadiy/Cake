package com.Aleksandr.Cake.model;

import com.Aleksandr.Cake.model.enums.ProductCategory;
import com.Aleksandr.Cake.model.interfaces.ProductInterface;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;

@Entity
@Table(name = "PRODUCT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PRODUCT_TYPE")
public abstract class AbstractProduct<T extends AbstractProduct> implements ProductInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "*Please provide a name for this product")
    private String name;

    @Column(name = "description")
    @NotEmpty(message = "*Please provide a description for this product")
    private String description;

    @Column(name = "price")
    @NotNull(message = "*Please provide product price")
    private BigDecimal price;

    @Column(name = "product_category")
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    @Column(name = "rating")
    private double rating;

    @Column(name = "image_data")
    private byte[] imageData;

    @OneToMany(mappedBy="productId")
    private Set<UserComments> userComments;

    public AbstractProduct() {
    }

    public AbstractProduct(String name, String description, BigDecimal price, ProductCategory productCategory, double rating, byte[] imageData) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.productCategory = productCategory;
        this.rating = rating;
        this.imageData = imageData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Set<UserComments> getUserComments() {
        return userComments;
    }

    public void setUserComments(Set<UserComments> userComments) {
        this.userComments = userComments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractProduct<?> that = (AbstractProduct<?>) o;

        if (Double.compare(that.rating, rating) != 0) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (productCategory != that.productCategory) return false;
        if (!Arrays.equals(imageData, that.imageData)) return false;
        return userComments != null ? userComments.equals(that.userComments) : that.userComments == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (productCategory != null ? productCategory.hashCode() : 0);
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + Arrays.hashCode(imageData);
        result = 31 * result + (userComments != null ? userComments.hashCode() : 0);
        return result;
    }

}
