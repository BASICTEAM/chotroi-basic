package edu.poly.spring.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public class PostingDetailDto {
	private int id;
	@NotNull
	@NotEmpty(message = "Tittle is empty")
	@Length(min = 5, max = 50, message = "Title is out of range")
	private String title;
	@NotNull
	@NotEmpty(message = "Content is empty")
	@Length(min = 5, max = 300, message = "Content is out of range")
	private String content;
	@NotNull
	private MultipartFile photo1;
	@NotNull
	private MultipartFile photo2;
	@NotNull
	private MultipartFile photo3;
	@NotNull
	private MultipartFile photo4;
	@NotNull
	private double price;
	private String manufacturer;
	@NotNull
	private Integer postingId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public MultipartFile getPhoto1() {
		return photo1;
	}
	public void setPhoto1(MultipartFile photo1) {
		this.photo1 = photo1;
	}
	public MultipartFile getPhoto2() {
		return photo2;
	}
	public void setPhoto2(MultipartFile photo2) {
		this.photo2 = photo2;
	}
	public MultipartFile getPhoto3() {
		return photo3;
	}
	public void setPhoto3(MultipartFile photo3) {
		this.photo3 = photo3;
	}
	public MultipartFile getPhoto4() {
		return photo4;
	}
	public void setPhoto4(MultipartFile photo4) {
		this.photo4 = photo4;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public Integer getPostingId() {
		return postingId;
	}
	public void setPostingId(Integer postingId) {
		this.postingId = postingId;
	}
	public PostingDetailDto(int id,
			@NotNull @NotEmpty(message = "Tittle is empty") @Length(min = 5, max = 50, message = "Title is out of range") String title,
			@NotNull @NotEmpty(message = "Content is empty") @Length(min = 5, max = 300, message = "Content is out of range") String content,
			@NotNull MultipartFile photo1, @NotNull MultipartFile photo2, @NotNull MultipartFile photo3,
			@NotNull MultipartFile photo4, @NotNull double price, String manufacturer) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.photo1 = photo1;
		this.photo2 = photo2;
		this.photo3 = photo3;
		this.photo4 = photo4;
		this.price = price;
		this.manufacturer = manufacturer;
	}
	public PostingDetailDto(@NotNull Integer postingId) {
		super();
		this.postingId = postingId;
	}
	public PostingDetailDto() {
		super();
	}
	
}