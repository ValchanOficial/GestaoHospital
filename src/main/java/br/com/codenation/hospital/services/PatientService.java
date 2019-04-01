package br.com.codenation.hospital.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codenation.hospital.domain.Hospital;
import br.com.codenation.hospital.domain.Patient;
import br.com.codenation.hospital.dto.HospitalDTO;
import br.com.codenation.hospital.dto.PatientDTO;
import br.com.codenation.hospital.repository.PatientRepository;
import br.com.codenation.hospital.services.exception.ObjectNotFoundException;

@Service
public class PatientService {

	@Autowired
	private  PatientRepository repo;
	
	public Patient findById(String patient_id) {
		Optional<Patient> obj = repo.findById(patient_id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Paciente não encontrado!"));
	}
	public Patient update(Patient obj) {
		return repo.save(obj);
	}
}