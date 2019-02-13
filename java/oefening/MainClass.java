package oefening;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainClass {

	public static void main(String[] args) {

		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("TestPersistence");
		EntityManager em = emFactory.createEntityManager();

		Baas baas = new Baas();
		baas.setNaam("PeterOptie1");
		
		Baas baas2 = new Baas();
		baas2.setNaam("PeterOptie2");
		
/*		em.persist(baas);
		em.persist(baas2);
		em.getTransaction().commit();
		em.getTransaction().begin();
		//detach Peter1
//		em.detach(baas);
		
		System.out.println("Contains Peter1?:" + em.contains(baas));
		System.out.println("Contains Peter2?:" + em.contains(baas2));
		
		//Geen select Peter1
		Baas b1 = em.find(Baas.class, 1L);
		System.out.println("Peter1:" + b1);
		
		//Wel select Peter2
		Baas b2 = em.find(Baas.class, 2L);
		System.out.println("Peter2:" + b2);
		
		em.remove(baas2);
		em.getTransaction().commit();
		
		em.detach(baas2);
		
		Baas b3 = em.find(Baas.class, 2L);
		System.out.println("Peter2:" + b3);
*/		
		
		
		Hond hond1 = new Hond();
		hond1.setNaam("Chrisa");
//		baas.setHond(hond1);
		hond1.setBaas(baas);
//		em.getTransaction().begin();
		em.getTransaction().begin();
		em.persist(hond1);
//		em.persist(baas);
		em.getTransaction().commit();
		
		em.close();
	}

}
