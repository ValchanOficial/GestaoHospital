package br.com.codenation.hospital.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.codenation.hospital.domain.Patient;
import br.com.codenation.hospital.resource.util.URL;
import br.com.codenation.hospital.services.PatientService;

@RestController
@RequestMapping(path="/")
public class PatientResource {

	@Autowired
	private PatientService service;
	
	@GetMapping("/v1/pacientes/{id}")
	public ResponseEntity<Patient> findPatientById(@PathVariable String id){
		return ResponseEntity.ok().body(service.findById(id));
	}
	
	@GetMapping("/v1/pacientes") //pacientes?paciente=nomeDoPaciente
	public ResponseEntity<List<Patient>> findPatient(@RequestParam(value="paciente", defaultValue="") String paciente){
		return ResponseEntity.ok().body(service.findByName(URL.decodeParam(paciente)));
	}
}