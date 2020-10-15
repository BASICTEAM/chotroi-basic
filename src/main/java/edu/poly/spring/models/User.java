package edu.poly.spring.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "nvarchar(50)")
	private String username;

	@Column(length = 50)
	private String password;

	@Column(length = 11)
	private String phone;

	@Column(length = 50)
	private String email;

	@Column(length = 50)
	private String picture;

	@Column(columnDefinition = "nvarchar(100)")
	private String fullname;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date birthday;

	@Column(columnDefinition = "nvarchar(500)")
	private String address;

	@Column(length = 20)
	private String status;
	
	@Column
	private boolean role;
	
	@Column
	private boolean gender;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Posting> postings;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<PostingSaved> postingsaveds;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Rate> rates;
	
	public User() {
		super();
	}

	public User(Integer id, String username, String password, String phone, String email, String picture,
			String fullname, Date birthday, String address, String status, boolean role, boolean gender,
			Set<Posting> postings, Set<PostingSaved> postingsaveds, Set<Rate> rates) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.picture = picture;
		this.fullname = fullname;
		this.birthday = birthday;
		this.address = address;
		this.status = status;
		this.role = role;
		this.gender = gender;
		this.postings = postings;
		this.postingsaveds = postingsaveds;
		this.rates = rates;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isRole() {
		return role;
	}

	public void setRole(boolean role) {
		this.role = role;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public Set<Posting> getPostings() {
		return postings;
	}

	public void setPostings(Set<Posting> postings) {
		this.postings = postings;
	}

	public Set<PostingSaved> getPostingsaveds() {
		return postingsaveds;
	}

	public void setPostingsaveds(Set<PostingSaved> postingsaveds) {
		this.postingsaveds = postingsaveds;
	}

	public Set<Rate> getRates() {
		return rates;
	}

	public void setRates(Set<Rate> rates) {
		this.rates = rates;
	}
}