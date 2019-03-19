package br.com.codenation.hospital.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.codenation.hospital.domain.Hospital;

@RestController
@RequestMapping(value="/")
public class HospitalResource {
	
	@GetMapping("/v1/hospitais")
	public ResponseEntity<List<Hospital>> findAll(){
		Hospital hospital1 = new Hospital("1", "Hospital de Olhos", "Rua dos sonhos, 2221", 10);
		Hospital hospital2 = new Hospital("2", "Hospital do Coração", "Rua do Sangue, 2221", 10);
		List<Hospital> list = new ArrayList<>();
		list.addAll(Arrays.asList(hospital1,hospital2));
		return ResponseEntity.ok().body(list);
	}
}