package com.Aleksandr.Cake.model;

import javax.persistence.*;

@Entity
@Table(name = "user_comments")
public class UserComments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private AbstractProduct<?> productId;

    @Column(name = "post")
    private String post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public AbstractProduct<?> getProductId() {
        return productId;
    }

    public void setProductId(AbstractProduct<?> productId) {
        this.productId = productId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "UserComments{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", post='" + post + '\'' +
                '}';
    }

}
