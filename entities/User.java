package entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;

import org.hibernate.annotations.NamedQueries;

@Entity
public class User {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private String username;
	private String password;

	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Emission> emissions = new ArrayList<Emission>();
	
    public User() {
	
    }
	
	public User(String name, String username, String password, List<Emission> emissions) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.emissions = emissions;
	}
	
	public User(String name, String username, String password) {
		this.name = name;
		this.username = username;
		this.password = password;
	}

	
	@XmlElement
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	@XmlElement
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
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
