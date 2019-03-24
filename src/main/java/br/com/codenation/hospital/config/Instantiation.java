package br.com.codenation.hospital.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import br.com.codenation.hospital.domain.Hospital;
import br.com.codenation.hospital.domain.Patient;
import br.com.codenation.hospital.domain.Product;
import br.com.codenation.hospital.dto.PatientDTO;
import br.com.codenation.hospital.dto.ProductDTO;
import br.com.codenation.hospital.repository.HospitalRepository;
import br.com.codenation.hospital.repository.PatientRepository;
import br.com.codenation.hospital.repository.ProductRepository;

//Operação de instanciação da base de dados

@Configuration
public class Instantiation implements CommandLineRunner{

	@Autowired
	private HospitalRepository hospitalRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		hospitalRepository.deleteAll(); //deleta todos dados do mongodb
		patientRepository.deleteAll();
		productRepository.deleteAll();
		
		Hospital hospitalUm = new Hospital(null, "Hospital Um", "Rua dos Sonhos, 1213", 21,5);
		Hospital hospitalDois = new Hospital(null, "Hospital Dois", "Rua dos Sonhos, 1213", 11,6);
		Hospital hospitalTres = new Hospital(null, "Hospital Tres", "Rua dos Sonhos, 1213", 32,12);
		
		hospitalRepository.saveAll(Arrays.asList(hospitalUm,hospitalDois,hospitalTres)); //adiciona dados
		
		Patient pacient1 = new Patient(null, "Maria", "864789205", sdf.parse("16/07/2003"), "feminino", sdf.parse("16/07/2019"),new PatientDTO(hospitalUm));
		Patient pacient2 = new Patient(null, "Pedro", "864789205", sdf.parse("16/07/2003"), "masculino", sdf.parse("16/07/2019"),new PatientDTO(hospitalUm));
		Patient pacient3 = new Patient(null, "Joana", "864789205", sdf.parse("16/07/2003"), "feminino", sdf.parse("16/07/2019"),new PatientDTO(hospitalTres));
		
		patientRepository.saveAll(Arrays.asList(pacient1,pacient2,pacient3));
		
		Product produto1 = new Product(null, "Alimento", "Maçã", 12, new ProductDTO(hospitalDois));
		Product produto2 = new Product(null, "Alimento", "Arroz", 3, new ProductDTO(hospitalTres));
		Product produto3 = new Product(null, "Alimento", "Feijão", 2, new ProductDTO(hospitalTres));
		Product produto4 = new Product(null, "Alimento", "Massa", 5, new ProductDTO(hospitalTres));
		
		productRepository.saveAll(Arrays.asList(produto1,produto2,produto3,produto4));
		
		//referenciando pacientes e produtos ao hospital
		hospitalUm.getPatients().addAll(Arrays.asList(pacient1,pacient2));
		hospitalTres.getPatients().addAll(Arrays.asList(pacient3));
		
		hospitalTres.getProducts().addAll(Arrays.asList(produto2,produto3,produto4));
		hospitalDois.getProducts().addAll(Arrays.asList(produto1));
		
		hospitalRepository.save(hospitalUm);
		hospitalRepository.save(hospitalDois);
		hospitalRepository.save(hospitalTres);
	}
}