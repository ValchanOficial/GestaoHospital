package br.com.codenation.hospital.resource;

import br.com.codenation.hospital.domain.Hospital;
import br.com.codenation.hospital.services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codenation.hospital.constant.Constant;
import br.com.codenation.hospital.domain.Product;
import br.com.codenation.hospital.services.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = Constant.V1Path)
public class ProductResource {
	
	@Autowired
	private ProductService service;
	@Autowired
	private HospitalService hospitalService;
	
	@GetMapping(path="/estoque/{produto}", produces="application/json")
	public ResponseEntity<Product> findProductById(@PathVariable("hospital_id") String hospital_id, @PathVariable("produto") String product_id){
		return ResponseEntity.ok().body(service.findById(product_id));
	}

	@GetMapping(path="/{hospital_id}/estoque")
	public ResponseEntity<List<Product>> findByProducts(@PathVariable String hospital_id){
		try {
			Hospital obj = hospitalService.findById(hospital_id);

			if (obj != null) {
				List<Product> productList = obj.getProducts();

				return Optional
						.ofNullable(productList)
						.map(productResponse -> ResponseEntity.ok().body(productResponse))
						.orElseGet( () -> ResponseEntity.notFound().build() );
			}

			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			e.printStackTrace(); // TODO - trocar por logger

			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping(path="/{hospital_id}/estoque/{produto}")
	public ResponseEntity<Product> findByProductById(@PathVariable String hospital_id, @PathVariable String produto){
		try {
			Hospital obj = hospitalService.findById(hospital_id);

			if (obj != null) {
				List<Product> productList = obj.getProducts();
				Product product = null;

				if (productList.size() > 0) {
					product = productList
							.stream()
							.filter(productFilter -> productFilter.getId().trim().equals(produto))
							.findFirst()
							.orElse(null);
				}

				return Optional
						.ofNullable(product)
						.map(productResponse -> ResponseEntity.ok().body(productResponse))
						.orElseGet( () -> ResponseEntity.notFound().build() );
			}

			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			e.printStackTrace(); // TODO - trocar por logger

			return ResponseEntity.notFound().build();
		}
	}
}