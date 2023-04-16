package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import modelos.Reserva;

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

    public void actualizar(Reserva reserva) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(reserva);
        tx.commit();
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
}