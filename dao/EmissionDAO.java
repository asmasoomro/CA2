package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Emission;

	public class EmissionDAO {
		
		protected static EntityManagerFactory emf = 
		Persistence.createEntityManagerFactory("jpaPU");

		public EmissionDAO() {
			// TODO Auto-generated constructor stub
		}
		 	

		public void persist(Emission emission) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(emission);
			em.getTransaction().commit();
			em.close();
		}
		
		public void removeEmission(Emission emission) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.remove(em.merge(emission));
			em.getTransaction().commit();
			em.close();
		}
		
		public Emission merge(Emission emission) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			Emission updatedEmission = em.merge(emission);
			em.getTransaction().commit();
			em.close();
			return updatedEmission;
		}
		
		
	}
