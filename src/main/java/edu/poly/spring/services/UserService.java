package edu.poly.spring.services;

import java.util.List;
import java.util.Optional;


import edu.poly.spring.models.User;

public interface UserService {

	void deleteAll();

	void deleteAll(Iterable<User> entities);

	void delete(User entity);

	void deleteById(Integer id);

	long count();

	Iterable<User> findAllById(Iterable<Integer> ids);

	Iterable<User> findAll();

	boolean existsById(Integer id);

	Optional<User> findById(Integer id);

	Iterable<User> saveAll(Iterable<User> entities);

	User save(User entity);

	List<User> findByUsernameLikeOrderByUsername(String name);

	User findByUsername(String username);


}
