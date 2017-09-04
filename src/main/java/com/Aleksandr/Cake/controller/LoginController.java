package com.Aleksandr.Cake.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.Aleksandr.Cake.model.User;
import com.Aleksandr.Cake.serviceSecurity.UserService;

@Controller
public class LoginController {
	private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		LOGGER.info("Use method login() and " + modelAndView.toString());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		if (user != null) {
			modelAndView.addObject("isUser", true);
			modelAndView.addObject("userName",
					"Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
			modelAndView.addObject("message", "Content Available Only for Users with Admin Role");
			LOGGER.info("Use method login() after find user and " + modelAndView.toString());
		} else {
			modelAndView.addObject("isUser", false);
			LOGGER.info("Use method login() user == null " + modelAndView.toString());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
			LOGGER.info(userExists.getEmail() + "-------------------------------------------------------");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		LOGGER.info("Use method home() and " + modelAndView.toString());
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName",
				"Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		LOGGER.info("Use method home() after find user and " + modelAndView.toString());
		return modelAndView;
	}

	@RequestMapping(value = "/user/home", method = RequestMethod.GET)
	public ModelAndView userHome() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		LOGGER.info("Use method userHome() and " + modelAndView.toString());
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.setViewName("user/home");
		LOGGER.info("Use method userHome() after find user and " + modelAndView.toString() + " user = " + user.toString());
		return modelAndView;
	}
}
