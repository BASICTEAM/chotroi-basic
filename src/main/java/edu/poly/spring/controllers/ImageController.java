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
import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;
import edu.poly.spring.services.ShopService;
import edu.poly.spring.services.UserService;

@Controller
public class ImageController {

	private static final Logger log = LoggerFactory.getLogger(ImageController.class);

	@Autowired
	private ShopService shopService;

	@Autowired
	private UserService userService;

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

}
