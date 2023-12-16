package main;

import dao.UserDAO;
import entities.User;

public class Test2 {
	
	public Test2() {

	UserDAO userdao = new UserDAO();
	User user = new User("Thomas","Thomas22","secret1");
	userdao.persist(user);
	}
	
	public static void main(String[] args) {
		new Test2();
	}
}
