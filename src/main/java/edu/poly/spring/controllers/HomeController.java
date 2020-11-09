package edu.poly.spring.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.spring.helpers.UserLogin;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;
import edu.poly.spring.services.ShopService;
import edu.poly.spring.services.UserService;
import groovyjarjarpicocli.CommandLine.Model;

@Controller
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ShopService shopService;

	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String home(ModelMap model) {

		log.info("Request to Homepage");

		// Don't login to system
		if (!UserLogin.authenticated_user() && !UserLogin.authenticated_shop()) {
			model.addAttribute("user", null);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", null);

			return "homes/index";
		}

		// Login to system
		if (UserLogin.ROLE_USER.equals("shop")) {
			Shop shop = shopService.findByUsername(UserLogin.SHOP.getUsername());
			UserLogin.SHOP = shop;
			model.addAttribute("user", UserLogin.SHOP);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", UserLogin.SHOP);

			return "homes/index";
		}

		if (UserLogin.ROLE_USER.equals("user")) {
			User user = userService.findByUsername(UserLogin.USER.getUsername());
			UserLogin.USER = user;
			model.addAttribute("user", UserLogin.USER);
			model.addAttribute("userLogin", UserLogin.USER);
			model.addAttribute("shopLogin", null);

			return "homes/index";
		}

		return "homes/index";
	}
	
	@RequestMapping("/admin")
	public String admin(ModelMap model) {
		return "admin";
	}

}
