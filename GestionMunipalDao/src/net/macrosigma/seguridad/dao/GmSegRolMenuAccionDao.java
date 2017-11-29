package net.macrosigma.seguridad.dao;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.seguridad.ent.GmSegAcciones;
import net.macrosigma.seguridad.ent.GmSegMenu;
import net.macrosigma.seguridad.ent.GmSegRol;
import net.macrosigma.seguridad.ent.GmSegRolMenuAccion;
import net.macrosigma.util.dao.GenericDao;

public class GmSegRolMenuAccionDao extends GenericDao<GmSegRolMenuAccion, Long> {

	public GmSegRolMenuAccionDao() {
		super(GmSegRolMenuAccion.class);
	}

	public void crear(GmSegRolMenuAccion pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmSegRolMenuAccion pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmSegRolMenuAccion pers) {
		super.eliminar(pers);
	}

	@SuppressWarnings("unchecked")
	public GmSegRolMenuAccion getPorRolMenuyAccion(GmSegRol rol,
			GmSegMenu menu, GmSegAcciones accion) {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmSegRolMenuAccion o where o.rolMenu.rol=:rol ");
		sql.append("and o.rolMenu.menu=:menu and o.accion=:accion ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("rol", rol);
		query.setParameter("menu", menu);
		query.setParameter("accion", accion);
		List<GmSegRolMenuAccion> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<GmSegRolMenuAccion> getTodosActivos() {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmSegRolMenuAccion o");
		Query query = em.createQuery(sql.toString());
		List<GmSegRolMenuAccion> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

}
