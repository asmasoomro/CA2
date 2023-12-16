package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name= "AllData")
public class AllData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String category;
	private String description;
	private String gasUnits;
	private String predictedEmissions;
	private String readings2023;
	private String variance;
	
	public AllData(String category, String description, String gasUnits, String predictedEmissions, String readings2023, String variance) {
		this.category = category;
		this.description = description;
		this.gasUnits = gasUnits;
		this.predictedEmissions = predictedEmissions;
		this.readings2023 = readings2023;
		this.variance = variance;
	}
	
	public AllData() {
		
	}
	
	@XmlElement
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement
	public String getGasUnits() {
		return gasUnits;
	}
	public void setGasUnits(String gasUnits) {
		this.gasUnits = gasUnits;
	}
	@XmlElement
	public String getpredictedEmissions() {
		return predictedEmissions;
	}
	public void setpredictedEmissions(String predictedEmissions) {
		this.predictedEmissions = predictedEmissions;
	}
	@XmlElement
	public String getReadings2023() {
		return readings2023;
	}
	public void setReadings2023(String readings2023) {
		this.readings2023 = readings2023;
	}
	@XmlElement
	public String getVariance() {
		return variance;
	}
	public void setVariance(String variance) {
		this.variance = variance;
	}
	
	
}