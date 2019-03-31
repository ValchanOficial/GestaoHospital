package br.com.codenation.hospital.dto;

import java.io.Serializable;
import br.com.codenation.hospital.domain.Hospital;

public class HospitalDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String hospital_id;
	private String hospitalName;
	private String address;
	private int beds;
	private int availableBeds;
	
	public HospitalDTO() {
		
	}
	
	public HospitalDTO(Hospital obj) {
		this.hospital_id = obj.getId();
		this.hospitalName = obj.getName();
		this.address = obj.getAddress();
		this.beds = obj.getBeds();
		this.availableBeds = obj.getAvailableBeds();
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getBeds() {
		return beds;
	}

	public void setBeds(int beds) {
		this.beds = beds;
	}

	public int getAvailableBeds() {
		return availableBeds;
	}

	public void setAvailableBeds(int availableBeds) {
		this.availableBeds = availableBeds;
	}
}