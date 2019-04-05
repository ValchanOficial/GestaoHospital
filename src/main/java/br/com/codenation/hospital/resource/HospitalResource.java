package br.com.codenation.hospital.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
		try {
			Hospital obj = service.findById(hospital_id);
			
			HospitalDTO hospitalDTO = new HospitalDTO(obj);
			
			return Optional
		            .ofNullable(hospitalDTO)
		            .map(hospitalResponse -> ResponseEntity.ok().body(hospitalResponse))
		            .orElseGet( () -> ResponseEntity.notFound().build() ); 
		} catch (Exception e) {
			e.printStackTrace(); // TODO - trocar por logger 
			
	        return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping()
	public ResponseEntity<HospitalDTO> insert(@RequestBody HospitalDTO objDTO){
		try {
			Hospital obj = service.fromDTO(objDTO);
			obj = service.insert(obj);
			
			HospitalDTO hospitalDTO = new HospitalDTO(obj);
			
			return Optional
		            .ofNullable(hospitalDTO)
		            .map(hospitalResponse -> ResponseEntity.ok().body(hospitalResponse))
		            .orElseGet( () -> ResponseEntity.notFound().build() ); 
		}
		catch (Exception e) {
			e.printStackTrace(); // TODO - trocar por logger 
			
	        return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping(path="/{hospital_id}")
	public ResponseEntity<String> deleteById(@PathVariable String hospital_id){
		try {
			Hospital obj = service.findById(hospital_id);
			
			if (obj != null) {
				service.delete(hospital_id);
			}
			
			return Optional
		            .ofNullable(obj)
		            .map(hospitalResponse -> ResponseEntity.ok().body("Hospital apagado id: " + hospital_id))
		            .orElseGet( () -> ResponseEntity.notFound().build() ); 
		} catch (Exception e) {
			e.printStackTrace(); // TODO - trocar por logger 
			
	        return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping(path="/{id}")
	public ResponseEntity<HospitalDTO> update(@RequestBody HospitalDTO objDTO, @PathVariable String hospital_id){
		try {
			Hospital obj = service.fromDTO(objDTO);
			obj.setId(hospital_id);
			obj = service.update(obj);
			
			HospitalDTO hospitalDTO = new HospitalDTO(obj);
			
			return Optional
		            .ofNullable(hospitalDTO)
		            .map(hospitalResponse -> ResponseEntity.ok().body(hospitalResponse))
		            .orElseGet( () -> ResponseEntity.notFound().build() ); 
		} catch (Exception e) {
			e.printStackTrace(); // TODO - trocar por logger 
			
	        return ResponseEntity.badRequest().build();
		}
	}


	@GetMapping(path = "/{id}/leitos")
	public Map<String, Integer> vefificaLeitosDisponiveis(@PathVariable String id){
		Hospital hospital = service.findById(id);
		Map<String, Integer> leitos = new HashMap<>();
		leitos.put("leitos", hospital.getAvailableBeds());
		return leitos;
	}

	
	/*
	@GetMapping(path="/{hospital_id}/pacientes")
	public ResponseEntity<List<Patient>> findByPatients(@PathVariable String hospital_id){
		try {
			Hospital obj = service.findById(hospital_id);
			
			if (obj != null) {
				List<Patient> patientList = obj.getPatients();
	
				 return Optional
			            .ofNullable(patientList)
			            .map(patientResponse -> ResponseEntity.ok().body(patientResponse))
			            .orElseGet( () -> ResponseEntity.notFound().build() ); 
			} 
			
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			e.printStackTrace(); // TODO - trocar por logger 
			
	        return ResponseEntity.badRequest().build();
		}
	}
	
	
	
	@GetMapping(path="/{hospital_id}/pacientes/{paciente}")
	public ResponseEntity<Patient> findByPatientsById(@PathVariable String hospital_id, @PathVariable String paciente){
		try {
			Hospital obj = service.findById(hospital_id);
			
			if (obj != null) {
				List<Patient> patientList = obj.getPatients();
				Patient patient = null;
				
				if (patientList.size() > 0) {
					patient = patientList
							.stream()
							.filter(patientFilter -> patientFilter.getId().trim().equals(paciente))
			                .findFirst()
			                .orElse(null);
				}					
				
				 return Optional
			            .ofNullable(patient)
			            .map(patientResponse -> ResponseEntity.ok().body(patientResponse))
			            .orElseGet( () -> ResponseEntity.notFound().build() ); 
			} 
			
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			e.printStackTrace(); // TODO - trocar por logger 
			
			return ResponseEntity.notFound().build();
		}
	}
	
	
	@GetMapping(path="/{hospital_id}/estoque/{produto}")
	public ResponseEntity<Product> findByProductById(@PathVariable String hospital_id, @PathVariable String produto){
		try {
			Hospital obj = service.findById(hospital_id);
			
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
	}*/
}