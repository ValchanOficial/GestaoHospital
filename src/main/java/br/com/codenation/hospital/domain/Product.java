package br.com.codenation.hospital.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import br.com.codenation.hospital.dto.ProductDTO;

@Document(collection="product_collection")
public class Product implements Serializable{
	private static final long serialVersionUID = 512205703487777734L;
	
	@Id
	private String id;
	private String name;
	private String description;
	private int quantity;
	private ProductDTO hospital;
	
	public Product() {
	
	}	
	
	public Product(String id, String name, String description, int quantity, ProductDTO hospital) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public ProductDTO getHospital() {
		return hospital;
	}
	public void setHospital(ProductDTO hospital) {
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
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}