package br.com.codenation.hospital.domain;

import java.io.Serializable;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.codenation.hospital.dto.PatientDTO;

@Document(collection="patient_collection")
public class Patient implements Serializable{
	private static final long serialVersionUID = 3741196581009802558L;
	
	@Id
	private String id;
	private String name;
	private String cpf;
	private Date birthDate;
	private String gender;
	private Date entryDate;
	private PatientDTO hospital;
	
	public Patient() {
		
	}
	
	public Patient(String id, String name, String cpf, Date birthDate, String gender, Date entryDate, PatientDTO hospital) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.birthDate = birthDate;
		this.gender = gender;
		this.entryDate = entryDate;
		this.hospital = hospital;
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public PatientDTO getHospital() {
		return hospital;
	}
	public void setHospital(PatientDTO hospital) {
		this.hospital = hospital;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}