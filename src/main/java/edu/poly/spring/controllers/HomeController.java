package edu.poly.spring.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.poly.spring.helpers.UserLogin;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;
import edu.poly.spring.services.ShopService;
import edu.poly.spring.services.UserService;

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

//	@GetMapping("/signup")
//	public String signup(ModelMap model) {
//		return "homes/luachon";
//	}
//
//	@RequestMapping("/active&i={id}&e={email}&t=e1e1cdcebc9a1c004cb9cf7f5ac2d4c5")
//	public String activeEmail(ModelMap model, @PathVariable(name = "id") Integer id,
//			@PathVariable(name = "email") String email) {
//
//		Optional<Shop> shop = shopService.findById(id);
//		shop.get().setStatus("unblock");
//		Shop shop2 = new Shop();
//		BeanUtils.copyProperties(shop.get(), shop2);
//		shopService.save(shop2);
//
//		/*
//		 * if (shop.isPresent()) { model.addAttribute("message", "Có gì đó sai sai");
//		 * model.addAttribute("shop", new Shop()); return "homes/login"; }
//		 */
//
//		model.addAttribute("messageComplete", "Tài khoản của bạn đã kích hoạt thành công!");
//		model.addAttribute("shop", new Shop());
//		return "homes/login";
//	}
//
//	@RequestMapping("/sendActive&i={id}&e={email}&t=e1e1cdcebc9a1c004cb9cf7f5ac2d4c5")
//	public String sendActive(ModelMap model, @PathVariable(name = "id") Integer id,
//			RedirectAttributes redirectAttributes) {
//
//		System.out.println("aaaa");
//
//		Optional<Shop> shop = shopService.findById(id);
//
//		// Send mail
//		String username = shop.get().getUsername();
//		String email = shop.get().getEmail();
//		String text = "Xin chào " + username
//				+ ",\n \nCảm ơn bạn đã đăng ký tài khoản Chợ Trời.\n \nNhấn vào đường link để kích hoạt tài khoản của bạn. Nếu trang không hiển thị, bạn có thể sao chép và dán liên kết vào trình duyệt của mình: http://localhost:8080/active&i="
//				+ id + "&e=" + email
//				+ "&t=e1e1cdcebc9a1c004cb9cf7f5ac2d4c5\n \nLiên hệ với chúng tôi qua email: chotroi.basic@gmail.com để được hỗ trợ nhiều hơn.\n \nThân ái,";
//
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo(email);
//		message.setSubject("KÍCH HOẠT TÀI KHOẢN CHỢ TRỜI");
//		message.setText(text);
//		this.emailSender.send(message);
//
//		redirectAttributes.addFlashAttribute("message", "Mã kích hoạt đã được gửi về hộp thư!");
//		model.addAttribute("messageComplete", "Tài khoản của bạn đã kích hoạt thành công!");
//		model.addAttribute("user", shop);
//
//		return "redirect:/shops/detailshop/" + id;
//	}
//
//	@GetMapping("/signupshop")
//	public String signupshop(ModelMap model) {
//		DangkydailyDto shop = new DangkydailyDto();
//		model.addAttribute("dangkydailyDto", shop);
//		return "homes/register";
//	}
//
//	@GetMapping("/signupuser")
//	public String signupuser(ModelMap model) {
//		DangkydailyDto user = new DangkydailyDto();
//		model.addAttribute("dangkydailyDto", user);
//		return "homes/register2";
//	}
//
//	@PostMapping("/savesignup")
//	public String savesignup(ModelMap model, @Validated DangkydailyDto dangkydailyDto, BindingResult result) {
//
//		if (result.hasErrors()) {
//			model.addAttribute("message", "Please input or required fields!!");
//			model.addAttribute("dangkydailyDto", dangkydailyDto);
//			return "homes/register";
//		}
//
//		// shop
//		String strUsername = dangkydailyDto.getUsername();
//		List<Shop> listShop = (List<Shop>) shopService.findAll();
//		for (Shop shops : listShop) {
//			if (strUsername.equals(shops.getUsername())) {
//				model.addAttribute("messageError", "Tài khoản đã tồn tại!");
//				return "homes/register";
//			}
//		}
//		String strEmail = dangkydailyDto.getEmail();
//		for (Shop shops : listShop) {
//			if (strEmail.equals(shops.getEmail())) {
//				model.addAttribute("messageError", "Email đã tồn tại!");
//				return "homes/register";
//			}
//		}
//
//		// user
//		String strUsername1 = dangkydailyDto.getUsername();
//		List<User> listShop1 = (List<User>) userService.findAll();
//		for (User users : listShop1) {
//			if (strUsername1.equals(users.getUsername())) {
//				model.addAttribute("messageError", "Tài khoản đã tồn tại!");
//				return "homes/register";
//			}
//		}
//		String strEmail1 = dangkydailyDto.getEmail();
//		for (User users : listShop1) {
//			if (strEmail1.equals(users.getEmail())) {
//				model.addAttribute("messageError", "Email đã tồn tại!");
//				return "homes/register";
//			}
//		}
//
//		// shop
//		Shop shop = new Shop();
//		shop.setId(dangkydailyDto.getId());
//		shop.setUsername(dangkydailyDto.getUsername());
//		shop.setPassword(dangkydailyDto.getPassword());
//
//		shop.setEmail(dangkydailyDto.getEmail());
//		shop.setPhone(dangkydailyDto.getPhone());
//		shop.setPicture("choTroi.png");
//		shop.setStatus("block");
//		model.addAttribute("message", "Đăng ký tài khoản đại lý thành công!");
//		shopService.save(shop);
//
//		// Send mail
//		Integer id = shop.getId();
//		String username = shop.getUsername();
//		String email = shop.getEmail();
//		String phone = shop.getPhone();
//		String text = "Xin chào " + username
//				+ ",\n \nChúc mừng bạn đã hoàn thành tông tin đăng ký tài khoản.\nBạn đã trở thành đại lý đối tác của Chợ Trời.\nDưới đây là thông tin tài khoản đã đăng ký:\n\t- Tên đăng nhập: "
//				+ username + "\n\t- Email: " + email + "\n\t- Số điện thoại: " + phone
//				+ "\nNhập vào đường link để kích hoạt tài khoản của bạn. Nếu trang không hiển thị, bạn có thể sao chép và dán liên kết vào trình duyệt của mình: http://localhost:8080/active&i="
//				+ id + "&e=" + email
//				+ "&t=e1e1cdcebc9a1c004cb9cf7f5ac2d4c5\nLiên hệ với chúng tôi qua email: chotroi.basic@gmail.com để được hỗ trợ nhiều hơn.\n \nThân ái!";
//
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo(email);
//		message.setSubject("ĐĂNG KÝ TÀI KHOẢN THÀNH CÔNG");
//		message.setText(text);
//		this.emailSender.send(message);
//
//		model.addAttribute("dangkydailyDto", dangkydailyDto);
//		return "homes/register";
//	}
//
//	@PostMapping("/savesignup2")
//	public String savesignup2(ModelMap model, @Validated DangkydailyDto dangkydailyDto, BindingResult result) {
//		if (result.hasErrors()) {
//			model.addAttribute("message", "Please input or required fields!!");
//			model.addAttribute("dangkydailyDto", dangkydailyDto);
//			return "homes/register";
//		}
//		String strUsername = dangkydailyDto.getUsername();
//		List<Shop> listShop = (List<Shop>) shopService.findAll();
//		for (Shop shops : listShop) {
//			if (strUsername.equals(shops.getUsername())) {
//				model.addAttribute("messageError", "Tài khoản đã tồn tại!");
//				return "homes/register";
//			}
//		}
//
//		String strEmail = dangkydailyDto.getEmail();
//		for (Shop shops : listShop) {
//			if (strEmail.equals(shops.getEmail())) {
//				model.addAttribute("messageError", "Email đã tồn tại!");
//				return "homes/register";
//			}
//		}
//
//		String strUsername1 = dangkydailyDto.getUsername();
//		List<User> listShop1 = (List<User>) userService.findAll();
//		for (User users : listShop1) {
//			if (strUsername1.equals(users.getUsername())) {
//				model.addAttribute("messageError", "Tài khoản đã tồn tại!");
//				return "homes/register";
//			}
//		}
//		String strEmail1 = dangkydailyDto.getEmail();
//		for (User users : listShop1) {
//			if (strEmail1.equals(users.getEmail())) {
//				model.addAttribute("messageError", "Email đã tồn tại!");
//				return "homes/register";
//			}
//		}
//		model.addAttribute("message", "Đăng ký tài khoản khách hàng thành công!");
//
//		User shop = new User();
//		shop.setId(dangkydailyDto.getId());
//		shop.setUsername(dangkydailyDto.getUsername());
//		shop.setPassword(dangkydailyDto.getPassword());
//
//		shop.setEmail(dangkydailyDto.getEmail());
//		shop.setPhone(dangkydailyDto.getPhone());
//		userService.save(shop);
//
//		model.addAttribute("dangkydailyDto", dangkydailyDto);
//		return "homes/register2";
//	}
//
////	@RequestMapping("/login")
////	public String login(ModelMap model) {
////		UserLogin.logoff();
////		return "homes/login";
////	}
//
//	@GetMapping("/signinshop")
//	public String signinshop(ModelMap model) {
//		Shop shop = new Shop();
//		model.addAttribute("shop", shop);
//		return "homes/login";
//	}
//
//	@GetMapping("/signinuser")
//	public String signinuser(ModelMap model) {
//		User user = new User();
//		model.addAttribute("user", user);
//		return "homes/login2";
//	}
//
//	@PostMapping("/signin")
//	public String signin(ModelMap model, @Validated Shop shop, BindingResult result) {
//
//		// Check fields
//		if (result.hasErrors()) {
//			model.addAttribute("message", "Please input all required fields!!");
//
//			return "homes/login";
//		}
//
//		// Check login
//		String username = shop.getUsername();
//		String password = shop.getPassword();
//
//		List<Shop> list = (List<Shop>) shopService.findAll();
//		model.addAttribute("message", "Tài khoản và mật khẩu không đúng!");
//		for (Shop shop2 : list) {
//			if (username.equals(shop2.getUsername())) {
//				if (password.equals(shop2.getPassword())) {
//					Shop shop3 = shopService.findByUsername(username);
//
//					// set UserLogin
//					UserLogin.USER = shop3;
//
//					model.addAttribute("user", shop3);
//					sel = "shop";
//					System.out.println("===========" + sel);
//					return "homes/index";
//				}
//				model.addAttribute("message", "Sai mật khẩu!");
//			}
//		}
//
//		return "homes/login";
//
//	}
//
//	@PostMapping("/signin2")
//	public String signin2(ModelMap model, @Validated User user, BindingResult result) {
//
//		// Check fields
//		if (result.hasErrors()) {
//			model.addAttribute("message", "Please input all required fields!!");
//
//			return "homes/login2";
//		}
//
//		// Check login
//
//		String username1 = user.getUsername();
//		String password1 = user.getPassword();
//		System.out.println(username1 + password1 + "============");
//		List<User> list1 = (List<User>) userService.findAll();
//		model.addAttribute("message", "Wrong username & password!!");
//		for (User user2 : list1) {
//			boolean role = user2.isRole();
//			System.out.println(user2.isRole());
//			if (role == true || role == false) {
//				if (username1.equals(user2.getUsername())) {
//					if (password1.equals(user2.getPassword())) {
//						User user3 = userService.findByUsername(username1);
//
//						// set UserLogin
//						UserLogin.USER1 = user3;
//
//						model.addAttribute("user", user3);
//						sel = "user";
//						System.out.println("===========" + sel);
//						return "homes/index";
//					}
//					model.addAttribute("message", "Wrong password!!");
//				}
//			}
//		}
//
//		return "homes/login2";
//
//	}
//
//	@RequestMapping("/forgotpassword")
//	public String forgotPassword(Model model) {
//
//		UserLogin.logoff();
//
//		return "homes/forgotPassword";
//	}
//
//	@PostMapping("/getpassword")
//	public String getPassword(Model model, @RequestParam(value = "username") String username,
//			@RequestParam(value = "email") String email) {
//
//		String errorMessage = "Tài khoản không đúng. Vui lòng nhập lại!";
//
//		List<Shop> list = (List<Shop>) shopService.findAll();
//		for (Shop shop : list) {
//			if (shop.getUsername().equals(username)) {
//				if (shop.getEmail().equals(email)) {
//
//					Shop shop2 = shopService.findByUsername(username);
//
//					// Send mail
//					String strEmail = shop.getEmail();
//
//					SimpleMailMessage message = new SimpleMailMessage();
//					message.setTo(strEmail);
//					message.setSubject("Chợ Trời - Mật khẩu của bạn");
//					message.setText("Kính chào " + shop2.getShopname()
//							+ ", chúng tôi cung cập lại mật khẩu cho bạn với tài khoản: " + shop.getUsername()
//							+ ", mật khẩu: " + shop2.getPassword() + ". Cảm ơn đã sử dụng dịch vụ của chúng tôi!");
//					this.emailSender.send(message);
//
//					model.addAttribute("message", "Mật khẩu đã được gửi về email thành công!");
//
//					return "homes/forgotPassword";
//				}
//				errorMessage = "Email không đúng. Vui lòng nhập lại!";
//			}
//		}
//
//		model.addAttribute("errorMessage", errorMessage);
//
//		return "homes/forgotPassword";
//	}
//
//	@GetMapping("/register")
//	public String add(ModelMap model) {
//
//		model.addAttribute("shop", new Shop());
//		return "homes/register";
//
//	}
//
//	@PostMapping("/signup")
//	public String saveOrUpdate(ModelMap model, Shop shop) {
//
//		String strUsername = shop.getUsername();
//		List<Shop> listShop = (List<Shop>) shopService.findAll();
//		for (Shop shops : listShop) {
//			if (strUsername.equals(shops.getUsername())) {
//				model.addAttribute("messageError", "Tài khoản đã tồn tại!");
//				return "homes/register";
//			}
//		}
//
//		// save shop
//		shopService.save(shop);
//
//		// Send mail
//		String strEmail = shop.getEmail();
//
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo(strEmail);
//		message.setSubject("Mã kích hoạt tài khoản");
//		int numberOfCharactor = 6;
//		RandomMa rand = new RandomMa();
//		message.setText("Mã kích hoạt tài khoản chợ trời của bạn là : " + rand.randomAlphaNumeric(numberOfCharactor));
//		this.emailSender.send(message);
//
//		model.addAttribute("message", "Đăng ký tài khoản thành công!");
//
//		return "homes/register";
//	}

//	public void tranIdToMail() {
//		Optional<Shop> optShop = shopService.findById(idshop);
//		if (optShop.isPresent()) {
//			MyConstants.mail = optShop.get().getEmail();
//		} else {
//		}
//	}

}
