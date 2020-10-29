package edu.poly.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.poly.spring.models.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>{
	Product findByName(String name);
	
}
