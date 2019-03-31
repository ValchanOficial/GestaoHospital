package br.com.codenation.hospital.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.codenation.hospital.domain.Patient;
import br.com.codenation.hospital.domain.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{

	List<Product> findByNameContainingIgnoreCase(String product);
	
	@Query("{ 'produto' : { $regex: ?0, $options: 'i' } }")
	List<Product> searchProductByDescription(String description);
	
	List<Product> findByHospitalId(String hospitalId);
}