package com.Aleksandr.Cake.repository;

import com.Aleksandr.Cake.model.AbstractProduct;
import com.Aleksandr.Cake.model.UserComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("userCommentsRepository")
public interface UserCommentsRepository extends JpaRepository<UserComments, Long> {

    List<UserComments> findByProductId(AbstractProduct<?> product);

    @Transactional
    void removeByProductId(AbstractProduct<?> product);

}
