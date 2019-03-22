package br.com.codenation.hospital.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.codenation.hospital.domain.Hospital;
import br.com.codenation.hospital.domain.Patient;
import br.com.codenation.hospital.repository.HospitalRepository;
import br.com.codenation.hospital.repository.PatientRepository;

//Operação de instanciação da base de dados

@Configuration
public class Instantiation implements CommandLineRunner{

	@Autowired
	private HospitalRepository hospitalRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		hospitalRepository.deleteAll(); //deleta todos dados do mongodb
		patientRepository.deleteAll();
		
		Hospital hospitalUm = new Hospital("1", "Hospital Um", "Rua dos Sonhos, 1213", 21,5);
		Hospital hospitalDois = new Hospital("2", "Hospital Dois", "Rua dos Sonhos, 1213", 11,6);
		Hospital hospitalTres = new Hospital("3", "Hospital Tres", "Rua dos Sonhos, 1213", 32,12);
		
		Patient pacient1 = new Patient(null, "Maria", "864789205", sdf.parse("16/07/2003"), "feminino", sdf.parse("16/07/2019"),hospitalUm);
		Patient pacient2 = new Patient(null, "Pedro", "864789205", sdf.parse("16/07/2003"), "masculino", sdf.parse("16/07/2019"),hospitalUm);
		Patient pacient3 = new Patient(null, "Joana", "864789205", sdf.parse("16/07/2003"), "feminino", sdf.parse("16/07/2019"),hospitalDois);
		
		hospitalRepository.saveAll(Arrays.asList(hospitalUm,hospitalDois,hospitalTres)); //adiciona dados
		patientRepository.saveAll(Arrays.asList(pacient1,pacient2,pacient3));
	}
}