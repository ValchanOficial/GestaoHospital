package br.com.codenation.hospital.resource;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.codenation.hospital.domain.Patient;
import br.com.codenation.hospital.services.PatientService;

@RestController
@RequestMapping(value="/")
public class PatientResource {

	@Autowired
	private PatientService service;
	
	@GetMapping("/v1/hospitais/{id}/pacientes/{paciente}")
	public ResponseEntity<List<Patient>> findPatientById(){
		List<Patient> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
}