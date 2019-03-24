package br.com.codenation.hospital.dto;

import java.io.Serializable;

import br.com.codenation.hospital.domain.Hospital;

public class ProductDTO implements Serializable{
	private static final long serialVersionUID = -2994099982968035173L;
	
	private String id;
	private String name;
	
	public ProductDTO() {
		
	}
	
	public ProductDTO(Hospital obj) {
		this.id = obj.getId();
		this.name = obj.getName();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}	
}