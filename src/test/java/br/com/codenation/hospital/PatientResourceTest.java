package br.com.codenation.hospital;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.codenation.hospital.domain.Patient;
import br.com.codenation.hospital.dto.HospitalDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class PatientResourceTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	private final HttpHeaders httpHeaders;

	private ResponseEntity<HospitalDTO> responseHospital;
	private ResponseEntity<Patient> responsePatient;
		
	public PatientResourceTest() {
		httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
	}
	
	@Before
	public void setUp(){
		
		String hospitalJson = "{\"name\": \"Hospital Um\", \"address\": \"Rua dos Sonhos, 1213\", \"beds\": \"21\", \"availableBeds\": \"5\"}";
				
		responseHospital = restTemplate
				.exchange("/v1/hospitais", 
						HttpMethod.POST, 
						new HttpEntity<>(hospitalJson, httpHeaders), 
						HospitalDTO.class);
		
		// TODO falta colocar a referencia ao hospital
		String pacienteJson = "{\"name\": \"João Carlos\", \"cpf\": \"999.999.999-99\", \"birthDate\": \"1980-01-01\", \"gender\": \"M\", \"entryDate\": \"2018-03-30\"}";
		
		responsePatient = restTemplate
				.exchange("/v1/paciente", 
						HttpMethod.POST, 
						new HttpEntity<>(pacienteJson, httpHeaders), 
						Patient.class);
		
	}
	
	@Test
	public void deveListarTodosPacienteDoHospital() {		
		ResponseEntity<List<Patient>> response = restTemplate.exchange(
				  "/v1/hospitais/" + responseHospital.getBody().getId() +"/pacientes",
				  HttpMethod.GET,
				  null,
				  new ParameterizedTypeReference<List<Patient>>(){});
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		
		System.out.println("deveListarTodosPacienteDoHospital");
		List<Patient> pacientes = response.getBody();
		for (Patient patient : pacientes) {
			System.out.println(patient.getId() + ' ' + patient.getName());
		}
	}
	
	@Test
	public void deveListarPacienteDoHospital() {		
		ResponseEntity<List<Patient>> response = restTemplate.exchange(
				  "/v1/hospitais/" + responseHospital.getBody().getId() +"/pacientes/" + responsePatient.getBody().getId(),
				  HttpMethod.GET,
				  null,
				  new ParameterizedTypeReference<List<Patient>>(){});
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		
		System.out.println("deveListarPacienteDoHospital");
		List<Patient> pacientes = response.getBody();
		for (Patient patient : pacientes) {
			System.out.println(patient.getId() + ' ' + patient.getName());
		}
	}
}
