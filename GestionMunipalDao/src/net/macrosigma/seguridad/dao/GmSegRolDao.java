package net.macrosigma.seguridad.dao;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.seguridad.ent.GmSegRol;
import net.macrosigma.util.dao.GenericDao;

public class GmSegRolDao extends GenericDao<GmSegRol, Long> {

	public GmSegRolDao() {
		super(GmSegRol.class);
	}

	public void crear(GmSegRol pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmSegRol pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmSegRol pers) {
		super.eliminar(pers);
	}

	@SuppressWarnings("unchecked")
	public List<GmSegRol> getPorRol(String nombre) {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmSegRol o where o.rolNombre = :nombre ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("nombre", nombre);
		List<GmSegRol> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

	@SuppressWarnings("unchecked")
	public List<GmSegRol> getRoles() {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmSegRol o where o.estado='ACT' ");
		sql.append("order by o.rolNombre ");
		Query query = em.createQuery(sql.toString());
		List<GmSegRol> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

}
