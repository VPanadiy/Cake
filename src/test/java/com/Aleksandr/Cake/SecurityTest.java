package com.Aleksandr.Cake;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.Aleksandr.Cake.controller.LoginController;
import com.Aleksandr.Cake.model.User;
import com.Aleksandr.Cake.serviceSecurity.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SecurityTest {
  
	   static ApplicationContext applicationContext = null;
	   static UserServiceImpl login = null;
	     
	   @Autowired
	   WebConfig mockMvc;
	    /**
	     * Initialize the application context to re-use in all test cases
	     * */
	    @BeforeClass
	    public static void setup()
	    {
	        //Create application context instance
	        applicationContext = new ClassPathXmlApplicationContext();
	        //Get user details service configured in configuration 
	        login = applicationContext.getBean(UserServiceImpl.class);
	    }
	    
	    /**
	     * Test the valid user with valid role
	     * */
	    @Test
	    public void testValidRole()
	    {
	        //Get the user by username from configured user details service
	        User userDetails = login.findUserByEmail("qqq@qqq.ua");
	        Authentication authToken =new UsernamePasswordAuthenticationToken (userDetails.getPassword(), userDetails.getEmail());
	        SecurityContextHolder.getContext().setAuthentication(authToken);
	        LoginController service = (LoginController) applicationContext.getBean("/index");
	        service.login();
	    }

//	    @Test
//	    @WithMockUser(roles="ADMIN")
//	    public void testAdminPathIsAvailableToAdmin() throws Exception {
//	    	mockMvc.perform(get(LoginController.CONTROLLER_URL))
//	    			.andExpect(status().isOk()); // Expect that ADMIN users can access this page
//	    }
}
