package br.com.codenation.hospital.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.codenation.hospital.domain.Hospital;
import br.com.codenation.hospital.domain.Location;
import br.com.codenation.hospital.domain.LocationBuilder;
import br.com.codenation.hospital.domain.LocationCategory;
import br.com.codenation.hospital.domain.Patient;
import br.com.codenation.hospital.domain.Product;
import br.com.codenation.hospital.domain.ProductType;
import br.com.codenation.hospital.repository.HospitalRepository;
import br.com.codenation.hospital.repository.LocationRepository;
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
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Override
	public void run(String... args) throws Exception {
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		hospitalRepository.deleteAll(); //deleta todos dados do mongodb
		patientRepository.deleteAll();
		productRepository.deleteAll();
		locationRepository.deleteAll();

		Location locationUm = new LocationBuilder()
				.setReferenceId("Rua dos Sonhos, 123")
				.setLocationCategory(LocationCategory.HOSPITAL)
				.setName("Hospital Um")
				.setLatitude(30D)
				.setLongitude(12D)
				.build();

		Location locationDois = new LocationBuilder()
				.setReferenceId("Rua dos Testes, 202")
				.setLocationCategory(LocationCategory.HOSPITAL)
				.setName("Hospital Dois")
				.setLatitude(30D)
				.setLongitude(12.1)
				.build();

		Location locationTres = new LocationBuilder()
				.setReferenceId("Rua São Paulo, 404")
				.setLocationCategory(LocationCategory.HOSPITAL)
				.setName("Hospital Tres")
				.setLatitude(35D)
				.setLongitude(12D)
				.build();
		
		
		Location locationPatientUm = new LocationBuilder()
				.setReferenceId("Rua dos Sonhos, 15")
				.setLocationCategory(LocationCategory.PATIENT)
				.setName("Maria")
				.setLatitude(30D)
				.setLongitude(12.1D)
				.build();

		Location locationPatientDois = new LocationBuilder()
				.setReferenceId("Rua dos Sonhos, 350")
				.setLocationCategory(LocationCategory.PATIENT)
				.setName("Pedro")
				.setLatitude(30D)
				.setLongitude(12.2D)
				.build();

		Location locationPatientTres = new LocationBuilder()
				.setReferenceId("Rua São Paulo, 305")
				.setLocationCategory(LocationCategory.PATIENT)
				.setName("Joana")
				.setLatitude(30D)
				.setLongitude(12.5D)
				.build();

		locationRepository.saveAll(Arrays.asList(locationUm,locationDois,locationTres)); //adiciona dados
		locationRepository.saveAll(Arrays.asList(locationPatientUm,locationPatientDois,locationPatientTres)); //adiciona dados
		
		Hospital hospitalUm = new Hospital("1", "Hospital Um", "Rua dos Sonhos, 123", 21,5, locationUm);
		Hospital hospitalDois = new Hospital("2", "Hospital Dois", "Rua dos Testes, 202", 11,6, locationDois);
		Hospital hospitalTres = new Hospital("3", "Hospital Tres", "Rua São Paulo, 404", 32,12, locationTres);
		
		hospitalRepository.saveAll(Arrays.asList(hospitalUm,hospitalDois,hospitalTres)); //adiciona dados
		
		Patient pacient1 = new Patient("1", "Maria", "864789205", sdf.parse("16/07/2003"), "feminino", sdf.parse("16/07/2019"));
		Patient pacient2 = new Patient("2", "Pedro", "864789205", sdf.parse("16/07/2003"), "masculino", sdf.parse("16/07/2019"));
		Patient pacient3 = new Patient("3", "Joana", "864789205", sdf.parse("16/07/2003"), "feminino", sdf.parse("16/07/2019"));
		Patient pacient4 = new Patient("4", "Arya", "864789205", sdf.parse("16/07/2003"), "feminino", sdf.parse("16/07/2019"));
		Patient pacient5 = new Patient("5", "João", "864789205", sdf.parse("16/07/2003"), "masculino", sdf.parse("16/07/2019"));
		Patient pacient6 = new Patient("6", "Gabriel", "864789205", sdf.parse("16/07/2003"), "masculino", sdf.parse("16/07/2019"));
		Patient pacient7 = new Patient("7", "Ana", "864789205", sdf.parse("16/07/2003"), "feminino", sdf.parse("16/07/2019"));
		Patient pacient8 = new Patient("8", "Paula", "864789205", sdf.parse("16/07/2003"), "feminino", sdf.parse("16/07/2019"));
		
		patientRepository.saveAll(Arrays.asList(pacient1,pacient2,pacient3,pacient4,pacient5,pacient6,pacient7,pacient8));
		

		Product produto1 = new Product(ObjectId.get(), "Alimento", "Maçã", 12, ProductType.COMMON);
		Product produto2 = new Product(ObjectId.get(), "Alimento", "Arroz", 3, ProductType.COMMON);
		Product produto3 = new Product(ObjectId.get(), "Alimento", "Feijão", 2, ProductType.COMMON);
		Product produto4 = new Product(ObjectId.get(), "Alimento", "Massa", 5, ProductType.COMMON);
		Product produto5 = new Product(ObjectId.get(), "Alimento", "Massa", 5, ProductType.COMMON);
		
		Product produto6 = new Product(ObjectId.get(), "Banco de Sangue", "Sangue", 8, ProductType.BLOOD);
		Product produto7 = new Product(ObjectId.get(), "Banco de Sangue", "Sangue", 1, ProductType.BLOOD);
		Product produto8 = new Product(ObjectId.get(), "Banco de Sangue", "Sangue", 4, ProductType.BLOOD);
		
		productRepository.saveAll(Arrays.asList(produto1,produto2,produto3,produto4,produto5,produto6,produto7,produto8));
		
		//referenciando pacientes e produtos ao hospital
		hospitalUm.getPatients().addAll(Arrays.asList(pacient1,pacient2));
		hospitalTres.getPatients().addAll(Arrays.asList(pacient3,pacient4,pacient5,pacient6,pacient7,pacient8));
		
		hospitalUm.getProducts().addAll(Arrays.asList(produto5,produto6));
		hospitalDois.getProducts().addAll(Arrays.asList(produto1,produto7));
		hospitalTres.getProducts().addAll(Arrays.asList(produto2,produto3,produto4,produto8));

		hospitalRepository.save(hospitalUm);
		hospitalRepository.save(hospitalDois);
		hospitalRepository.save(hospitalTres);

//		MongoCollection<Document> collection = database.getCollection("hospital_collection");
//		collection.createIndex(Indexes.geo2dsphere("location"));
	}
}