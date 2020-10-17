package edu.poly.spring.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.poly.spring.helpers.UserLogin;
import edu.poly.spring.models.PostingDetail;
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;
import edu.poly.spring.services.PostingDetailService;
import edu.poly.spring.services.ShopService;
import edu.poly.spring.services.UserService;

@Controller
public class ImageController {

	private static final Logger log = LoggerFactory.getLogger(ImageController.class);

	@Autowired
	private ShopService shopService;

	@Autowired
	private UserService userService;
	@Autowired
	private PostingDetailService postingDetailService;

	@RequestMapping(value = "getimage/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> downloadLinkInage(@PathVariable Integer id) {

		if (UserLogin.ROLE_USER.equals("shop")) {
			Optional<Shop> sop = shopService.findById(id);
			if (sop.isPresent()) {
				Shop shop = sop.get();
				try {
					Path filename = Paths.get("images", shop.getPicture());
					byte[] buffer = Files.readAllBytes(filename);
					ByteArrayResource bsr = new ByteArrayResource(buffer);
					return ResponseEntity.ok().contentLength(buffer.length)
							.contentType(MediaType.parseMediaType("image/png")).body(bsr);
				} catch (Exception e) {
					log.info("User Image is null!");
				}
			}
		}

		if (UserLogin.ROLE_USER.equals("user")) {
			Optional<User> sop = userService.findById(id);
			if (sop.isPresent()) {
				User user = sop.get();
				try {
					Path filename = Paths.get("images", user.getPicture());
					byte[] buffer = Files.readAllBytes(filename);
					ByteArrayResource bsr = new ByteArrayResource(buffer);
					return ResponseEntity.ok().contentLength(buffer.length)
							.contentType(MediaType.parseMediaType("image/png")).body(bsr);
				} catch (Exception e) {
					log.info("User Image is null!");
				}
			}
		}

		return ResponseEntity.badRequest().build();
	}

	
	
	@RequestMapping(value = "getimage1/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> downloadLinkInage1(@PathVariable Integer id) {
		Optional<PostingDetail> sop = postingDetailService.findById(id);
		if(sop.isPresent()) {
PostingDetail postingDetail = sop.get();
		try {
				Path filename1 = Paths.get("images",postingDetail.getPicture1());
				byte[] buffer1 = Files.readAllBytes(filename1);
				ByteArrayResource bsr1 = new ByteArrayResource(buffer1);
				return ResponseEntity.ok()
						.contentLength(buffer1.length)
						.contentType(MediaType.parseMediaType("image/png"))
						.body(bsr1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		} return ResponseEntity.badRequest().build();
	}
	@RequestMapping(value = "getimage2/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> downloadLinkInage2(@PathVariable Integer id) {
		Optional<PostingDetail> sop = postingDetailService.findById(id);
		if(sop.isPresent()) {
PostingDetail postingDetail = sop.get();
		try {
				Path filename2 = Paths.get("images",postingDetail.getPicture2());
				byte[] buffer2 = Files.readAllBytes(filename2);
				ByteArrayResource bsr2 = new ByteArrayResource(buffer2);
				return ResponseEntity.ok()
						.contentLength(buffer2.length)
						.contentType(MediaType.parseMediaType("image/png"))
						.body(bsr2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		} return ResponseEntity.badRequest().build();
	}
	@RequestMapping(value = "getimage3/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> downloadLinkInage3(@PathVariable Integer id) {
		Optional<PostingDetail> sop = postingDetailService.findById(id);
		if(sop.isPresent()) {
PostingDetail postingDetail = sop.get();
		try {
				Path filename3 = Paths.get("images",postingDetail.getPicture3());
				byte[] buffer3 = Files.readAllBytes(filename3);
				ByteArrayResource bsr3 = new ByteArrayResource(buffer3);
				return ResponseEntity.ok()
						.contentLength(buffer3.length)
						.contentType(MediaType.parseMediaType("image/png"))
						.body(bsr3);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		} return ResponseEntity.badRequest().build();
	}
	@RequestMapping(value = "getimage4/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> downloadLinkInage4(@PathVariable Integer id) {
		Optional<PostingDetail> sop = postingDetailService.findById(id);
		if(sop.isPresent()) {
PostingDetail postingDetail = sop.get();
		try {
				Path filename4 = Paths.get("images",postingDetail.getPicture4());
				byte[] buffer4 = Files.readAllBytes(filename4);
				ByteArrayResource bsr4 = new ByteArrayResource(buffer4);
				return ResponseEntity.ok()
						.contentLength(buffer4.length)
						.contentType(MediaType.parseMediaType("image/png"))
						.body(bsr4);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		} return ResponseEntity.badRequest().build();
	}
}
