package net.macrosigma.util.dao;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

public class GenericDao<T, PK> {

	{
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("GestionMunipalDao");
		}
		em = emf.createEntityManager();
	}
	@PersistenceContext(type = PersistenceContextType.TRANSACTION) 
	protected static EntityManager em = null;
	private static EntityManagerFactory emf;

	public GenericDao() {
		this.tipo = null;
	}

	public final Class<T> tipo;

	public GenericDao(final Class<T> type) {
		this.tipo = type;
	}

	public void crear(T o) {
		try {
			em.persist(o);
			em.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizar(T o) {
		try {

			em.merge(o);

			em.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminar(T o) {
		try {
			EntityTransaction trx = em.getTransaction();
			trx.begin();
			if (trx.isActive()) {
				em.merge(o);
				// em.remove(o);
			}
			trx.commit();
			em.flush();
			em.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public T recuperar(PK o) {
		T retorno = em.find(tipo, o);
		return retorno;
	}

	@SuppressWarnings("unchecked")
	public List<T> obtenerTodos() {
		final String className = tipo.getSimpleName();
		final StringBuffer sql = new StringBuffer();
		sql.append("from ").append(className);
		final Query query = em.createQuery(sql.toString());
		return query.getResultList();
	}

	protected void consultaDependientes(Object t) {
		if (t == null)
			return;
		for (Field f : t.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if (f.getType().equals(Long.class)
					|| f.getType().equals(long.class)
					|| f.getType().equals(String.class)
					|| f.getType().equals(char.class)
					|| f.getType().equals(Integer.class)
					|| f.getType().equals(int.class)
					|| f.getType().equals(Boolean.class)
					|| f.getType().equals(boolean.class)
					|| f.getType().equals(BigDecimal.class)
					|| f.getType().isEnum() || f.getType().equals(Date.class)
					|| f.getType().equals(Timestamp.class)) {
				continue;
			}
			if (!f.getType().equals(List.class)) {
				try {
					consultaDependientes(f.get(t));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				List<?> l = null;
				try {
					l = (List<?>) f.get(t);
					l.size();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public T getUnique(List<?> result) {
		if (!result.isEmpty()) {
			return (T) result.get(0);
		}
		return null;
	}

	public void newManager() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("GestionMunipalDao");
		}
		if (em == null)
			em = emf.createEntityManager();
		else {
			if (!em.isOpen()) {
				em = emf.createEntityManager();
			}
		}
	}
}
