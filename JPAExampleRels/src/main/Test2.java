package main;

import java.util.ArrayList;
import java.util.List;

import dao.EmissionDAO;
import dao.UserDAO;
import entities.Emission;
import entities.User;

public class Test2 {
	
	public Test2() {

	UserDAO userdao = new UserDAO();
	EmissionDAO eDAO = new EmissionDAO();
	
	Emission e1 = new Emission("2.A.", "Total GHGs (ktCO2e)", "107.34");
	Emission e2 = new Emission("3.B.", "Total ESD GHGs (ktCO2e)", "1877");
	eDAO.persist(e1);
	eDAO.persist(e2);
	
	List<Emission> emissions = new ArrayList<Emission>();
	emissions.add(e1);
	emissions.add(e2);
	
	User user = new User("Kimberly","kimi99","hello99", emissions);
	userdao.persist(user);
	}
	
	public static void main(String[] args) {
		new Test2();
	}
}
