package br.com.codenation.hospital.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.codenation.hospital.domain.Patient;
import br.com.codenation.hospital.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	private  PatientRepository repo;
	
	public List<Patient> findAll(){
		return repo.findAll();
	}
}