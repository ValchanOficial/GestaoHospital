package br.com.codenation.hospital.config;

import java.security.cert.CollectionCertStoreParameters;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import br.com.codenation.hospital.domain.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

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

		Location location1 = new Location(52, 49);
		Location location2 = new Location(75, 49);
		Location location3 = new Location(82, 49);

		Hospital hospitalUm = new Hospital("1", "Hospital Um", "Rua dos Sonhos, 123", 21,5, location1);
		Hospital hospitalDois = new Hospital("2", "Hospital Dois", "Rua dos Testes, 202", 11,6, location2);
		Hospital hospitalTres = new Hospital("3", "Hospital Tres", "Rua São Paulo, 404", 32,12, location3);
		
		hospitalRepository.saveAll(Arrays.asList(hospitalUm,hospitalDois,hospitalTres)); //adiciona dados
		
		Patient pacient1 = new Patient("1", "Maria", "864789205", sdf.parse("16/07/2003"), "feminino", sdf.parse("16/07/2019"));
		Patient pacient2 = new Patient("2", "Pedro", "864789205", sdf.parse("16/07/2003"), "masculino", sdf.parse("16/07/2019"));
		Patient pacient3 = new Patient("3", "Joana", "864789205", sdf.parse("16/07/2003"), "feminino", sdf.parse("16/07/2019"));
		
		patientRepository.saveAll(Arrays.asList(pacient1,pacient2,pacient3));
		

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
		hospitalTres.getPatients().addAll(Arrays.asList(pacient3));
		
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