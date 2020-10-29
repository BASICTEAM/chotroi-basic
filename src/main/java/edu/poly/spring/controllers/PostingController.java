package edu.poly.spring.controllers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.spring.dtos.PostingDetailDto;
import edu.poly.spring.dtos.UserLoginDTO;
import edu.poly.spring.helpers.UserLogin;
import edu.poly.spring.models.Posting;
import edu.poly.spring.models.PostingDetail;
import edu.poly.spring.models.Product;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;
import edu.poly.spring.services.PostingDetailService;
import edu.poly.spring.services.PostingService;
import edu.poly.spring.services.ProductService;
import edu.poly.spring.services.UserService;

@Controller
@RequestMapping("/postingdetails")
public class PostingController {

	public String image1 = "";
	public String image2 = "";
	public String image3 = "";
	public String image4 = "";

	@Autowired
	PostingDetailService postingDetailService;

	@Autowired
	PostingService postingService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@RequestMapping("/find-all")
	public List<Posting> findAll() {
		return (List<Posting>) postingService.findAll();
	}

	@RequestMapping("/search")
	public String search(ModelMap model, @RequestParam(name = "k") String keyword) {

		model.addAttribute("keyword", keyword);

		return "postings/filteredProducts";
	}

	@RequestMapping("/da-nang/laptop/76")
	public String postingDetail(ModelMap model) {

		return "postings/postingDeatails";
	}

	@GetMapping("/posting")
	public String add(ModelMap model) {
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

	@PostMapping("/saveOrUpdate")
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
		posting.setUser(UserLogin.USER);

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

	@ModelAttribute(name = "postings")
	public List<Posting> getPostings() {
		return postingDetailService.findAllPostings();
	}
}
