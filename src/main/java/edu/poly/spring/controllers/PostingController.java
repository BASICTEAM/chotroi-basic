package edu.poly.spring.controllers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.poly.spring.dtos.PostingDetailDto;
import edu.poly.spring.dtos.RateDTO;
import edu.poly.spring.dtos.ShowRate;
import edu.poly.spring.dtos.UserLoginDTO;
import edu.poly.spring.helpers.UserLogin;
import edu.poly.spring.models.ChatBox;
import edu.poly.spring.models.Posting;
import edu.poly.spring.models.PostingDetail;
import edu.poly.spring.models.Product;
import edu.poly.spring.models.Rate;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;
import edu.poly.spring.services.ChatBoxService;
import edu.poly.spring.services.PostingDetailService;
import edu.poly.spring.services.PostingService;
import edu.poly.spring.services.ProductService;
import edu.poly.spring.services.RateService;
import edu.poly.spring.services.ShopService;
import edu.poly.spring.services.UserService;

@Controller
public class PostingController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ShopService shopService;

	@Autowired
	private UserService userService;

	@Autowired
	PostingDetailService postingDetailService;

	@Autowired
	PostingService postingService;

	@Autowired
	private ProductService productService;

	@Autowired
	private RateService rateService;

	@Autowired
	private ChatBoxService chatBoxService;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	static int idshop;
	static int iduser;
	static String type;
	static String sender="";
	public String image1 = "";
	public String image2 = "";
	public String image3 = "";
	public String image4 = "";

	@RequestMapping("/search")
	public String search(ModelMap model) {
		log.info("Request to Search");

		// Don't login to system
		if (!UserLogin.authenticated_user() && !UserLogin.authenticated_shop()) {
			model.addAttribute("user", null);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", null);

			return "postings/searchPostings";
		}

		// Login to system
		if (UserLogin.ROLE_USER.equals("shop")) {
			Shop shop = shopService.findByUsername(UserLogin.SHOP.getUsername());
			UserLogin.SHOP = shop;
			model.addAttribute("user", UserLogin.SHOP);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", UserLogin.SHOP);

			return "postings/searchPostings";
		}

		if (UserLogin.ROLE_USER.equals("user")) {
			User user = userService.findByUsername(UserLogin.USER.getUsername());
			UserLogin.USER = user;
			model.addAttribute("user", UserLogin.USER);
			model.addAttribute("userLogin", UserLogin.USER);
			model.addAttribute("shopLogin", null);

			return "postings/searchPostings";
		}

		return "postings/searchPostings";
	}

	@RequestMapping("/loai-danh-muc/search")
	public String searchProductsType(ModelMap model) {
		log.info("Request to Search");

		// Don't login to system
		if (!UserLogin.authenticated_user() && !UserLogin.authenticated_shop()) {
			model.addAttribute("user", null);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", null);

			return "postings/searchPostings";
		}

		// Login to system
		if (UserLogin.ROLE_USER.equals("shop")) {
			Shop shop = shopService.findByUsername(UserLogin.SHOP.getUsername());
			UserLogin.SHOP = shop;
			model.addAttribute("user", UserLogin.SHOP);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", UserLogin.SHOP);

			return "postings/searchPostings";
		}

		if (UserLogin.ROLE_USER.equals("user")) {
			User user = userService.findByUsername(UserLogin.USER.getUsername());
			UserLogin.USER = user;
			model.addAttribute("user", UserLogin.USER);
			model.addAttribute("userLogin", UserLogin.USER);
			model.addAttribute("shopLogin", null);

			return "postings/searchPostings";
		}

		return "postings/searchPostings";
	}

	@RequestMapping("/danh-muc/search")
	public String searchProducts(ModelMap model) {
		log.info("Request to Search");

		// Don't login to system
		if (!UserLogin.authenticated_user() && !UserLogin.authenticated_shop()) {
			model.addAttribute("user", null);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", null);

			return "postings/searchPostings";
		}

		// Login to system
		if (UserLogin.ROLE_USER.equals("shop")) {
			Shop shop = shopService.findByUsername(UserLogin.SHOP.getUsername());
			UserLogin.SHOP = shop;
			model.addAttribute("user", UserLogin.SHOP);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", UserLogin.SHOP);

			return "postings/searchPostings";
		}

		if (UserLogin.ROLE_USER.equals("user")) {
			User user = userService.findByUsername(UserLogin.USER.getUsername());
			UserLogin.USER = user;
			model.addAttribute("user", UserLogin.USER);
			model.addAttribute("userLogin", UserLogin.USER);
			model.addAttribute("shopLogin", null);

			return "postings/searchPostings";
		}

		return "postings/searchPostings";
	}

	@RequestMapping("/{id}")
	@Transactional
	public String postingDetail(ModelMap model, @PathVariable("id") Integer id) {

		String assessorUserLogin = "";

		// Don't login to system
		if (!UserLogin.authenticated_user() && !UserLogin.authenticated_shop()) {
			model.addAttribute("user", null);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", null);
		} else {
			// Login to system
			if (UserLogin.ROLE_USER.equals("shop")) {
				Shop shop = shopService.findByUsername(UserLogin.SHOP.getUsername());
				UserLogin.SHOP = shop;
				assessorUserLogin = shop.getUsername();
				model.addAttribute("user", UserLogin.SHOP);
				model.addAttribute("userLogin", null);
				model.addAttribute("shopLogin", UserLogin.SHOP);
			}

			if (UserLogin.ROLE_USER.equals("user")) {
				User user = userService.findByUsername(UserLogin.USER.getUsername());
				UserLogin.USER = user;
				assessorUserLogin = user.getUsername();
				model.addAttribute("user", UserLogin.USER);
				model.addAttribute("userLogin", UserLogin.USER);
				model.addAttribute("shopLogin", null);

			}
		}

		Optional<PostingDetail> postingDetail = postingDetailService.findById(id);

		if (!postingDetail.isPresent()) {
			return "homes/error";
		}

		String username = "";

		if (postingDetail.get().getPosting().getUser() != null) {
			username = postingDetail.get().getPosting().getUser().getUsername();
			model.addAttribute("userPosting", userService.findByUsername(username));
			model.addAttribute("shopPosting", null);
		}
		if (postingDetail.get().getPosting().getShop() != null) {
			username = postingDetail.get().getPosting().getShop().getUsername();
			model.addAttribute("userPosting", null);
			model.addAttribute("shopPosting", shopService.findByUsername(username));
		}

		List<ShowRate> listR = new ArrayList<ShowRate>();
		List<RateDTO> userRated = new ArrayList<RateDTO>();

		Session session = entityManagerFactory.createEntityManager().unwrap(Session.class);
		Query query = session.createSQLQuery("SELECT supply_unit, AVG(point) FROM rates WHERE supply_unit = '"
				+ username + "' GROUP BY supply_unit");

		Query queryRated = session.createSQLQuery("SELECT * FROM rates WHERE supply_unit = '" + username
				+ "' AND assessor = '" + assessorUserLogin + "'");

		List<Object[]> list = query.list();
		List<Object[]> rated = queryRated.list();

		System.out.println("----------list: " + list.size());
		System.out.println("----------rated: " + rated.size());

		if (list.size() == 0 && rated.size() == 0) {
			System.out.println("1");
			ShowRate sr = new ShowRate(username, 0.0);
			listR.add(sr);

			RateDTO dto = new RateDTO(null, null, null, 0.0, assessorUserLogin, username);
			userRated.add(dto);

			model.addAttribute("rateDTO", new Rate());
			model.addAttribute("showRate", listR);
			model.addAttribute("listRated", rateService.findAllBySupplyUnitLike(username));
			model.addAttribute("userRated", userRated);
		} else if (list.size() != 0 && rated.size() == 0) {
			System.out.println("2");
			for (Object[] object : list) {
				String userName = String.valueOf(object[0]);
				String point = String.valueOf(object[1]);

				ShowRate sr = new ShowRate(userName, Double.valueOf(point));
				listR.add(sr);
			}
			RateDTO dto = new RateDTO(null, null, null, 0.0, assessorUserLogin, username);
			userRated.add(dto);
			List<Rate> pd = rateService.findAllBySupplyUnitLike(username);
			model.addAttribute("rateDTO", new Rate());
			model.addAttribute("showRate", listR);
			model.addAttribute("listRated", rateService.findAllBySupplyUnitLike(username));
			model.addAttribute("userRated", userRated);
		} else {
			System.out.println("3");
			for (Object[] object : list) {
				String userName = String.valueOf(object[0]);
				String point = String.valueOf(object[1]);

				ShowRate sr = new ShowRate(userName, Double.valueOf(point));
				listR.add(sr);
			}
			for (Object[] objects : rated) {
				String idRated = String.valueOf(objects[0]);
				String reason = String.valueOf(objects[1]);
				String point = String.valueOf(objects[3]);
				String image = String.valueOf(objects[2]);
				String supplyUnit = String.valueOf(objects[5]);
				String assessor = String.valueOf(objects[4]);

				RateDTO dto = new RateDTO(Integer.valueOf(idRated), reason, null, Double.valueOf(point), assessor,
						supplyUnit);
				userRated.add(dto);
			}

			model.addAttribute("rateDTO", new Rate());
			model.addAttribute("showRate", listR);
			model.addAttribute("listRated", rateService.findAllBySupplyUnitLike(username));
			model.addAttribute("userRated", userRated);
		}

		return "postings/postingDetails";
	}

	@PostMapping("/saveRate")
	public String saveRate(ModelMap model, @Validated RateDTO rateDTO, BindingResult result,
			@RequestParam(name = "postingDetailId") String postingDetailId) {

		String filename = "";
		String assessor = "";
		String supplyUnit = "";

		// Check login
		if (!UserLogin.authenticated_shop() && !UserLogin.authenticated_user()) {
			model.addAttribute("userLoginDTO", new UserLoginDTO());
			model.addAttribute("message", "Vui lòng đăng nhập để truy cập!");
			return "logins/login";
		}

		if (UserLogin.ROLE_USER.equals("user")) {
			User user = UserLogin.USER;
			assessor = user.getUsername();
			model.addAttribute("user", user);
			model.addAttribute("userLogin", user);
			model.addAttribute("shopLogin", null);
		}

		if (UserLogin.ROLE_USER.equals("shop")) {
			Shop shop = UserLogin.SHOP;
			assessor = shop.getUsername();
			model.addAttribute("user", shop);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", shop);
		}

		Optional<PostingDetail> postingDetail = postingDetailService.findById(Integer.parseInt(postingDetailId));
		if (postingDetail.get().getPosting().getUser() != null) {
			supplyUnit = postingDetail.get().getPosting().getUser().getUsername();
		}
		if (postingDetail.get().getPosting().getShop() != null) {
			supplyUnit = postingDetail.get().getPosting().getShop().getUsername();
		}

		Rate rate = new Rate();
		rate.setId(rateDTO.getId());
		rate.setPoint(rateDTO.getPoint());
		rate.setReason(rateDTO.getReason());
		rate.setAssessor(assessor);
		rate.setSupplyUnit(supplyUnit);

		Path path = Paths.get("images/");
		try (InputStream inputStream = rateDTO.getImage().getInputStream()) {
			Files.copy(inputStream, path.resolve(rateDTO.getImage().getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			filename = rateDTO.getImage().getOriginalFilename();
			System.out.println(filename);
		} catch (Exception e) {
			e.printStackTrace();

		}
		System.out.println(filename);
		rate.setImage(filename);

		rateService.save(rate);

		return "redirect:/" + postingDetailId;
	}

	@GetMapping("/postingdetails/posting")
	public String add(ModelMap model) {

		// Check login
		if (!UserLogin.authenticated_shop() && !UserLogin.authenticated_user()) {
			model.addAttribute("userLoginDTO", new UserLoginDTO());
			model.addAttribute("message", "Vui lòng đăng nhập để truy cập!");
			return "logins/login";
		}

		if (UserLogin.ROLE_USER.equals("user")) {
			User user = UserLogin.USER;
			model.addAttribute("user", user);
			model.addAttribute("userLogin", user);
			model.addAttribute("shopLogin", null);
		}
		if (UserLogin.ROLE_USER.equals("shop")) {
			Shop shop = UserLogin.SHOP;
			model.addAttribute("user", shop);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", shop);
		}
		model.addAttribute("postingDetailDto", new PostingDetailDto());
		return "postings/posting";

	}

	@PostMapping("/postingdetails/saveOrUpdate")
	public String saveOrUpdate(ModelMap model, @Validated PostingDetailDto postingDetailDto, BindingResult result,
			@RequestParam(value = "productName", required = false) String productName,
			@RequestParam(value = "postingType", required = false) boolean postingType) {

		// Check login
		if (!UserLogin.authenticated_shop() && !UserLogin.authenticated_user()) {
			model.addAttribute("userLoginDTO", new UserLoginDTO());
			model.addAttribute("message", "Vui lòng đăng nhập để truy cập!");
			return "logins/login";
		}

		if (productName.equals("") || productName == null) {
			if (UserLogin.ROLE_USER.equals("user")) {
				User user = UserLogin.USER;
				model.addAttribute("user", user);
				model.addAttribute("userLogin", user);
				model.addAttribute("shopLogin", null);
			}
			if (UserLogin.ROLE_USER.equals("shop")) {
				Shop shop = UserLogin.SHOP;
				model.addAttribute("user", shop);
				model.addAttribute("userLogin", null);
				model.addAttribute("shopLogin", shop);
			}
			model.addAttribute("messageError", "Bạn chưa chọn danh mục sản phẩm!");
			return "postings/posting";
		}

		if (result.hasErrors()) {
			System.out.println(result);
			model.addAttribute("message", "Please input or required fields!!");
			model.addAttribute("postingDetailDto", postingDetailDto);
			return "postings/posting";
		}

		Product product = productService.findByName(productName);

		Posting posting = new Posting();
		posting.setType(postingType);
		posting.setProduct(product);
		posting.setDate(new Date());
		posting.setStatus("unapproved");
		
		if (UserLogin.ROLE_USER.equals("user")) {
			posting.setUser(UserLogin.USER);
		}
		if (UserLogin.ROLE_USER.equals("shop")) {
			posting.setShop(UserLogin.SHOP);
		}

		postingService.save(posting);

		Posting pt = postingService.findTopByOrderByIdDesc();
		PostingDetail postingDetail = new PostingDetail();
		postingDetail.setId(postingDetailDto.getId());
		postingDetail.setTitle(postingDetailDto.getTitle());
		postingDetail.setManufacturer(postingDetailDto.getManufacturer());
		postingDetail.setProduct_type(postingDetailDto.getProduct_type());
		postingDetail.setContent(postingDetailDto.getContent());
		postingDetail.setPrice(postingDetailDto.getPrice());
		postingDetail.setPosting(pt);
		postingDetail.setAddress(postingDetailDto.getAddress());

		if (postingDetailDto.getId() != 0 && postingDetailDto.getId() > 0) {
			model.addAttribute("message", "The tin updated!");
		} else {
			model.addAttribute("message", "New tin inserted!");

		}
		Path path = Paths.get("images/");
		try (InputStream inputStream = postingDetailDto.getPhoto1().getInputStream()) {
			Files.copy(inputStream, path.resolve(postingDetailDto.getPhoto1().getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			String filename1 = postingDetailDto.getPhoto1().getOriginalFilename();
			System.out.println(postingDetailDto.getPhoto1());
		} catch (Exception e) {
			e.printStackTrace();

		}
		try (InputStream inputStream = postingDetailDto.getPhoto2().getInputStream()) {
			Files.copy(inputStream, path.resolve(postingDetailDto.getPhoto2().getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			String filename2 = postingDetailDto.getPhoto2().getOriginalFilename();
			System.out.println(postingDetailDto.getPhoto2());
		} catch (Exception e) {
			e.printStackTrace();

		}
		try (InputStream inputStream = postingDetailDto.getPhoto3().getInputStream()) {
			Files.copy(inputStream, path.resolve(postingDetailDto.getPhoto3().getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			String filename3 = postingDetailDto.getPhoto3().getOriginalFilename();
			System.out.println(postingDetailDto.getPhoto3());
		} catch (Exception e) {
			e.printStackTrace();

		}
		try (InputStream inputStream = postingDetailDto.getPhoto4().getInputStream()) {
			Files.copy(inputStream, path.resolve(postingDetailDto.getPhoto4().getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			String filename4 = postingDetailDto.getPhoto4().getOriginalFilename();
			System.out.println(postingDetailDto.getPhoto4());
		} catch (Exception e) {
			e.printStackTrace();

		}

		if (postingDetailDto.getPhoto1().getOriginalFilename().equals("")) {
			postingDetail.setPicture1(image1);
		} else {
			postingDetail.setPicture1(postingDetailDto.getPhoto1().getOriginalFilename());
		}
		if (postingDetailDto.getPhoto2().getOriginalFilename().equals("")) {
			postingDetail.setPicture2(image2);
		} else {
			postingDetail.setPicture2(postingDetailDto.getPhoto2().getOriginalFilename());
		}
		if (postingDetailDto.getPhoto3().getOriginalFilename().equals("")) {
			postingDetail.setPicture3(image3);
		} else {
			postingDetail.setPicture3(postingDetailDto.getPhoto3().getOriginalFilename());
		}
		if (postingDetailDto.getPhoto4().getOriginalFilename().equals("")) {
			postingDetail.setPicture4(image4);
		} else {
			postingDetail.setPicture4(postingDetailDto.getPhoto4().getOriginalFilename());
		}

		postingDetailService.save(postingDetail);

		if (UserLogin.ROLE_USER.equals("user")) {
			User user = UserLogin.USER;
			model.addAttribute("user", user);
			model.addAttribute("userLogin", user);
			model.addAttribute("shopLogin", null);
		}
		if (UserLogin.ROLE_USER.equals("shop")) {
			Shop shop = UserLogin.SHOP;
			model.addAttribute("user", shop);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", shop);
		}

		model.addAttribute("postingDetailDto", new PostingDetailDto());
		model.addAttribute("message", "Tin đã đăng thành công!");
		return "postings/posting";
	}

	@GetMapping("/{username}/profile")
	public String getProfilePosting() {

		return "postings/profile";
	}

	@GetMapping("/chatuser/{id}")
	public String chatuser(ModelMap model,@PathVariable(name ="id")  Integer id) {
		String mes = null,userc = null,shopc = null;
		if (!UserLogin.authenticated_shop() && !UserLogin.authenticated_user()) {
			model.addAttribute("userLoginDTO", new UserLoginDTO());
			model.addAttribute("message", "Vui lòng đăng nhập để truy cập!");
			return "logins/login";
		}

		// User chat voi User
				if (UserLogin.ROLE_USER.equals("user")) {
					User user = UserLogin.USER;
					iduser = user.getId();
					model.addAttribute("user", user);
					model.addAttribute("userLogin", user);
					model.addAttribute("shopLogin", null);
					
					Optional<User>optUser =	userService.findById(id);
					if(optUser.isPresent()) {
						model.addAttribute("shop", optUser.get());
						idshop = id;
					} else {
						return "postings/postingDetails";
					}
					Optional<User>optUser2 =	userService.findById(iduser);
					if(optUser2.isPresent()) {
						model.addAttribute("user", optUser2.get());
						sender = optUser2.get().getFullname();
					} else {
						return "postings/postingDetails";
					}
					Session session1 = entityManagerFactory
							.createEntityManager()
							.unwrap(Session.class);
							Query query1 = session1.createSQLQuery(
									"	select time, message \r\n" + 
											"	from chatboxs  \r\n" +  
											"	where chatboxs.iduser="+iduser +" and chatboxs.iduser2="+id +" or chatboxs.iduser="+id +"and chatboxs.iduser2="+iduser );
				
							userc="uvsu";
							List<Object[]> list1 = ((org.hibernate.query.Query) query1).list();
							for (Iterator iterator1 = list1.iterator(); iterator1.hasNext();) {
							Object[] records1 = (Object[]) iterator1.next();
							System.out.println("=======" +records1[0]);
							System.out.println("=======" +records1[1]);

							}
							
							Session session = entityManagerFactory
									.createEntityManager()
									.unwrap(Session.class);
									Query query= session.createSQLQuery(
								"select distinct chat.iduser,chat.idshop,shop.shopname \r\n" + 
										"	from chatboxs chat \r\n" + 
										"	join shops shop on shop.id = chat.idshop \r\n" + 
										"					 where chat.iduser ="+iduser );
									List<Object[]> listchatshop = ((org.hibernate.query.Query) query).list();
									for (Iterator iterator = listchatshop.iterator(); iterator.hasNext();) {
									Object[] records = (Object[]) iterator.next();

									}
									Session session2 = entityManagerFactory
											.createEntityManager()
											.unwrap(Session.class);
											Query query2= session2.createSQLQuery(
										"select distinct chat.iduser,chat.iduser2,us.fullname \r\n" + 
												"	from chatboxs chat \r\n" + 
												"	join users us on us.id = chat.iduser2 \r\n" + 
												"					 where chat.iduser ="+iduser );
											List<Object[]> listchatuser = ((org.hibernate.query.Query) query2).list();
											for (Iterator iterator2 = listchatuser.iterator(); iterator2.hasNext();) {
											Object[] records2 = (Object[]) iterator2.next();

											}
									model.addAttribute("arrays", list1);	
									model.addAttribute("listchatshop", listchatshop);
									model.addAttribute("listchatuser", listchatuser);
									model.addAttribute("mes",mes);
									model.addAttribute("userc",userc);
									model.addAttribute("shopc",shopc);
									type = "uservsuser";
									model.addAttribute("chatbox",new ChatBox());
									return "homes/chat";
				}
//      Shop chat voi User
		if (UserLogin.ROLE_USER.equals("shop")) {
			Shop shop = UserLogin.SHOP;
			iduser = shop.getId();
			model.addAttribute("user", shop);
			model.addAttribute("userLogin", null);
			model.addAttribute("shopLogin", shop);
		
			Optional<User>optUser =	userService.findById(id);
			if(optUser.isPresent()) {
				model.addAttribute("user", optUser.get());
				idshop = id;
			} else {
				return "postings/postingDetails";
			}
			Optional<Shop>optShop =	shopService.findById(iduser);
			if(optShop.isPresent()) {
				model.addAttribute("shop", optShop.get());
				sender = optShop.get().getShopname();
			} else {
				return "postings/postingDetails";
			}
			Session session1 = entityManagerFactory
					.createEntityManager()
					.unwrap(Session.class);
					Query query1 = session1.createSQLQuery(
				"	SELECT chatboxs.time , chatboxs.message \r\n" + 
						"	FROM     chatboxs INNER JOIN \r\n" + 
						"	shops ON chatboxs.idshop = shops.id INNER JOIN \r\n" + 
						"	users ON chatboxs.iduser = users.id \r\n" + 
						"					 where users.id="+id + " and shops.id="+iduser );
		
					mes="khac";
					List<Object[]> list1 = ((org.hibernate.query.Query) query1).list();
					for (Iterator iterator1 = list1.iterator(); iterator1.hasNext();) {
					Object[] records1 = (Object[]) iterator1.next();
					System.out.println("=======" +records1[0]);
					System.out.println("=======" +records1[1]);

					}
					
					Session session = entityManagerFactory
							.createEntityManager()
							.unwrap(Session.class);
							Query query= session.createSQLQuery(
							"select distinct chat.idshop,chat.idshop2,shop.shopname \r\n" + 
								"	from chatboxs chat \r\n" + 
								"	join shops shop on shop.id = chat.idshop2 \r\n" + 
								"					 where chat.idshop ="+iduser );
							List<Object[]> listchatshop = ((org.hibernate.query.Query) query).list();
							for (Iterator iterator = listchatshop.iterator(); iterator.hasNext();) {
							Object[] records = (Object[]) iterator.next();

							}
							Session session2 = entityManagerFactory
									.createEntityManager()
									.unwrap(Session.class);
									Query query2= session2.createSQLQuery(
								"select distinct chat.idshop,chat.iduser,us.fullname \r\n" + 
										"	from chatboxs chat \r\n" + 
										"	join users us on us.id = chat.iduser \r\n" + 
										"					 where chat.idshop ="+iduser );
									List<Object[]> listchatuser = ((org.hibernate.query.Query) query2).list();
									for (Iterator iterator2 = listchatuser.iterator(); iterator2.hasNext();) {
									Object[] records2 = (Object[]) iterator2.next();

									}
							
							model.addAttribute("arrays", list1);	
							model.addAttribute("listchatshop", listchatshop);
							model.addAttribute("listchatuser", listchatuser);
							model.addAttribute("mes", mes);
							model.addAttribute("userc",userc);
							model.addAttribute("shopc",shopc);
							type = "shopvsuser";
							model.addAttribute("chatbox",new ChatBox());	
		}

		return "homes/chat";
	}
	
	@GetMapping("/chatshop/{id}")
	public String chatshop(ModelMap model,@PathVariable(name ="id")  Integer id) {
		String mes = null, shopc=null,userc=null;
		if (!UserLogin.authenticated_shop() && !UserLogin.authenticated_user()) {
			model.addAttribute("userLoginDTO", new UserLoginDTO());
			model.addAttribute("message", "Vui lòng đăng nhập để truy cập!");
			return "logins/login";
		}	
	
//  User chat voi Shop
if (UserLogin.ROLE_USER.equals("user")) {
User user = UserLogin.USER;
iduser = user.getId();
model.addAttribute("user", user);
model.addAttribute("userLogin", user);
model.addAttribute("shopLogin", null);

Optional<Shop>optShop =	shopService.findById(id);
if(optShop.isPresent()) {
model.addAttribute("shop", optShop.get());
idshop = id;
} else {
return "postings/postingDetails";
}
Optional<User>optUser =	userService.findById(iduser);
if(optUser.isPresent()) {
model.addAttribute("user", optUser.get());
sender = optUser.get().getFullname();
} else {
return "postings/postingDetails";
}
Session session1 = entityManagerFactory
.createEntityManager()
.unwrap(Session.class);
Query query1 = session1.createSQLQuery(
"	SELECT chatboxs.time , chatboxs.message \r\n" + 
"	FROM     chatboxs INNER JOIN \r\n" + 
"	shops ON chatboxs.idshop = shops.id INNER JOIN \r\n" + 
"	users ON chatboxs.iduser = users.id \r\n" + 
"					 where shops.id="+id + " and users.id="+iduser );

mes="khac";
List<Object[]> list1 = ((org.hibernate.query.Query) query1).list();
for (Iterator iterator1 = list1.iterator(); iterator1.hasNext();) {
Object[] records1 = (Object[]) iterator1.next();
System.out.println("=======" +records1[0]);
System.out.println("=======" +records1[1]);

}

Session session = entityManagerFactory
.createEntityManager()
.unwrap(Session.class);
Query query= session.createSQLQuery(
"select distinct chat.iduser,chat.idshop,shop.shopname \r\n" + 
	"	from chatboxs chat \r\n" + 
	"	join shops shop on shop.id = chat.idshop \r\n" + 
	"					 where chat.iduser ="+iduser );
List<Object[]> listchatshop = ((org.hibernate.query.Query) query).list();
for (Iterator iterator = listchatshop.iterator(); iterator.hasNext();) {
Object[] records = (Object[]) iterator.next();

}
Session session2 = entityManagerFactory
		.createEntityManager()
		.unwrap(Session.class);
		Query query2= session2.createSQLQuery(
	"select distinct chat.iduser,chat.iduser2,us.fullname \r\n" + 
			"	from chatboxs chat \r\n" + 
			"	join users us on us.id = chat.iduser2 \r\n" + 
			"					 where chat.iduser ="+iduser );
		List<Object[]> listchatuser = ((org.hibernate.query.Query) query2).list();
		for (Iterator iterator2 = listchatuser.iterator(); iterator2.hasNext();) {
		Object[] records2 = (Object[]) iterator2.next();

		}
	model.addAttribute("arrays", list1);	
	model.addAttribute("listchatshop", listchatshop);
	model.addAttribute("listchatuser", listchatuser);
	model.addAttribute("mes",mes);
	model.addAttribute("userc",userc);
	model.addAttribute("shopc",shopc);
	type = "uservsshop";
	model.addAttribute("chatbox",new ChatBox());	
}
//                  Shop chat vs Shop
	if (UserLogin.ROLE_USER.equals("shop")) {
		Shop shop = UserLogin.SHOP;
		iduser = shop.getId();
		model.addAttribute("user", shop);
		model.addAttribute("userLogin", null);
		model.addAttribute("shopLogin", shop);
	
		Optional<Shop>optShop =	shopService.findById(id);
		if(optShop.isPresent()) {
			model.addAttribute("user", optShop.get());
			idshop = id;
		} else {
			return "postings/postingDetails";
		}
		Optional<Shop>optShop1 =	shopService.findById(iduser);
		if(optShop1.isPresent()) {
			model.addAttribute("shop", optShop1.get());
			sender = optShop1.get().getShopname();
		} else {
			return "postings/postingDetails";
		}
		Session session1 = entityManagerFactory
				.createEntityManager()
				.unwrap(Session.class);
				Query query1 = session1.createSQLQuery(
			"	select time, message \r\n" + 
					"	from chatboxs  \r\n" +  
					"	where chatboxs.idshop="+iduser +" and chatboxs.idshop2="+id +" or chatboxs.idshop="+id +"and chatboxs.idshop2="+iduser);
	
				shopc="svss";
				List<Object[]> list1 = ((org.hibernate.query.Query) query1).list();
				for (Iterator iterator1 = list1.iterator(); iterator1.hasNext();) {
				Object[] records1 = (Object[]) iterator1.next();
				System.out.println("=======" +records1[0]);
				System.out.println("=======" +records1[1]);

				}
				
				Session session = entityManagerFactory
						.createEntityManager()
						.unwrap(Session.class);
						Query query= session.createSQLQuery(
						"select distinct chat.idshop,chat.idshop2,shop.shopname \r\n" + 
							"	from chatboxs chat \r\n" + 
							"	join shops shop on shop.id = chat.idshop2 \r\n" + 
							"					 where chat.idshop ="+iduser );
						List<Object[]> listchatshop = ((org.hibernate.query.Query) query).list();
						for (Iterator iterator = listchatshop.iterator(); iterator.hasNext();) {
						Object[] records = (Object[]) iterator.next();

						}
						Session session2 = entityManagerFactory
								.createEntityManager()
								.unwrap(Session.class);
								Query query2= session2.createSQLQuery(
							"select distinct chat.idshop,chat.iduser,us.fullname \r\n" + 
									"	from chatboxs chat \r\n" + 
									"	join users us on us.id = chat.iduser \r\n" + 
									"					 where chat.idshop ="+iduser );
								List<Object[]> listchatuser = ((org.hibernate.query.Query) query2).list();
								for (Iterator iterator2 = listchatuser.iterator(); iterator2.hasNext();) {
								Object[] records2 = (Object[]) iterator2.next();

								}
						model.addAttribute("arrays", list1);	
						model.addAttribute("listchatshop", listchatshop);
						model.addAttribute("listchatuser", listchatuser);
						model.addAttribute("mes",mes);
						model.addAttribute("userc",userc);
						model.addAttribute("shopc",shopc);
						model.addAttribute("chatbox",new ChatBox());	
						type = "shopvsshop";
						return "homes/chat";
	}
	return "homes/chat";
	}
}
