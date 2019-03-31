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
		List<Hospital> hospitals = repo.findAll();
		for(int i = 0; i < hospitals.size(); i++) {
			
			hospitals.get(i).setAvailableBeds(hospitals.get(i).getBeds() - hospitals.get(i).getPatients().size());
		}
		return hospitals;
	}
	
	public Hospital findById(String id) {
		Optional<Hospital> obj = repo.findById(id);
		//repo.
		if(obj.isPresent()) {
			
			obj.get().setAvailableBeds(obj.get().getBeds() - obj.get().getPatients().size());
		}
		return obj.orElseThrow(() -> new ObjectNotFoundException("Hospital não encontrado!"));
	}
	
	
	
	public Hospital insert(Hospital obj) {
		return repo.insert(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
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
		return new Hospital(objDTO.getId(),objDTO.getName(),objDTO.getAddress(),objDTO.getBeds(),objDTO.getAvailableBeds());
	}
}