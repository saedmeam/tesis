package net.macrosigma.seguridad.dao;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.seguridad.ent.GmSegMenu;
import net.macrosigma.seguridad.ent.GmSegRol;
import net.macrosigma.seguridad.ent.GmSegRolMenu;
import net.macrosigma.util.dao.GenericDao;

public class GmSegRolMenuDao extends GenericDao<GmSegRolMenu, Long> {

	public GmSegRolMenuDao() {
		super(GmSegRolMenu.class);
	}

	public void crear(GmSegRolMenu pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmSegRolMenu pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmSegRolMenu pers) {
		super.eliminar(pers);
	}

	@SuppressWarnings("unchecked")
	public GmSegRolMenu getPorRolyMenu(GmSegRol rol, GmSegMenu menu) {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmSegRolMenu o where o.rol=:rol and o.menu=:menu ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("rol", rol);
		query.setParameter("menu", menu);
		List<GmSegRolMenu> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<GmSegRolMenu> getPorRol(GmSegRol rol) {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmSegRolMenu o where o.rol=:rol ");
		sql.append("and o.menu.estado = 'ACT' ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("rol", rol);
		List<GmSegRolMenu> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

}
