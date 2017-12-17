package com.Aleksandr.Cake.repository;

import com.Aleksandr.Cake.model.UserComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userCommentsRepository")
public interface UserCommentsRepository extends JpaRepository<UserComments, Long> {

}
