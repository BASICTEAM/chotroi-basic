package edu.poly.spring.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chatboxs")
public class ChatBox {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 500)
	private String message;
	@Column(length = 100)
	private String time;
	@ManyToOne
	@JoinColumn(name = "iduser")
	private User user;
	@ManyToOne
	@JoinColumn(name = "idshop")
	private Shop shop;
	public ChatBox(Integer id, String message, String time, User user, Shop shop) {
		super();
		this.id = id;
		this.message = message;
		this.time = time;
		this.user = user;
		this.shop = shop;
	}
	public ChatBox() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
}
