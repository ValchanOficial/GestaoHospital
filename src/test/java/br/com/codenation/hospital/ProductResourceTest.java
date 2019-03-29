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

import br.com.codenation.hospital.domain.Product;
import br.com.codenation.hospital.dto.HospitalDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class ProductResourceTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	private final HttpHeaders httpHeaders;

	private ResponseEntity<HospitalDTO> responseHospital;
	private ResponseEntity<Product> responseProduct;
		
	public ProductResourceTest() {
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
		String productJson = "{\"name\": \"Seringa\", \"description\": \"seringa 50 ml\", \"quantity\": \"10\"}";
		
		responseProduct = restTemplate
				.exchange("/v1/produto", 
						HttpMethod.POST, 
						new HttpEntity<>(productJson, httpHeaders), 
						Product.class);
	}
	
	@Test
	public void deveListarTodosProdutosDoHospital() {		
		ResponseEntity<List<Product>> response = restTemplate.exchange(
				  "/v1/hospitais/" + responseHospital.getBody().getId() +"/estoque",
				  HttpMethod.GET,
				  null,
				  new ParameterizedTypeReference<List<Product>>(){});
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		
		System.out.println("deveListarProdutoDoHospital");
		List<Product> products = response.getBody();
		for (Product product : products) {
			System.out.println(product.getId() + ' ' + product.getName());
		}
	}
	
	@Test
	public void deveListarProdutoDoHospital() {		
		ResponseEntity<List<Product>> response = restTemplate.exchange(
				  "/v1/hospitais/" + responseHospital.getBody().getId() +"/estoque/" + responseProduct.getBody().getId(),
				  HttpMethod.GET,
				  null,
				  new ParameterizedTypeReference<List<Product>>(){});
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		
		System.out.println("deveListarProdutoDoHospital");
		List<Product> products = response.getBody();
		for (Product product : products) {
			System.out.println(product.getId() + ' ' + product.getName());
		}
	}
}
