package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.User;

	public class UserDAO {
		
		protected static EntityManagerFactory emf = 
		Persistence.createEntityManagerFactory("jpaPU");

		public UserDAO() {
			// TODO Auto-generated constructor stub
		}
		 	

		public void persist(User user) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
			em.close();
		}
		
		public void removeUser(User user) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.remove(em.merge(user));
			em.getTransaction().commit();
			em.close();
		}
		
		public User merge(User user) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			User updatedUser = em.merge(user);
			em.getTransaction().commit();
			em.close();
			return updatedUser;
		}
		
		
		public List<User> getAllUsers() {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			List<User> users = new ArrayList<User>();
			users = em.createQuery("from User").getResultList();
			em.getTransaction().commit();
			em.close();
			return users;
		}

		public User getUserByName(String name) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			User u = em.createQuery("SELECT p FROM Customer p WHERE p.name = :name", User.class)
	                .setParameter("name", name)
	                .getSingleResult();
			em.getTransaction().commit();
			em.close();
			return u;
		}
		


	}
