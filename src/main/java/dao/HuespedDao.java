package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.Query;


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
	
	public void eliminarHuesped(Long id) {
	    EntityTransaction tx = em.getTransaction();
	    try {
	        tx.begin();
	        Huesped huesped = em.find(Huesped.class, id);
	        if (huesped != null) {
	            em.remove(huesped);
	        }
	        tx.commit();
	    } catch (Exception e) {
	        if (tx != null && tx.isActive()) {
	            tx.rollback();
	        }
	        throw e;
	    }
	}
	
	public void actualizarHuesped(Huesped huesped) {
	    EntityTransaction tx = em.getTransaction();
	    try {
	        tx.begin();
	        Query query = em.createQuery("UPDATE Huesped h SET h.nombre = :nombre, "
	                + "h.apellido = :apellido, h.fechaNacimiento = :fecha_nacimiento, "
	                + "h.nacionalidad = :nacionalidad, h.telefono = :telefono WHERE h.id = :id ");
	        query.setParameter("nombre", huesped.getNombre());
	        query.setParameter("apellido", huesped.getApellido());
	        query.setParameter("fecha_nacimiento", huesped.getFechaNacimiento());
	        query.setParameter("nacionalidad", huesped.getNacionalidad());
	        query.setParameter("telefono", huesped.getTelefono());
	        query.setParameter("id", huesped.getId());
	        query.executeUpdate();
	        tx.commit();
	    } catch (Exception e) {
	        if (tx != null && tx.isActive()) {
	            tx.rollback();
	        }
	        throw e;
	    }
	}

}