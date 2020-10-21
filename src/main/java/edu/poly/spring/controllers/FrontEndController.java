package edu.poly.spring.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.poly.spring.models.Shop;
import edu.poly.spring.models.User;
import edu.poly.spring.services.ShopService;
import edu.poly.spring.services.UserService;

@RestController
public class FrontEndController {

	@Autowired
	private UserService userService;

	@Autowired
	private ShopService shopService;
	
	@RequestMapping("/json/categories")
	public String readJson() {
		return "libs/json/categories";
	}
	
	@RequestMapping("/libs/angular.min.js")
	public String readFileAngular() {
		return "libs/angular/angular.min.js";
	}
	
	@RequestMapping("/libs/angular.route.js")
	public String readFileAngularRoute() {
		return "libs/angular-route/angular.route.js";
	}
	@RequestMapping("/users/find-all")
	public List<User> findAllUsers() {
		return (List<User>) userService.findAll();
	}

	// -------------------------
	// ------ USER MANAGER -----
	// -------------------------

	@RequestMapping("/users/list-activated")
	public List<User> listUsersActivated() {
		return (List<User>) userService.findUsersByStatus("activated");
	}

	@RequestMapping("/users/list-not-activated")
	public List<User> listUsersNotActivated() {
		return (List<User>) userService.findUsersByStatus("not-activated");
	}

	@RequestMapping("/users/list-block")
	public List<User> listUsersNot() {
		return (List<User>) userService.findUsersByStatus("block");
	}

	@GetMapping("/users/{name}/find")
	public List<User> findByName(@PathVariable("name") String name) {
		return userService.findByUsernameLikeOrderByFullname(name);
	}

	@GetMapping("/users/{id}/get")
	public Optional<User> getUser(@PathVariable("id") Integer id) {
		return userService.findById(id);
	}

	@PutMapping("/users/{id}/update")
	public User update(@PathVariable Integer id, @RequestBody User user) {
		return userService.save(user);
	}

	@DeleteMapping("/users/{id}/delete")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
		userService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/users/{id}/block")
	public ResponseEntity<Void> blockUser(@PathVariable("id") Integer id) {
		Optional<User> user = userService.findById(id);
		user.get().setStatus("block");
		userService.save(user.get());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/users/{id}/activated")
	public ResponseEntity<Void> activatedUser(@PathVariable("id") Integer id) {
		Optional<User> user = userService.findById(id);
		user.get().setStatus("activated");
		userService.save(user.get());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/users/{id}/not-activated")
	public ResponseEntity<Void> notActivatedUser(@PathVariable("id") Integer id) {
		Optional<User> user = userService.findById(id);
		user.get().setStatus("not-activated");
		userService.save(user.get());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	// -------------------------
	// ------ SHOP MANAGER -----
	// -------------------------

	@RequestMapping("/shops/find-all")
	public List<Shop> findAllShops() {
		return (List<Shop>) shopService.findAll();
	}

	@RequestMapping("/shops/list-activated")
	public List<Shop> listShopsActivated() {
		return (List<Shop>) shopService.findShopsByStatus("activated");
	}

	@RequestMapping("/shops/list-not-activated")
	public List<Shop> listShopsNotActivated() {
		return (List<Shop>) shopService.findShopsByStatus("not-activated");
	}

	@RequestMapping("/shops/list-block")
	public List<Shop> listShopsNot() {
		return (List<Shop>) shopService.findShopsByStatus("block");
	}

	@GetMapping("/shops/{id}/get")
	public Optional<Shop> getShop(@PathVariable("id") Integer id) {
		return shopService.findById(id);
	}

	@PutMapping("/shops/{id}/update")
	public Shop update(@PathVariable Integer id, @RequestBody Shop shop) {
		return shopService.save(shop);
	}

	@DeleteMapping("/shops/{id}/delete")
	public ResponseEntity<Void> deleteShop(@PathVariable("id") Integer id) {
		shopService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/shops/{id}/block")
	public ResponseEntity<Void> blockShop(@PathVariable("id") Integer id) {
		Optional<Shop> shop = shopService.findById(id);
		shop.get().setStatus("block");
		shopService.save(shop.get());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/shops/{id}/activated")
	public ResponseEntity<Void> activatedShop(@PathVariable("id") Integer id) {
		Optional<Shop> shop = shopService.findById(id);
		shop.get().setStatus("activated");
		shopService.save(shop.get());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/shops/{id}/not-activated")
	public ResponseEntity<Void> notActivatedShop(@PathVariable("id") Integer id) {
		Optional<Shop> shop = shopService.findById(id);
		shop.get().setStatus("not-activated");
		shopService.save(shop.get());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
