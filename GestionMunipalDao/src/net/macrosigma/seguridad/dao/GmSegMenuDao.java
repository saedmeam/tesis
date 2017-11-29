package net.macrosigma.seguridad.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.seguridad.ent.GmSegMenu;
import net.macrosigma.seguridad.ent.GmSegRol;
import net.macrosigma.seguridad.ent.GmSegRolUsuario;
import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.dao.GenericDao;

public class GmSegMenuDao extends GenericDao<GmSegMenu, Long> {

	public GmSegMenuDao() {
		super(GmSegMenu.class);
	}

	public void crear(GmSegMenu pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmSegMenu pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmSegMenu pers) {
		super.eliminar(pers);
	}

	@SuppressWarnings("unchecked")
	public GmSegMenu getMenuPorId(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmSegMenu o where o.menId = :id ");
		sql.append("and o.estado = 'ACT' ");
		sql.append("order by o.menOrden ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("id", id);
		List<GmSegMenu> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result.get(0);
	}

	//
	// @SuppressWarnings("unchecked")
	// public static List<GmMenu> getMenuPorIdPadreSubmenu(GmMenu id) {
	// StringBuilder sql = new StringBuilder();
	// sql.append("select o from GmSegMenu o where o.padreSubmenu = :id ");
	// sql.append("and o.estado = 'ACT' ");
	// sql.append("order by o.menOrden ");
	// Query query = em.createQuery(sql.toString());
	// query.setParameter("id", id);
	// List<GmMenu> result = query.getResultList();
	// return result == null || result.isEmpty() ? null : result;
	// }

	@SuppressWarnings("unchecked")
	public static List<GmSegMenu> getMenuPorIdPadreMenu(GmSegMenu id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmSegMenu o where o.padreMenu = :id ");
		sql.append("and o.estado = 'ACT' ");
		sql.append("order by o.menOrden ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("id", id);
		List<GmSegMenu> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

	@SuppressWarnings("unchecked")
	public static List<GmSegMenu> getMenuPrincipal() {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmSegMenu o where o.padreMenu = null ");
		sql.append("and o.estado = 'ACT' ");
		sql.append("order by o.menOrden, o.menNombre ");
		Query query = em.createQuery(sql.toString());
		List<GmSegMenu> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

	@SuppressWarnings("unchecked")
	public static List<GmSegMenu> getMenuPorUsuario(GmSegUsuario usu) {
		List<GmSegRol> roles = new ArrayList<GmSegRol>();
		for (GmSegRolUsuario rolUsu : usu.getUsuRolUsuId()) {
			if (rolUsu.getEstado().equals("ACT")) {
				roles.add(rolUsu.getGmSegRol());
			}
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct rm.menu.padreMenu from GmSegRolMenu rm, GmSegRolUsuario ru ");
		sql.append("where ru.gmSegUsuario = :usu ");
		sql.append("and rm.rol in (:roles) ");
		sql.append("and ru.estado = 'ACT' ");
		sql.append("and rm.estado = 'ACT' ");
		sql.append("and rm.menu.estado = 'ACT' ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("usu", usu);
		query.setParameter("roles", roles);
		List<GmSegMenu> result = query.getResultList();
		if (result != null) {
			Collections.sort(result, new Comparator<GmSegMenu>() {
				public int compare(GmSegMenu m1, GmSegMenu m2) {
					return new Integer(m1.getMenOrden()).compareTo(new Integer(
							m2.getMenOrden()));
				}
			});
		}
		return result == null || result.isEmpty() ? null : result;
	}

	@SuppressWarnings("unchecked")
	public static List<GmSegMenu> getMenuPorModulo(GmSegUsuario usu,
			GmSegMenu id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct rm.menu from GmSegRolMenu rm, GmSegRolUsuario ru ");
		sql.append("where ru.gmSegUsuario = :usu ");
		sql.append("and ru.gmSegRol = rm.rol ");
		sql.append("and rm.menu.padreMenu = :id ");
		sql.append("and ru.estado = 'ACT' ");
		sql.append("and rm.estado = 'ACT' ");
		sql.append("and rm.menu.estado = 'ACT' ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("usu", usu);
		query.setParameter("id", id);
		List<GmSegMenu> result = query.getResultList();
		if (result != null) {
			Collections.sort(result, new Comparator<GmSegMenu>() {
				public int compare(GmSegMenu m1, GmSegMenu m2) {
					return new Integer(m1.getMenOrden()).compareTo(new Integer(
							m2.getMenOrden()));
				}
			});
		}
		return result == null || result.isEmpty() ? null : result;
	}

	@SuppressWarnings("unchecked")
	public static List<GmSegMenu> getMenuModulo() {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmSegMenu o where o.padreMenu = null ");
		sql.append("order by o.menOrden, o.menNombre ");
		Query query = em.createQuery(sql.toString());
		List<GmSegMenu> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

	@SuppressWarnings("unchecked")
	public static List<GmSegMenu> getMenuOpcion() {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmSegMenu o where o.padreMenu <> null ");
		sql.append("order by  o.padreMenu, o.menOrden, o.menNombre ");
		Query query = em.createQuery(sql.toString());
		List<GmSegMenu> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

	@SuppressWarnings("unchecked")
	public List<GmSegMenu> getOpcionPorNombre(String nombre) {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmSegMenu o where o.padreMenu <> null ");
		if (nombre != null) {
			sql.append("and o.menNombre like :nombre ");
		}
		sql.append("order by  o.padreMenu, o.menOrden, o.menNombre ");
		Query query = em.createQuery(sql.toString());
		if (nombre != null) {
			nombre = "%" + nombre.toUpperCase() + "%";
			query.setParameter("nombre", nombre);
		}
		List<GmSegMenu> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

	@SuppressWarnings("unchecked")
	public static List<GmSegMenu> getModuloPorNombre(String nombre) {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmSegMenu o where o.padreMenu = null ");
		if (nombre != null) {
			sql.append("and o.menNombre like :nombre ");
		}
		sql.append("order by o.menOrden, o.menNombre ");
		Query query = em.createQuery(sql.toString());
		if (nombre != null) {
			nombre = "%" + nombre.toUpperCase() + "%";
			query.setParameter("nombre", nombre);
		}
		List<GmSegMenu> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

}
