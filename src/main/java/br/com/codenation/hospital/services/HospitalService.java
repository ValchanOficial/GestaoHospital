package br.com.codenation.hospital.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codenation.hospital.domain.Hospital;
import br.com.codenation.hospital.dto.HospitalDTO;
import br.com.codenation.hospital.repository.HospitalRepository;
import br.com.codenation.hospital.services.exception.ObjectNotFoundException;

@Service
public class HospitalService {

	@Autowired
	private  HospitalRepository repo;
	
	public List<Hospital> findAll(){
		return repo.findAll();
	}
	

	public Hospital findById(String hospital_id) {
		Optional<Hospital> obj = repo.findById(hospital_id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Hospital não encontrado! ID:"+ hospital_id));	
	}
	
	
	
	public Hospital insert(Hospital obj) {
		return repo.insert(obj);
	}
	
	public void delete(String hospital_id) {
		findById(hospital_id);
		repo.deleteById(hospital_id);
	}
	
	public Hospital update(Hospital obj) {
		Hospital newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	private void updateData(Hospital newObj, Hospital obj) {
		newObj.setName(obj.getName());
		newObj.setAddress(obj.getAddress());
		newObj.setBeds(obj.getBeds());
		newObj.setAvailableBeds(obj.getAvailableBeds());
	}

	public Hospital fromDTO(HospitalDTO objDTO) {
		return new Hospital(objDTO.getHospital_id(),objDTO.getHospitalName(),objDTO.getAddress(),objDTO.getBeds(),objDTO.getAvailableBeds());
	}
}