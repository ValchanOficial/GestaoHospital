package br.com.codenation.hospital.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.codenation.hospital.domain.Product;
import br.com.codenation.hospital.resource.util.URL;
import br.com.codenation.hospital.services.ProductService;

@RestController
@RequestMapping(path="/")
public class ProductResource {
	
	@Autowired
	private ProductService service;
	
	@GetMapping("/estoque/{id}")
	public ResponseEntity<Product> findProductById(@PathVariable String id){
		return ResponseEntity.ok().body(service.findById(id));
	}
	
	@GetMapping("/estoque") //estoque?produto=nomeDoProduto
	public ResponseEntity<List<Product>> findProduct(@RequestParam(value="produto", defaultValue="") String description){
		return ResponseEntity.ok().body(service.findByProductDescription(URL.decodeParam(description)));
	}
}