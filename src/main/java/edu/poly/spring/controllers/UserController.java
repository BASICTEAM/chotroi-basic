package edu.poly.spring.controllers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.spring.dtos.EditshopDto;
import edu.poly.spring.dtos.EdituserDto;
import edu.poly.spring.dtos.UserLoginDTO;
import edu.poly.spring.helpers.UserLogin;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;
import edu.poly.spring.services.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	public String strImage = "";

	@Autowired
	private UserService userService;

	@RequestMapping("/profile/{id}")
	public String detailShop(Model model, @PathVariable(name = "id") Integer id) {

		// Check login
		if (!UserLogin.authenticated_shop() && !UserLogin.authenticated_user()) {
			model.addAttribute("userLoginDTO", new UserLoginDTO());
			model.addAttribute("message", "Vui lòng đăng nhập để truy cập!");
			return "logins/login";
		}

		// Set user login
		User user = UserLogin.USER;
		model.addAttribute("user", user);
		model.addAttribute("userLogin", user);
		model.addAttribute("shopLogin", null);

		Optional<User> optUser = userService.findById(id);
		if (optUser.isPresent()) {
			strImage = optUser.get().getPicture();
			EdituserDto dto = new EdituserDto();
			BeanUtils.copyProperties(optUser.get(), dto);
			model.addAttribute("edituserDto", dto);
			return "users/profileUser";
		}

		return "users/profileUser";
	}

	@PostMapping("/update")
	public String update(Model model, @Validated EdituserDto edituserDto, BindingResult result) {

		// Check login
		if (!UserLogin.authenticated_shop() && !UserLogin.authenticated_user()) {
			model.addAttribute("userLoginDTO", new UserLoginDTO());
			model.addAttribute("message", "Vui lòng đăng nhập để truy cập!");
			return "logins/login";
		}

		// check error
		if (result.hasErrors()) {
			model.addAttribute("message", "Please input or required fields!!");
			model.addAttribute("edituserDto", edituserDto);
			return "users/profileUser";
		}

		User user = new User();

		model.addAttribute("message", "Cập nhật tài khoản thành công!");

		Path path = Paths.get("images/");
		try (InputStream inputStream = edituserDto.getImage().getInputStream()) {
			Files.copy(inputStream, path.resolve(edituserDto.getImage().getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			System.out.println("Image is null");
		}

		user.setId(UserLogin.USER.getId());
		user.setUsername(edituserDto.getUsername());
		user.setPassword(UserLogin.USER.getPassword());
		if (edituserDto.getImage().getOriginalFilename().equals("")) {
			user.setPicture(strImage);
		} else {
			user.setPicture(edituserDto.getImage().getOriginalFilename());
		}

		user.setEmail(edituserDto.getEmail());
		user.setPhone(edituserDto.getPhone());
		user.setAddress(edituserDto.getAddress());
		user.setGender(edituserDto.isGender());
		user.setFullname(edituserDto.getFullname());
		user.setBirthday(edituserDto.getBirthday());
		user.setStatus(UserLogin.USER.getStatus());

		userService.save(user);

		Optional<User> optUser = userService.findById(UserLogin.USER.getId());
		strImage = optUser.get().getPicture();
		EdituserDto dto = new EdituserDto();
		BeanUtils.copyProperties(optUser.get(), dto);
		model.addAttribute("user", user);
		model.addAttribute("edituserDto", dto);
		model.addAttribute("userLogin", user);
		model.addAttribute("shopLogin", null);

		return "users/profileUser";
	}

	@RequestMapping("/change-password/{id}")
	public String changePassword(Model model, @PathVariable(name = "id") Integer id) {

//		// Check login
//		if (!UserLogin.authenticated_shop() && !UserLogin.authenticated_user()) {
//			model.addAttribute("userLoginDTO", new UserLoginDTO());
//			model.addAttribute("message", "Vui lòng đăng nhập để truy cập!");
//			return "logins/login";
//		}

		// Set user login
		User user = UserLogin.USER;
		model.addAttribute("user", user);
		model.addAttribute("userLogin", user);
		model.addAttribute("shopLogin", null);

		Optional<User> optUser = userService.findById(id);
		if (optUser.isPresent()) {
			strImage = optUser.get().getPicture();
			EdituserDto dto = new EdituserDto();
			BeanUtils.copyProperties(optUser.get(), dto);
			model.addAttribute("edituserDto", dto);
			model.addAttribute("name", optUser.get().getUsername());
			return "users/profileUser";
		}

		return "shops/changePassword";
	}

//	HomeController hcl;
//	public String ima = "";
//	public int idshop ;
//	public String password = "";
//	@Autowired
//	private JavaMailSender emailSender;
//	@Autowired
//	private UserService userService;
//	@PostMapping("/update")
//	public String update(Model model, @Validated EdituserDto edituserDto, BindingResult result) {
//
//		// Check login
//		if (!UserLogin.authenticated1()) {
//			model.addAttribute("user", new User());
//			model.addAttribute("message", "Please log in to access!!");
//			return "homes/login";
//		}
//		// check error
//		if (result.hasErrors()) {
//			model.addAttribute("message", "Please input or required fields!!");
//			model.addAttribute("edituserDto", edituserDto);
//			System.out.println("====" + result);
//			return "users/detailShop";
//		}
//		model.addAttribute("message", "Cap Nhat tài khoản thành công!");
//		Path path = Paths.get("images/");
//		try (InputStream inputStream = edituserDto.getImage().getInputStream()) {
//			Files.copy(inputStream, path.resolve(edituserDto.getImage().getOriginalFilename()),
//					StandardCopyOption.REPLACE_EXISTING);
//			String filename = edituserDto.getImage().getOriginalFilename();
//System.out.println(edituserDto.getImage());
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//		// Set user login
//	User shop = new User();
//	shop.setId(ShopController.idshop);
//	shop.setUsername(edituserDto.getUsername());
//	shop.setPassword(ShopController.password);;
//	if(edituserDto.getImage().getOriginalFilename().equals("")) {
//	shop.setPicture(ShopController.ima);	
//	}
//	else {
//		shop.setPicture(edituserDto.getImage().getOriginalFilename());
//	}
//	
//	shop.setEmail(edituserDto.getEmail());
//	shop.setPhone(edituserDto.getPhone());
//	shop.setAddress(edituserDto.getAddress());
//	shop.setGender(edituserDto.isGender());
//	shop.setFullname(edituserDto.getFullname());
//	shop.setBirthday(edituserDto.getBirthday());
//	userService.save(shop);
//	model.addAttribute("edituserDto", edituserDto);
//
//		return "users/detailShop";
//	}
//
}