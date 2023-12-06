package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


//@NamedQueries({
	//@NamedQuery(name="User.findAll", query="select o from User o"), 
//	@NamedQuery(name = "User.findByUsername", query = "select o from User o where o.name=:name")
//})

@Entity
public class Emission {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private int year;
	private double value;
	private String scenario;
	private String category;
	private String description;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getScenario() {
		return scenario;
	}
	public void setScenario(String scenario) {
		this.scenario = scenario;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
