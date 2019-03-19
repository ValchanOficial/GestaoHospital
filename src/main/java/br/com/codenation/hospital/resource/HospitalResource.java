package br.com.codenation.hospital.resource;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.codenation.hospital.domain.Hospital;
import br.com.codenation.hospital.services.HospitalService;

@RestController
@RequestMapping(value="/")
public class HospitalResource {
	
	@Autowired
	private HospitalService service;
	
	@GetMapping("/v1/hospitais")
	public ResponseEntity<List<Hospital>> findAll(){
		List<Hospital> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
}