package com.Aleksandr.Cake.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Aleksandr.Cake.model.Book;

public interface ReadingListRepository extends JpaRepository<Book, Long> {
	List<Book> findByReader(String reader);
}
