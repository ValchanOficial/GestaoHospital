package br.com.codenation.hospital.repository;

import br.com.codenation.hospital.domain.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationRepository extends MongoRepository<Location, String> {
}
