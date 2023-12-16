package entities;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name= "XML")
public class XML {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String category;
	private String year;
	private String scenario;
	private String gasUnits;
	private String nk;
	private String value;
	
	public XML() {
		
	}
	
	public XML(String category, String year, String scenario, String gasUnits, String nk, String value) {
		this.category = category;
		this.year = year;
		this.scenario = scenario;
		this.gasUnits = gasUnits;
		this.nk = nk;
		this.value = value;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public String getGasUnits() {
		return gasUnits;
	}

	public void setGasUnits(String gasUnits) {
		this.gasUnits = gasUnits;
	}

	public String getNk() {
		return nk;
	}

	public void setNk(String nk) {
		this.nk = nk;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
