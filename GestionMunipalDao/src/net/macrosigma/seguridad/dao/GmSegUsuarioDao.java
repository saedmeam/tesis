package net.macrosigma.seguridad.dao;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.dao.GenericDao;

public class GmSegUsuarioDao extends GenericDao<GmSegUsuario, Long> {

	public GmSegUsuarioDao() {
		super(GmSegUsuario.class);
	}

	public void crear(GmSegUsuario pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmSegUsuario pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmSegUsuario pers) {
		super.eliminar(pers);
	}

	@SuppressWarnings("unchecked")
	public static GmSegUsuario getUsuarioPorUsuario(String usuario) {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmSegUsuario o where o.usuUsuario = :usuario ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("usuario", usuario);
		List<GmSegUsuario> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<GmSegUsuario> todosLosUsuario() {
		em.clear();
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct(o) from GmSegUsuario o left join fetch o.usuRolUsuId r ");
		sql.append("where r.estado = 'ACT' ");
		sql.append("order by o.usuUsuario ");
		Query query = em.createQuery(sql.toString());
		List<GmSegUsuario> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

	@SuppressWarnings("unchecked")
	public static List<GmSegUsuario> getUsuarioPorNombre(String nombre) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct(o) from GmSegUsuario o left join fetch o.usuRolUsuId r ");
		sql.append("where r.estado = 'ACT' ");
		if (nombre != null) {
			sql.append("and o.usuUsuario like :nombre ");
		}
		sql.append("order by o.usuUsuario");
		Query query = em.createQuery(sql.toString());
		if (nombre != null) {
			nombre = "%" + nombre.toUpperCase() + "%";
			query.setParameter("nombre", nombre);
		}
		List<GmSegUsuario> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

}