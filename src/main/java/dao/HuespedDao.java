package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import modelos.Huesped;

public class HuespedDao {

	private final EntityManager em;

	public HuespedDao(EntityManager em) {
		this.em = em;
	}

	public void guardar(Huesped huesped) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(huesped);
		tx.commit();
	}

	public void actualizar(Huesped huesped) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(huesped);
		tx.commit();
	}

	public void eliminar(Huesped huesped) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(huesped);
		tx.commit();
	}

	public List<Huesped> obtenerTodos() {
		TypedQuery<Huesped> query = em.createQuery("SELECT h FROM Huesped h", Huesped.class);
		return query.getResultList();
	}

	public List<Huesped> listarPorApellido(String busqueda) {
		TypedQuery<Huesped> query = em
				.createQuery("SELECT h FROM Huesped h WHERE apellido " + "LIKE CONCAT('%', ?1, '%')", Huesped.class);
		query.setParameter(1, busqueda);
		return query.getResultList();
	}

}