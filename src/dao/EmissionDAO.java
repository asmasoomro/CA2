package dao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import entities.AllData;
import entities.Emission;
import entities.User;

public class EmissionDAO {

	protected static EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory("gasesPU");

	public EmissionDAO() {
		// TODO Auto-generated constructor stub
	}


	public List<AllData> readAllFromFile() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<AllData> emissions = new ArrayList<AllData>();
		emissions = em.createQuery("from AllData", AllData.class).getResultList();
		em.getTransaction().commit();
		em.close();
		return emissions;
	}
	
	public List<AllData> searchByCategory(String category) {
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    List<AllData> results = em.createQuery("SELECT e FROM AllData e WHERE e.category = :category", AllData.class)
	            .setParameter("category", category)
	            .getResultList();
	    em.getTransaction().commit();
	    em.close();
	    return results;
	}
	
	public void persist(AllData d) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(d);
		em.getTransaction().commit();
		em.close();
	}
	
	public AllData merge(AllData d) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		AllData updatedData = em.merge(d);
		em.getTransaction().commit();
		em.close();
		return updatedData;
	}
	
	public String update(int id, AllData d) {
	    EntityManager em = emf.createEntityManager();
	    em.getTransaction().begin();
	    AllData currentData = em.find(AllData.class, id);

	    if (currentData != null) {
	    	currentData.setCategory(d.getCategory());
	    	currentData.setDescription(d.getDescription());
	    	currentData.setGasUnits(d.getGasUnits());
	    	currentData.setpredictedEmissions(d.getpredictedEmissions());
	        currentData.setReadings2023(d.getReadings2023());
	        
	        em.getTransaction().commit();
	        em.close();
	        return "Data has now been Updated";
	    } else {
	        em.getTransaction().rollback();
	        em.close();
	        return "Error data id: " + id + " not found";
	    }
	}

	public String removeData(String category, String gasUnits) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		try {
			AllData data = em.createQuery("SELECT u FROM AllData u WHERE u.category = :category AND u.gasUnits = :gasUnits", AllData.class)
					.setParameter("category", category)
					.setParameter("gasUnits", gasUnits)
					.getSingleResult();

			if  (data != null) {
				em.remove(data);
				em.getTransaction().commit();
				em.close();
				return "Data Removed";
			} else {
				em.getTransaction().rollback();
				em.close();
				return "Data Not Found";
			}
		} catch (NoResultException e) {
			em.getTransaction().rollback();
			em.close();
			return "Data Not Found";
		}
	}

}