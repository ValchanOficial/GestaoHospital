package br.com.codenation.hospital.domain;

import java.io.Serializable;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.codenation.hospital.dto.PatientDTO;

@Document(collection="patient_collection")
public class Patient implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private String patientName;
	private String cpf;
	private Date birthDate;
	private String gender;
	private Date entryDate;
	private PatientDTO hospital;
	private boolean active;
	public Patient() {
		this.active = false;
	}
	
	public Patient(String paciente_id, String name, String cpf, Date birthDate, String gender, Date entryDate, PatientDTO hospital) {
		super();
		this.id = paciente_id;
		this.patientName = name;
		this.cpf = cpf;
		this.birthDate = birthDate;
		this.gender = gender;
		this.entryDate = entryDate;
		this.hospital = hospital;
		this.active = false;
	}

	public String getPaciente_id() {
		return id;
	}

	public void setPaciente_id(String paciente_id) {
		this.id = paciente_id;
	}
	public String getName() {
		return patientName;
	}
	public void setName(String name) {
		this.patientName = name;
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
	public void checkIn(){this.active=true;}
	public void checkOut(){this.active=false;}
	public boolean isActive(){return this.active;}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((entryDate == null) ? 0 : entryDate.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((patientName == null) ? 0 : patientName.hashCode());
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
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (entryDate == null) {
			if (other.entryDate != null)
				return false;
		} else if (!entryDate.equals(other.entryDate))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (patientName == null) {
			if (other.patientName != null)
				return false;
		} else if (!patientName.equals(other.patientName))
			return false;
		return true;
	}
}