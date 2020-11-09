package edu.poly.spring.services;

import java.util.List;
import java.util.Optional;

import edu.poly.spring.models.Posting;

public interface PostingService {

	void deleteAll();

	void deleteAll(Iterable<Posting> entities);

	void delete(Posting entity);

	void deleteById(Integer id);

	long count();

	Iterable<Posting> findAllById(Iterable<Integer> ids);

	Iterable<Posting> findAll();

	boolean existsById(Integer id);

	Optional<Posting> findById(Integer id);

	Iterable<Posting> saveAll(Iterable<Posting> entities);

	Posting save(Posting entity);

	Posting findTopByOrderByIdDesc();

	List<Posting> findPostingsByStatus(String name);

	List<Posting> findPostingsByProductId(Integer id);

}
