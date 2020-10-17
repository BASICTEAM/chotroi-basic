package edu.poly.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.poly.spring.models.PostingDetail;

@Repository
public interface PostingDetailRepository extends CrudRepository<PostingDetail, Integer> {

}
