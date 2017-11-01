package com.Aleksandr.Cake;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnit44Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.Aleksandr.Cake.controller.ReadingListController;
import com.Aleksandr.Cake.model.Book;
import com.Aleksandr.Cake.model.Reader;
import com.Aleksandr.Cake.model.User;
import com.Aleksandr.Cake.repository.ReadingListRepository;
import com.Aleksandr.Cake.serviceSecurity.UserServiceImpl;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(MockitoJUnit44Runner.class)
@ContextConfiguration(classes = CakeApplication.class)
@WebAppConfiguration
public class AddressServiceTests {
	@Autowired
	private WebApplicationContext webContext;
	private MockMvc mockMvc;
	@Mock
	private ReadingListRepository readingListRepository;
	@Before
	public void setupMockMvc() {
//		mockMvc = MockMvcBuilders
//				.webAppContextSetup(webContext)
//				.defaultRequest(get("/").with(user("user").roles("ADMIN")))
//				.apply(springSecurity())
//				.build();
		mockMvc = MockMvcBuilders.standaloneSetup(new ReadingListController(readingListRepository)).build();
	}

	@Test
	public void homePage() throws Exception {
		mockMvc.perform(get(""))
		.andExpect(status().isOk())
		.andExpect(header().string("Location", "index"));
	}

	@Test
//	@WithUserDetails("sd6")
	public void homePage_authenticatedUser() throws Exception {
		Reader expectedReader = new Reader();
		expectedReader.setUsername("craig");
		expectedReader.setPassword("password");
		expectedReader.setFullname("Craig Walls");
		
		mockMvc.perform(get("/index/sd7"))
		.andExpect(status().isOk())
		.andExpect(view().name("readingList"))
//				.andExpect(model().attribute("reader", samePropertyValuesAs(expectedReader)))
				.andExpect(model().attribute("books", hasSize(0)));
	}

	@Test
	public void postBook() throws Exception {
		mockMvc.perform(
				post("/readingList").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("title", "BOOK TITLE")
						.param("author", "BOOK AUTHOR").param("isbn", "1234567890").param("description", "DESCRIPTION"))
				.andExpect(status().is3xxRedirection()).andExpect(header().string("Location", "/index/readingList"));
		Book expectedBook = new Book();
		expectedBook.setId(1L);
		expectedBook.setReader("craig");
		expectedBook.setTitle("BOOK TITLE");
		expectedBook.setAuthor("BOOK AUTHOR");
		expectedBook.setIsbn("1234567890");
		expectedBook.setDescription("DESCRIPTION");
		mockMvc.perform(get("/readingList")).andExpect(status().isOk()).andExpect(view().name("readingList"))
				.andExpect(model().attributeExists("books")).andExpect(model().attribute("books", hasSize(1)))
				.andExpect(model().attribute("books", contains(samePropertyValuesAs(expectedBook))));
	}
}
