package edu.poly.spring.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "postings")
public class Posting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;
	
	@Column(length = 100)
	private boolean type;
	
	@ManyToOne
	@JoinColumn(name = "id", insertable=false, updatable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "id", insertable=false, updatable=false)
	private Shop shop;
	
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;
	
	@OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
	private Set<PostingDetail> postings;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Set<PostingDetail> getPostings() {
		return postings;
	}

	public void setPostings(Set<PostingDetail> postings) {
		this.postings = postings;
	}

	public Posting(Integer id, boolean type, User user, Shop shop, Product product, Set<PostingDetail> postings) {
		super();
		this.id = id;
		this.type = type;
		this.user = user;
		this.shop = shop;
		this.product = product;
		this.postings = postings;
	}

	public Posting() {
		super();
	}
	
}
