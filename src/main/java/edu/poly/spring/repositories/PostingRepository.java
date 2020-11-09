package edu.poly.spring.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.poly.spring.models.Posting;

@Repository
public interface PostingRepository extends CrudRepository<Posting, Integer> {
	Posting findTopByOrderByIdDesc();
	List<Posting> findPostingsByStatus(String name);
	List<Posting> findPostingsByProductId(Integer id);
}
