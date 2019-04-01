package br.com.codenation.hospital.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codenation.hospital.constant.Constant;
import br.com.codenation.hospital.domain.Hospital;
import br.com.codenation.hospital.domain.Patient;
import br.com.codenation.hospital.services.PatientService;
import br.com.codenation.hospital.services.HospitalService;

@RestController
@RequestMapping(path = Constant.V1Path)
public class PatientResource {

	@Autowired
	private PatientService service;
	
	@Autowired
	private HospitalService hospitalService;
	
	@GetMapping(path="/pacientes/{paciente}", produces="application/json")
	public ResponseEntity<Patient> findPatientById(@PathVariable("hospital_id") String hospital_id, @PathVariable("paciente") String patient_id){
		return ResponseEntity.ok().body(service.findById(patient_id));
	}
	
	@PutMapping(path="/pacientes/{paciente}", produces="application/json")
	public ResponseEntity<Patient> checkIn(@PathVariable("hospital_id") String hospital_id, @PathVariable("paciente") String patient_id){
		Hospital obj = hospitalService.findById(hospital_id);
		obj.setAvailableBeds(obj.getAvailableBeds()-1);
		List<Patient> patients = obj.getPatients();
		Patient patient = service.findById(patient_id);
		return ResponseEntity.ok().body(service.findById(patient_id));
	}
}