package br.com.codenation.hospital.dto;

import java.io.Serializable;

import br.com.codenation.hospital.domain.Hospital;

public class ProductDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String hospital_id;
	private String hospitalName;
	
	public ProductDTO() {
		
	}
	
	public ProductDTO(Hospital obj) {
		this.hospital_id = obj.getId();
		this.hospitalName = obj.getName();
	}

	public String getHospital_id() {
		return hospital_id;
	}

	public void setHospital_id(String hospital_id) {
		this.hospital_id = hospital_id;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
}