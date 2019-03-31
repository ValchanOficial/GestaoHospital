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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.codenation.hospital.dto.HospitalDTO;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class HospitalResourceTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	private final HttpHeaders httpHeaders;

	private ResponseEntity<HospitalDTO> response;
		
	
	public HospitalResourceTest() {
		httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
	}
	
	@Before
	public void setUp(){
		
		String hospitalJson = "{\"name\": \"Hospital Um\", \"address\": \"Rua dos Sonhos, 1213\", \"beds\": \"21\", \"availableBeds\": \"5\"}";
				
		response = restTemplate
				.exchange("/v1/hospitais", 
						HttpMethod.POST, 
						new HttpEntity<>(hospitalJson, httpHeaders), 
						HospitalDTO.class);
	}
	
	@Test
	public void deveSalvarHospital() {
		String hospitalJson = "{\"name\": \"Hospital Novo\", \"address\": \"Rua dos Novos, 0001\", \"beds\": \"10\", \"availableBeds\": \"9\"}";
		
		ResponseEntity<HospitalDTO> salvarResponse = restTemplate
				.exchange("/v1/hospitais", 
						HttpMethod.POST, 
						new HttpEntity<>(hospitalJson, httpHeaders), 
						HospitalDTO.class);
		
		assertThat(salvarResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void deveAtualizarHospital() {		
		String hospitalJson = "{\"name\": \"Hospital Novo\", \"address\": \"Rua dos Novos, 1000\", \"beds\": \"10\", \"availableBeds\": \"10\"}";
		
		ResponseEntity<Void> atualizarResponse = restTemplate
				.exchange("/v1/hospitais/" + response.getBody().getId(), 
						HttpMethod.PUT, 
						new HttpEntity<>(hospitalJson, httpHeaders), 
						Void.class);
		
		assertThat(atualizarResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void deveRemoverHospital() {		
		ResponseEntity<Void> removerResponse = restTemplate
				.exchange("/v1/hospitais/" + response.getBody().getId(), 
						HttpMethod.DELETE, 
						null, 
						Void.class);
		
		assertThat(removerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void deveListarHospitalPeloId() {		
		ResponseEntity<HospitalDTO> getResponse = restTemplate
				.exchange("/v1/hospitais/" + response.getBody().getId(), 
						HttpMethod.GET, 
						null, 
						HospitalDTO.class);
		
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	
	@Test
	public void deveListarTodosHospitais() {		
		ResponseEntity<List<HospitalDTO>> response = restTemplate.exchange(
				  "/v1/hospitais",
				  HttpMethod.GET,
				  null,
				  new ParameterizedTypeReference<List<HospitalDTO>>(){});
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void naoDeveListarHospital() {		
		ResponseEntity<HospitalDTO> getResponse = restTemplate
				.exchange("/v1/hospitais/0", 
						HttpMethod.GET, 
						null, 
						HospitalDTO.class);
		
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
}
