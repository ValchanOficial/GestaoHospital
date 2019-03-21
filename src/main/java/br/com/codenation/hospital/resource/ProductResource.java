package br.com.codenation.hospital.resource;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.codenation.hospital.domain.Product;
import br.com.codenation.hospital.services.ProductService;

@RestController
@RequestMapping(value="/")
public class ProductResource {
	
	@Autowired
	private ProductService service;
	
	@GetMapping("/v1/hospitais/{id}/estoque")
	public ResponseEntity<List<Product>> findAll(){
		List<Product> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/v1/hospitais/{id}/estoque/{produto}")
	public ResponseEntity<List<Product>> findProductById(){
		List<Product> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
}