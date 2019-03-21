package br.com.codenation.hospital.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.codenation.hospital.domain.Hospital;
import br.com.codenation.hospital.repository.HospitalRepository;
import br.com.codenation.hospital.services.exception.ObjectNotFoundException;

@Service
public class HospitalService {

	@Autowired
	private  HospitalRepository repo;
	
	public List<Hospital> findAll(){
		return repo.findAll();
	}
	
	public Hospital findById(String id) {
		Optional<Hospital> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Hospital não encontrado!"));
	}
}