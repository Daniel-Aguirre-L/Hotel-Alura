package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import modelos.Reserva;
import javax.persistence.Query;

public class ReservaDao {

	private final EntityManager em;

	public ReservaDao(EntityManager em) {
		this.em = em;
	}

	public Long guardar(Reserva reserva) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(reserva);
		em.flush();
		tx.commit();
		return reserva.getId();

	}

	public void eliminar(Reserva reserva) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.remove(reserva);
		tx.commit();
	}

	public List<Reserva> obtenerTodos() {
		TypedQuery<Reserva> query = em.createQuery("SELECT h FROM Reserva h", Reserva.class);
		return query.getResultList();
	}

	public void actualizarReserva(Reserva reserva) {
	    EntityTransaction tx = em.getTransaction();
	    try {
	        tx.begin();
	        Query query = em.createQuery("UPDATE Reserva r SET r.fechaEntrada = :fechaEntrada, "
	                + "r.fechaSalida = :fechaSalida, r.valor = :valor, r.formaPago = :formaPago "
	                + "WHERE r.id = :id ");
	        query.setParameter("id", reserva.getId());
	        query.setParameter("fechaEntrada", reserva.getFechaEntrada());
	        query.setParameter("fechaSalida", reserva.getFechaSalida());
	        query.setParameter("valor", reserva.getValor());
	        query.setParameter("formaPago", reserva.getFormaPago());
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