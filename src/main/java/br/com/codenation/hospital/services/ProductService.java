package br.com.codenation.hospital.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.codenation.hospital.domain.Product;
import br.com.codenation.hospital.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private  ProductRepository repo;
	
	public List<Product> findAll(){
		return repo.findAll();
	}
}