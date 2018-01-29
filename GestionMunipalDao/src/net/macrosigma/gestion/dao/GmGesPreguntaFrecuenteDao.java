package net.macrosigma.gestion.dao;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.gestion.ent.GmGesPreguntaFrecuente;
import net.macrosigma.util.dao.GenericDao;

public class GmGesPreguntaFrecuenteDao extends
		GenericDao<GmGesPreguntaFrecuente, Long> {

	public GmGesPreguntaFrecuenteDao() {
		super(GmGesPreguntaFrecuente.class);
	}

	public void crear(GmGesPreguntaFrecuente pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmGesPreguntaFrecuente pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmGesPreguntaFrecuente pers) {
		super.eliminar(pers);
	}

	@SuppressWarnings("unchecked")
	public static List<GmGesPreguntaFrecuente> getValPrebyPre(String claCat) {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesPreguntaFrecuente o where (o.desPregunta like '%'||:claCat||'%' or :claCat =null)";
		select += "and o.estado = 'ACT'";
		sql.append(select);
		Query query = em.createQuery(sql.toString());
		query.setParameter("claCat", claCat);

		List<GmGesPreguntaFrecuente> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<GmGesPreguntaFrecuente> getPreFreAct() {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesPreguntaFrecuente o where o.estado = 'ACT'";
		sql.append(select);
		Query query = em.createQuery(sql.toString());

		List<GmGesPreguntaFrecuente> result = query.getResultList();
		return result;
	}

}
