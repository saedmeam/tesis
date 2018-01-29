package net.macrosigma.parametro.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.parametro.ent.GmParParametros;
import net.macrosigma.util.dao.GenericDao;

public class GmParParametroDao extends GenericDao<GmParParametros, Long> {

	public GmParParametroDao() {
		super(GmParParametros.class);
	}

	public void crear(GmParParametros pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmParParametros pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmParParametros pers) {
		super.eliminar(pers);
	}

	@SuppressWarnings("unchecked")
	public static List<GmParParametros> getParametroByGrupo(
			GmParParametros grupo) {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmParParametros o where o.carIdPad = :grupo and o.estado = 'ACT' order by o.parDes");
		Query query = em.createQuery(sql.toString());
		query.setParameter("grupo", grupo);
		List<GmParParametros> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

	@SuppressWarnings("unchecked")
	public static List<GmParParametros> getParametropad() {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmParParametros o where o.estado = 'ACT' and o.carIdPad is null order by o.parDes");
		Query query = em.createQuery(sql.toString());
		List<GmParParametros> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}
	
	@SuppressWarnings("unchecked")
	public static List<GmParParametros> getParametroComp() {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmParParametros o where o.carIdPad is null order by o.parDes");
		Query query = em.createQuery(sql.toString());
		List<GmParParametros> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

	@SuppressWarnings("unchecked")
	public static List<GmParParametros> getParametroByDes(String grupo) {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmParParametros o where upper(o.parDes) like '%'||:grupo||'%' and o.carIdPad is null and o.estado = 'ACT' order by o.parDes");
		Query query = em.createQuery(sql.toString());
		query.setParameter("grupo", grupo);
		List<GmParParametros> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}
	
	@SuppressWarnings("unchecked")
	public boolean valParametroByDes(String grupo) {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmParParametros o where upper(o.parDes) like '%'||:grupo||'%' and o.estado = 'ACT' order by o.parDes");
		Query query = em.createQuery(sql.toString());
		query.setParameter("grupo", grupo);
		List<GmParParametros> result = query.getResultList();
		
		return result == null || result.isEmpty() ? false : true;
	}

	public static List<GmParParametros> getParametroByDesPad(String grupo) {
		List<GmParParametros> lpad = new ArrayList<>();
		lpad = getParametroByDes(grupo);
		List<GmParParametros> result = ParametroPorCodPadre(lpad.get(0)
				.getPar_id());
		return result == null || result.isEmpty() ? null : result;
	}

	@SuppressWarnings("unchecked")
	public static List<GmParParametros> ParametroPorCodPadre(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmParParametros o where o.carIdPad.par_id = :id and o.estado = 'ACT' order by o.parDes");
		Query query = em.createQuery(sql.toString());
		query.setParameter("id", id);
		List<GmParParametros> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

	@SuppressWarnings("unchecked")
	public static long getconthij(GmParParametros pad) {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmParParametros o where o.estado = 'ACT' and o.carIdPad = :pad order by o.parDes");
		Query query = em.createQuery(sql.toString());
		query.setParameter("pad", pad);
		List<GmParParametros> lpar = query.getResultList();
		long result = lpar.size();
		return result;
	}

}
