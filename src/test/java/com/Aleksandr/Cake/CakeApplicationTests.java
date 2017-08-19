package com.Aleksandr.Cake;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.Aleksandr.Cake.model.Book;
import com.Aleksandr.Cake.repository.ReadingListRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CakeApplicationTests {
	@Autowired
	private ReadingListRepository readingListRepository;
	
	@Test
	public void insertAndFind() {
		String nameReader = "sd7";
		Book book = new Book(null, nameReader, "ibsn7", "title7", "author7", "descr7");
		System.out.println("Starting insertion");
		System.out.println(readingListRepository.save(book));
		System.out.println("Finished insertion");
		
		readingListRepository.findAll().stream().forEach(System.out::println);
		assertEquals(nameReader, readingListRepository.findByReader(nameReader).get(0).getReader());
	}
	

}
