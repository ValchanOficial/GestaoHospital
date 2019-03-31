package br.com.codenation.hospital.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codenation.hospital.domain.Product;
import br.com.codenation.hospital.repository.ProductRepository;
import br.com.codenation.hospital.services.exception.ObjectNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private  ProductRepository repo;
	
	public Product findById(String product_id) {
		Optional<Product> obj = repo.findById(product_id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado!"));
	}	
}