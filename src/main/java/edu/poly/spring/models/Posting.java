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
	
	@OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
	private Set<PostingDetail> postings;
	
}
