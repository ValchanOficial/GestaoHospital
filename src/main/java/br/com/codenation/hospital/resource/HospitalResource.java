package br.com.codenation.hospital.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import br.com.codenation.hospital.dto.HospitalDTO;
import br.com.codenation.hospital.resource.exception.ResourceNotFoundException;
import br.com.codenation.hospital.services.HospitalService;

@CrossOrigin("http://localhost:4200") // permissão para o Angular
@RestController
@RequestMapping(path = Constant.V1)
public class HospitalResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductResource.class);

	@Autowired
	private HospitalService service;

	@GetMapping()
	public ResponseEntity<List<HospitalDTO>> findAll() {
		try {
			List<Hospital> list = service.findAll();
			List<HospitalDTO> listDTO = list.stream().map(x -> new HospitalDTO(x)).collect(Collectors.toList());
			return ResponseEntity.ok().body(listDTO);
		} catch (Exception e) {
			LOGGER.error("findAllHospital - Error with message: {}", e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping(path = "/{hospital_id}")
	public ResponseEntity<HospitalDTO> findById(@PathVariable String hospital_id) {
		try {
			Hospital obj = service.findById(hospital_id);

			HospitalDTO hospitalDTO = new HospitalDTO(obj);

			return Optional.ofNullable(hospitalDTO).map(hospitalResponse -> ResponseEntity.ok().body(hospitalResponse))
					.orElseGet(() -> ResponseEntity.notFound().build());
		} catch (Exception e) {
			LOGGER.error("findHospitalById - Error with message: {}", e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping()
	public ResponseEntity<HospitalDTO> insert(@RequestBody HospitalDTO objDTO) {
		try {
			Hospital obj = service.fromDTO(objDTO);
			if(obj.getName().equals("") || obj.getAddress().equals("") || obj.getBeds()<0 || obj.getAvailableBeds()<0) {
				throw new ResourceNotFoundException("Preencha os campos corretamente!"); //precisa tratar erro
			}
			obj = service.insert(obj);
			HospitalDTO hospitalDTO = new HospitalDTO(obj);
			return Optional.ofNullable(hospitalDTO).map(hospitalResponse -> ResponseEntity.ok().body(hospitalResponse))
					.orElseGet(() -> ResponseEntity.notFound().build());
		} catch (Exception e) {
			LOGGER.error("insertHospital - Handling error with message: {}", e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping(path = "/{hospital_id}")
	public ResponseEntity<String> deleteById(@PathVariable String hospital_id) {
		try {
			Hospital obj = service.findById(hospital_id);
			if (obj != null) {
				service.delete(hospital_id);
			}
			return Optional.ofNullable(obj)
					.map(hospitalResponse -> ResponseEntity.ok().body("Hospital apagado id: " + hospital_id))
					.orElseGet(() -> ResponseEntity.notFound().build());
		} catch (Exception e) {
			LOGGER.error("deleteHospitalById - Handling error with message: {}", e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<HospitalDTO> update(@RequestBody HospitalDTO objDTO, @PathVariable String hospital_id) {
		try {
			Hospital obj = service.fromDTO(objDTO);
			obj.setId(hospital_id);
			if(obj.getName().equals("") || obj.getAddress().equals("") || obj.getBeds()<0 || obj.getAvailableBeds()<0) {
				throw new ResourceNotFoundException("Preencha os campos corretamente!"); //precisa tratar erro
			}
			obj = service.update(obj);
			HospitalDTO hospitalDTO = new HospitalDTO(obj);
			return Optional.ofNullable(hospitalDTO).map(hospitalResponse -> ResponseEntity.ok().body(hospitalResponse))
					.orElseGet(() -> ResponseEntity.notFound().build());
		} catch (Exception e) {
			LOGGER.error("updateHospital - Handling error with message: {}", e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping(path = "/{id}/leitos")
	public Map<String, Integer> vefificaLeitosDisponiveis(@PathVariable String id) {
		Hospital hospital = service.findById(id);
		Map<String, Integer> leitos = new HashMap<>();
		leitos.put("leitos", hospital.getAvailableBeds());
		return leitos;
	}
}