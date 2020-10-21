package edu.poly.spring.controllers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import org.springframework.web.bind.annotation.RestController;

import edu.poly.spring.dtos.PostingDetailDto;
import edu.poly.spring.models.Posting;
import edu.poly.spring.models.PostingDetail;
import edu.poly.spring.services.PostingDetailService;
import edu.poly.spring.services.PostingService;

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
		PostingDetailDto postingDetail = new PostingDetailDto();
		model.addAttribute("postingDetailDto", postingDetail);
		return "postings/posting";

	}

	@PostMapping("/saveOrUpdate")
	public String saveOrUpdate(ModelMap model, @Validated PostingDetailDto postingDetailDto, BindingResult result) {
		if (result.hasErrors()) {
			System.out.println(result);
			model.addAttribute("message", "Please input or required fields!!");
			model.addAttribute("postingDetailDto", postingDetailDto);
			return "postings/posting";
		}
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

		PostingDetail pt = new PostingDetail();
		pt.setId(postingDetailDto.getId());
		pt.setTitle(postingDetailDto.getTitle());
		pt.setContent(postingDetailDto.getContent());
		if (postingDetailDto.getPhoto1().getOriginalFilename().equals("")) {
			pt.setPicture1(image1);
		} else {
			pt.setPicture1(postingDetailDto.getPhoto1().getOriginalFilename());
		}
		if (postingDetailDto.getPhoto2().getOriginalFilename().equals("")) {
			pt.setPicture2(image2);
		} else {
			pt.setPicture2(postingDetailDto.getPhoto2().getOriginalFilename());
		}
		if (postingDetailDto.getPhoto3().getOriginalFilename().equals("")) {
			pt.setPicture3(image3);
		} else {
			pt.setPicture3(postingDetailDto.getPhoto3().getOriginalFilename());
		}
		if (postingDetailDto.getPhoto4().getOriginalFilename().equals("")) {
			pt.setPicture4(image4);
		} else {
			pt.setPicture4(postingDetailDto.getPhoto4().getOriginalFilename());
		}
		pt.setPrice(postingDetailDto.getPrice());
		Posting posting = new Posting();
		posting.setId(postingDetailDto.getPostingId());
		pt.setPosting(posting);

		postingDetailService.save(pt);
		model.addAttribute("postingDetailDto", postingDetailDto);
		return "postings/posting";
	}

	@ModelAttribute(name = "postings")
	public List<Posting> getPostings() {
		return postingDetailService.findAllPostings();
	}
}
