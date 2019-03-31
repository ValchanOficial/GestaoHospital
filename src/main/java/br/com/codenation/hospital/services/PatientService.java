package br.com.codenation.hospital.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codenation.hospital.domain.Hospital;
import br.com.codenation.hospital.domain.Patient;
import br.com.codenation.hospital.repository.PatientRepository;
import br.com.codenation.hospital.services.exception.ObjectNotFoundException;

@Service
public class PatientService {

	@Autowired
	private  PatientRepository repo;
	
	public Patient findById(String id) {
		Optional<Patient> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Paciente não encontrado!"));
	}
	
	public List<Patient> findByName(String paciente) {
		return repo.findByNameContainingIgnoreCase(paciente);
	}
	
	public List<Patient> findByHospitalId(String hospitalId) {
		return repo.findByHospitalId(hospitalId);
	}
}