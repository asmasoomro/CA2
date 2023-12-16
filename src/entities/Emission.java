package entities;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@XmlRootElement(name= "Emissions")
public class Emission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty("Category")
    private String category;
	@JsonProperty("Gas Units")
	private String gasUnits;
	@JsonProperty("Value")
	private String value;
	
	public Emission(String category, String gasUnits, String value) {
		this.category = category;
		this.gasUnits = gasUnits;
		this.value = value;
	}

	public Emission() {
	    
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGasUnits() {
		return gasUnits;
	}

	public void setGasUnits(String gasUnits) {
		this.gasUnits = gasUnits;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}