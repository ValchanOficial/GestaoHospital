package br.com.codenation.hospital.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codenation.hospital.domain.Hospital;
import br.com.codenation.hospital.domain.Patient;
import br.com.codenation.hospital.dto.HospitalDTO;
import br.com.codenation.hospital.repository.HospitalRepository;
import br.com.codenation.hospital.repository.PatientRepository;
import br.com.codenation.hospital.resource.exception.HospitalCheioException;
import br.com.codenation.hospital.services.exception.ObjectNotFoundException;

@Service
public class
HospitalService {

	@Autowired
	private  HospitalRepository repo;

	@Autowired
	private PatientRepository patientRepository;
	
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
		return new Hospital(objDTO.getId(),objDTO.getName(),objDTO.getAddress(),objDTO.getBeds(),objDTO.getAvailableBeds());
	}

	public Patient checkIn(Hospital hospital, Patient patient){
		if(hospital.temVaga()) {
			patient.setEntryDate(new Date());
			patient.setActive(true);
			patient.setExitDate(null);
			patientRepository.save(patient);

			hospital.addPacient(patient);
			repo.save(hospital);

			return patient;
		}
		throw new HospitalCheioException();
	}

	public Patient checkOut(Hospital hospital, String idPatient){
		Patient patient = hospital.getPatients().stream()
				.filter(p -> p.getId().equals(idPatient))
				.findFirst()
				.orElseThrow(() -> new ObjectNotFoundException("Paciente não encontrado no hospital!"));
		hospital.removePacient(patient);
		repo.save(hospital);
		patient.setActive(false);
		patient.setExitDate(new Date());
		return patientRepository.save(patient);
	}

}