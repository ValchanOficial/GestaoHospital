package br.com.codenation.hospital.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.codenation.hospital.domain.Hospital;
import br.com.codenation.hospital.repository.HospitalRepository;

@Service
public class HospitalService {

	@Autowired
	private  HospitalRepository repo;
	
	public List<Hospital> findAll(){
		return repo.findAll();
	}
}