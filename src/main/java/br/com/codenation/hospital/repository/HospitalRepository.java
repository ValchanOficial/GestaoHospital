package br.com.codenation.hospital.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import br.com.codenation.hospital.domain.Hospital;

@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String>{

	
}