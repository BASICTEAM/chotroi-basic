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
@Table(name = "postingdetails")
public class PostingDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "nvarchar(150)")
	private String title;
	
	@Column(columnDefinition = "nvarchar(500)")
	private String content;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String picture;
	
	@Column
	private double price;
	
	@ManyToOne
	@JoinColumn(name = "postingId")
	private Posting posting;
	
	@OneToMany(mappedBy = "postingdetail", cascade = CascadeType.ALL)
	private Set<PostingSaved> postingsaveds;	
	
}
