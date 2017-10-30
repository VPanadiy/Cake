package com.Aleksandr.Cake;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.Aleksandr.Cake.model.Book;
import com.Aleksandr.Cake.repository.ReadingListRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CakeApplicationTests {

	@Mock
	private ReadingListRepository readingListRepository;

	@Test
	public void insertAndFind() {
		String nameReader = "sd7";
		Book book = new Book(null, nameReader, "ibsn7", "title7", "author7", "descr7");
		readingListRepository.save(book);
		List<Book> listBook = new ArrayList<>();
		listBook.add(book);
		Mockito.when(readingListRepository.findByReader(nameReader)).thenReturn(listBook);
	}

}
