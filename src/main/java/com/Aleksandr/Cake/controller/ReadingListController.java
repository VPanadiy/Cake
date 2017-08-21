package com.Aleksandr.Cake.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Aleksandr.Cake.model.Book;
import com.Aleksandr.Cake.repository.ReadingListRepository;

import java.util.List;

@Controller
@ConfigurationProperties(prefix = "amazon")
public class ReadingListController {
	private ReadingListRepository readingListRepository;
	private final Logger logger = LoggerFactory.getLogger(ReadingListController.class);

	private String associateId;
	
	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}

	@Autowired
	public ReadingListController(ReadingListRepository readingListRepository) {
		this.readingListRepository = readingListRepository;
	}

	@RequestMapping("/index")
	public String home() {
		logger.info("Open the page index.jsp -- my logger");
		return "index";
	}

	@RequestMapping(value = "/{reader}", method = RequestMethod.GET)
	public String readersBooks(@PathVariable("reader") String reader, Model model) {
		List<Book> readingList = readingListRepository.findByReader(reader);
		if (readingList != null) {
			model.addAttribute("books", readingList);
			model.addAttribute("reader", reader);
			model.addAttribute("amazonID", associateId);
		}
		return "readingList";
	}

	@RequestMapping(value = "/{reader}", method = RequestMethod.POST)
	public String addToReadingList(@PathVariable("reader") String reader, Book book) {
		book.setReader(reader);
		// book.setId(4L);
		System.out.println(book);
		readingListRepository.save(book);
		return "redirect:/{reader}";
	}
}
