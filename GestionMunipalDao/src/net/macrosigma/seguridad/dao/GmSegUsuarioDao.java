package net.macrosigma.seguridad.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.seguridad.ent.GmSegRol;
import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.seguridad.enu.RolEnum;
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
		sql.append("where o.estado = 'ACT' ");
		sql.append("order by o.usuUsuario ");
		Query query = em.createQuery(sql.toString());
		List<GmSegUsuario> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

	@SuppressWarnings("unchecked")
	public static List<GmSegUsuario> getUsuarioPorNombre(String nombre) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct(o) from GmSegUsuario o left join fetch o.usuRolUsuId r ");
		sql.append("where o.estado = 'ACT' ");
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

	@SuppressWarnings("unchecked")
	public static List<GmSegUsuario> getUsuarioACT() {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct(o) from GmSegUsuario o left join fetch o.usuRolUsuId r ");
		sql.append("where o.estado = 'ACT' ");
		Query query = em.createQuery(sql.toString());
		List<GmSegUsuario> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

	public static List<GmSegUsuario> getUsuarioEstudiante() {
		// StringBuilder sql = new StringBuilder();
		// sql.append("select distinct(o) from GmSegUsuario o ");
		// sql.append("where o.estado = 'ACT' ");
		// // sql.append("and :rol in (o.usuRolUsuId) ");
		// sql.append("order by o.usuUsuario");
		// Query query = em.createQuery(sql.toString());
		GmSegRol rol = new GmSegRol();
		GmSegRolDao rolDao = new GmSegRolDao();
		rol = rolDao.recuperar(RolEnum.ESTUDIANTE.getIndice());
		// query.setParameter("rol", rol);
		List<GmSegUsuario> result = new ArrayList<>();
		for (int i = 0; i < rol.getRolRolUsuId().size(); i++) {
			result.add(rol.getRolRolUsuId().get(i).getGmSegUsuario());
		}
//		List<GmSegUsuario> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

}
