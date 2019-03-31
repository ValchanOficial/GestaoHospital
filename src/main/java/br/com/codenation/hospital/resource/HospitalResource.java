package br.com.codenation.hospital.resource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codenation.hospital.constant.Constant;
import br.com.codenation.hospital.domain.Hospital;
import br.com.codenation.hospital.domain.Patient;
import br.com.codenation.hospital.domain.Product;
import br.com.codenation.hospital.dto.HospitalDTO;
import br.com.codenation.hospital.services.HospitalService;

@CrossOrigin("http://localhost:4200") //permissão para o Angular
@RestController
@RequestMapping(path = Constant.V1)
public class HospitalResource {
	
	@Autowired
	private HospitalService service;
	
	
	@GetMapping()
	public ResponseEntity<List<HospitalDTO>> findAll(){
		try {
			List<Hospital> list = service.findAll();
			List<HospitalDTO> listDTO = list.stream().map(x -> new HospitalDTO(x)).collect(Collectors.toList());
			return ResponseEntity.ok().body(listDTO);
		} catch (Exception e) {
			e.printStackTrace(); // TODO - trocar por logger 
			
	        return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping(path="/{hospital_id}")
	public ResponseEntity<HospitalDTO> findById(@PathVariable String hospital_id){
		Hospital obj = service.findById(hospital_id);
		return ResponseEntity.ok().body(new HospitalDTO(obj));
	}
	
	@PostMapping()
	public ResponseEntity<HospitalDTO> insert(@RequestBody HospitalDTO objDTO){
		try {
			Hospital obj = service.fromDTO(objDTO);
			obj = service.insert(obj);
			
			HospitalDTO hospitalDTO = new HospitalDTO(obj);
			
			return Optional
		            .ofNullable(hospitalDTO)
		            .map(hospitalReponse -> ResponseEntity.ok().body(hospitalReponse))          
		            .orElseGet( () -> ResponseEntity.notFound().build() ); 
		}
		catch (Exception e) {
			e.printStackTrace(); // TODO - trocar por logger 
			
	        return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping(path="/{hospital_id}")
	public ResponseEntity<String> deleteById(@PathVariable String hospital_id){
		service.delete(hospital_id);
		return ResponseEntity.ok().body("Hospital apagado: " + hospital_id);
	}
	
	@PutMapping(path="/{hospital_id}")
	public ResponseEntity<HospitalDTO> update(@RequestBody HospitalDTO objDTO, @PathVariable String hospital_id){
		Hospital obj = service.fromDTO(objDTO);
		obj.setId(hospital_id);
		obj = service.update(obj);
		return ResponseEntity.ok().body(new HospitalDTO(obj));
	}
	
	@GetMapping(path="/{hospital_id}/pacientes")
	public ResponseEntity<List<Patient>> findByPatients(@PathVariable String hospital_id){
		Hospital obj = service.findById(hospital_id);
		return ResponseEntity.ok().body(obj.getPatients());
	}
	
	@GetMapping(path="/{hospital_id}/estoque")
	public ResponseEntity<List<Product>> findByProducts(@PathVariable String hospital_id){
		Hospital obj = service.findById(hospital_id);
		return ResponseEntity.ok().body(obj.getProducts());
	}
	
	@GetMapping("/v1/hospitais/{id}/estoque/{produto}")
	public ResponseEntity<Product> findByProductById(@PathVariable String id, @PathVariable String produto){
		try {
			Hospital obj = service.findById(id);
			
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
			            .map(productReponse -> ResponseEntity.ok().body(productReponse))          
			            .orElseGet( () -> ResponseEntity.notFound().build() ); 
			}
			 
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			e.printStackTrace(); // TODO - trocar por logger 
			
			return ResponseEntity.notFound().build();
		}
	}
}