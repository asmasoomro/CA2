package entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	
	private String name;
	private String userName;
	private String password;
	
	public User(String name, String userName, String password, List<Emission> emissions) {
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.emissions = emissions;
	}
	
	public User(String name, String userName, String password) {
		this.name = name;
		this.userName = userName;
		this.password = password;
	}
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Emission> emissions = new ArrayList<Emission>();
	
	
	public User() {
		
	}
	
	@XmlElement
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@XmlElement
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void addEmission(Emission emission) {
		emissions.add(emission);
	}
	
	public List<Emission> getEmissions() {
		return emissions;
	}



	public void setEmissions(List<Emission> emissions) {
		this.emissions = emissions;
	}
	
}