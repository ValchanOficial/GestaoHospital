package br.com.codenation.hospital.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codenation.hospital.constant.Constant;
import br.com.codenation.hospital.domain.Product;
import br.com.codenation.hospital.services.ProductService;

@RestController
@RequestMapping(path = Constant.V1Path)
public class ProductResource {
	
	@Autowired
	private ProductService service;
	
	@GetMapping(path="/estoque/{produto}", produces="application/json")
	public ResponseEntity<Product> findProductById(@PathVariable("hospital_id") String hospital_id, @PathVariable("produto") String product_id){
		return ResponseEntity.ok().body(service.findById(product_id));
	}
}