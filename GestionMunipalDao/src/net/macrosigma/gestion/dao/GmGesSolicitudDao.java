package net.macrosigma.gestion.dao;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.gestion.ent.GmGesSolicitud;
import net.macrosigma.util.dao.GenericDao;

public class GmGesSolicitudDao extends
		GenericDao<GmGesSolicitud, Long> {

	public GmGesSolicitudDao() {
		super(GmGesSolicitud.class);
	}

	public void crear(GmGesSolicitud pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmGesSolicitud pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmGesSolicitud pers) {
		super.eliminar(pers);
	}

	@SuppressWarnings("unchecked")
	public static List<GmGesSolicitud> getValPrebyPre(String claCat) {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesPreguntasFrecuentes o where (o.desPregunta = :claCat or :claCat =null)";
		select += "and o.estado = 'ACT'";
		sql.append(select);
		Query query = em.createQuery(sql.toString());
		query.setParameter("claCat", claCat);

		List<GmGesSolicitud> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<GmGesSolicitud> getPreFreAct() {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesSolicitud o where o.estado = 'ACT'";
		sql.append(select);
		Query query = em.createQuery(sql.toString());

		List<GmGesSolicitud> result = query.getResultList();
		return result;
	}

}
