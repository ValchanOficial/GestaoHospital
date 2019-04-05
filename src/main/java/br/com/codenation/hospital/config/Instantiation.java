package br.com.codenation.hospital.config;

import java.text.DateFormat;
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
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		hospitalRepository.deleteAll(); //deleta todos dados do mongodb
		patientRepository.deleteAll();
		productRepository.deleteAll();
		
		Hospital hospitalUm = new Hospital("1", "Hospital Um", "Rua dos Sonhos, 123", 21,5);
		Hospital hospitalDois = new Hospital("2", "Hospital Dois", "Rua dos Testes, 202", 11,6);
		Hospital hospitalTres = new Hospital("3", "Hospital Tres", "Rua São Paulo, 404", 32,12);
		
		hospitalRepository.saveAll(Arrays.asList(hospitalUm,hospitalDois,hospitalTres)); //adiciona dados
		
		Patient pacient1 = new Patient("1", "Maria", "864789205", sdf.parse("16/07/2003"), "feminino", sdf.parse("16/07/2019"), hospitalUm);
		Patient pacient2 = new Patient("2", "Pedro", "864789205", sdf.parse("16/07/2003"), "masculino", sdf.parse("16/07/2019"), hospitalUm);
		Patient pacient3 = new Patient("3", "Joana", "864789205", sdf.parse("16/07/2003"), "feminino", sdf.parse("16/07/2019"), hospitalTres);
		
		patientRepository.saveAll(Arrays.asList(pacient1,pacient2,pacient3));
		
		Product produto1 = new Product("1", "Alimento", "Maçã", 12, new ProductDTO(hospitalDois));
		Product produto2 = new Product("2", "Alimento", "Arroz", 3, new ProductDTO(hospitalTres));
		Product produto3 = new Product("3", "Alimento", "Feijão", 2, new ProductDTO(hospitalTres));
		Product produto4 = new Product("4", "Alimento", "Massa", 5, new ProductDTO(hospitalTres));
		Product produto5 = new Product("5", "Alimento", "Massa", 5, new ProductDTO(hospitalUm));
		
		Product produto6 = new Product("6", "Banco de Sangue", "Sangue", 8, new ProductDTO(hospitalUm));
		Product produto7 = new Product("7", "Banco de Sangue", "Sangue", 1, new ProductDTO(hospitalDois));
		Product produto8 = new Product("8", "Banco de Sangue", "Sangue", 4, new ProductDTO(hospitalTres));
		
		productRepository.saveAll(Arrays.asList(produto1,produto2,produto3,produto4,produto5,produto6,produto7,produto8));
		
		//referenciando pacientes e produtos ao hospital
		hospitalUm.getPatients().addAll(Arrays.asList(pacient1,pacient2));
		hospitalTres.getPatients().addAll(Arrays.asList(pacient3));
		
		hospitalUm.getProducts().addAll(Arrays.asList(produto5,produto6));
		hospitalDois.getProducts().addAll(Arrays.asList(produto1,produto7));
		hospitalTres.getProducts().addAll(Arrays.asList(produto2,produto3,produto4,produto8));
		
		
		hospitalRepository.save(hospitalUm);
		hospitalRepository.save(hospitalDois);
		hospitalRepository.save(hospitalTres);
	}
}