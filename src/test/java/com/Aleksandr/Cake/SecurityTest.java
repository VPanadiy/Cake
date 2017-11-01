package com.Aleksandr.Cake;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Comparator;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.Aleksandr.Cake.controller.LoginController;
import com.Aleksandr.Cake.model.User;
import com.Aleksandr.Cake.repository.UserRepository;
import com.Aleksandr.Cake.serviceSecurity.UserServiceImpl;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
//@WebAppConfiguration
public class SecurityTest {
  
//	@Autowired
//	private WebApplicationContext context;

//	private MockMvc mvc;
//
//	@Before
//	public void setup() {
//		mvc = MockMvcBuilders
//				.webAppContextSetup(context)
//				.defaultRequest(get("/").with(user("user").roles("ADMIN")))
//				.apply(springSecurity())
//				.build();
//	}
	    
	    /**
	     * Test the valid user with valid role
	     * */
//	    @Test
//	    public void testValidRole()
//	    {
//	        //Get the user by username from configured user details service
//	        User userDetails = userRepository.findByEmail("qqq@qqq.ua");
//	        Authentication authToken = new UsernamePasswordAuthenticationToken (userDetails.getPassword(), userDetails.getEmail());
//	        SecurityContextHolder.getContext().setAuthentication(authToken);
//	        LoginController service = (LoginController) applicationContext.getBean("/index");
//	        service.login();
//	    }
	    
//	    @Test
//	    @WithMockUser(username="qqq@qqq.ua",roles={"USER"})
//	    public void getMessageWithMockUserCustomUser() {
////	    	String message = messageService.getMessage();
//	    	
//	    }
@Test
public void testName() throws Exception {
	String i = Stream.of("0ba", "1affffffffff").max(Comparator.comparing(x -> x)).get();
	System.out.println(i);
}
//	    @Test
//	    @WithMockUser(roles="ADMIN")
//	    public void testAdminPathIsAvailableToAdmin() throws Exception {
//	    	mockMvc.perform(get(LoginController.CONTROLLER_URL))
//	    			.andExpect(status().isOk()); // Expect that ADMIN users can access this page
//	    }
}
