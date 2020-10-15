package edu.poly.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostingController {
	
	@RequestMapping("/json/categories")
	public String readJson() {		
		return "json/categories";
	}

	@RequestMapping("/posting")
	public String posting(ModelMap model) {
		
		return "posting/posting";
	}
	
	@RequestMapping("/search")
	public String search(ModelMap model, @RequestParam(name = "k") String keyword) {
		
		model.addAttribute("keyword", keyword);
		
		return "posting/filteredProducts";
	}
	
	@RequestMapping("/da-nang/laptop/76")
	public String postingDetail(ModelMap model) {
		
		return "posting/postingDeatails";
	}
}
