package br.com.codenation.hospital.dto;

import java.io.Serializable;
import br.com.codenation.hospital.domain.Hospital;

public class PatientDTO implements Serializable {
	private static final long serialVersionUID = 2495409413110323196L;
	
	private String id;
	private String name;
	
	public PatientDTO() {
		
	}
	
	public PatientDTO(Hospital obj) {
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