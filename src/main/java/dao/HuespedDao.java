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

	public void eliminar(Huesped huesped) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(huesped);
		tx.commit();
	}
	
	public void actualizar(Huesped huesped) {
		TypedQuery<Huesped> query = em.createQuery("UPDATE Huesped h SET h.nombre = :nombre, "
				+ "h.apellido = :apellido, h.fecha_nacimiento = :fecha_nacimiento, h.nacionalidad = :nacionalidad"
				+ ", h.telefono = :telefono WHERE h.id = :id ", Huesped.class);
		query.setParameter("nombre", huesped.getNombre());
		query.setParameter("apellido", huesped.getApellido());
		query.setParameter("nacionalidad", huesped.getNacionalidad());
		query.setParameter("telefono", huesped.getTelefono());
		query.setParameter("fecha_nacimiento", huesped.getFechaNacimiento());
		query.setParameter("id", huesped.getId());
		query.executeUpdate();
		em.close();
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