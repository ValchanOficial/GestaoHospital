package br.com.codenation.hospital;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.codenation.hospital.domain.Hospital;
import br.com.codenation.hospital.domain.Patient;
import br.com.codenation.hospital.services.HospitalService;
import br.com.codenation.hospital.services.PatientService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class PatientResourceTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private HospitalService hospitalService;
	
	private final HttpHeaders httpHeaders;
	private Hospital hospitalTest;
	private Patient patientTest;
	
	public PatientResourceTest() {
		httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

	}
	
	@Before
	public void setUp(){
		hospitalTest = hospitalService.findById("1");
		
		if (hospitalTest != null) {
			List<Patient> patientList = hospitalTest.getPatients();
					
			if (patientList.size() > 0) {
				hospitalTest.setPatients(patientList);
				Patient patient = patientList.get(0);	
				
				patientTest = new Patient(patient.getId(), patient.getName(), patient.getCpf(), patient.getBirthDate(), patient.getGender(), patient.getEntryDate(), patient.getHospital());
			}
		}
	}
	
	@Test
	public void deveListarTodosPacienteDoHospital() {		
		ResponseEntity<List<Patient>> response = restTemplate.exchange(
				  "/v1/hospitais/" + hospitalTest.getId() +"/pacientes",
				  HttpMethod.GET,
				  null,
				  new ParameterizedTypeReference<List<Patient>>(){});
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void deveListarPacienteDoHospital() {		
		ResponseEntity<Patient> response = restTemplate.exchange(
				  "/v1/hospitais/" + hospitalTest.getId() +"/pacientes/" + patientTest.getId(),
				  HttpMethod.GET,
				  null,
				  new ParameterizedTypeReference<Patient>(){});
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void naoDeveListarPacienteDoHospital() {		
		ResponseEntity<Patient> response = restTemplate.exchange(
				  "/v1/hospitais/" + hospitalTest.getId() +"/pacientes/0",
				  HttpMethod.GET,
				  null,
				  new ParameterizedTypeReference<Patient>(){});
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
}
