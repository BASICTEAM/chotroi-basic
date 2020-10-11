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
@Table(name = "rates")
public class Rate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "nvarchar(500)")
	private String reason;
	
	@Column
	private Integer type;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "shopId")
	private Shop shop;
	
}
