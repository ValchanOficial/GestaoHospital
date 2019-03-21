package br.com.codenation.hospital.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import br.com.codenation.hospital.domain.Hospital;
import br.com.codenation.hospital.repository.HospitalRepository;

//Operação de instanciação da base de dados

@Configuration
public class Instantiation implements CommandLineRunner{

	@Autowired
	private HospitalRepository hospitalRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		hospitalRepository.deleteAll(); //deleta todos dados do mongodb
		
		Hospital hospitalUm = new Hospital("1", "Hospital Um", "Rua dos Sonhos, 1213", 21,5);
		Hospital hospitalDois = new Hospital("2", "Hospital Dois", "Rua dos Sonhos, 1213", 11,6);
		Hospital hospitalTres = new Hospital("3", "Hospital Tres", "Rua dos Sonhos, 1213", 32,12);
		
		hospitalRepository.saveAll(Arrays.asList(hospitalUm,hospitalDois,hospitalTres)); //adiciona dados
	}
}