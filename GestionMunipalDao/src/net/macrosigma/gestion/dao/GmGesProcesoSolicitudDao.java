package net.macrosigma.gestion.dao;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.gestion.ent.GmGesProcesoSolicitud;
import net.macrosigma.gestion.ent.GmGesSolicitud;
import net.macrosigma.util.dao.GenericDao;

public class GmGesProcesoSolicitudDao extends
		GenericDao<GmGesProcesoSolicitud, Long> {

	public GmGesProcesoSolicitudDao() {
		super(GmGesProcesoSolicitud.class);
	}

	public void crear(GmGesProcesoSolicitud pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmGesProcesoSolicitud pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmGesProcesoSolicitud pers) {
		super.eliminar(pers);
	}

	@SuppressWarnings("unchecked")
	public List<GmGesProcesoSolicitud> getPreFreAct() {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesProcesoSolicitud o where o.estado = 'ACT'";
		select+=" order by solId desc";
		sql.append(select);
		Query query = em.createQuery(sql.toString());

		List<GmGesProcesoSolicitud> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<GmGesProcesoSolicitud> getProcSolBySol(GmGesSolicitud usu) {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesProcesoSolicitud o where o.estado = 'ACT' and (procSolSolid = :usu or :usu = null )";
		select+=" order by solId desc";
		sql.append(select);
		Query query = em.createQuery(sql.toString());
		query.setParameter("usu", usu);

		List<GmGesProcesoSolicitud> result = query.getResultList();
		return result;
	}

}
