package net.macrosigma.gestion.dao;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.gestion.ent.GmGesContenidoTipSolicitud;
import net.macrosigma.parametro.ent.GmParParametros;
import net.macrosigma.util.dao.GenericDao;

public class GmGesContenidoTipSolicitudDao extends
		GenericDao<GmGesContenidoTipSolicitud, Long> {

	public GmGesContenidoTipSolicitudDao() {
		super(GmGesContenidoTipSolicitud.class);
	}

	public void crear(GmGesContenidoTipSolicitud pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmGesContenidoTipSolicitud pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmGesContenidoTipSolicitud pers) {
		super.eliminar(pers);
	}

	@SuppressWarnings("unchecked")
	public static List<GmGesContenidoTipSolicitud> getDepTipSolAct() {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesContenidoTipSolicitud o where ";
		select += " o.estado = 'ACT'";
		sql.append(select);
		Query query = em.createQuery(sql.toString());
		// query.setParameter("claCat", claCat);

		List<GmGesContenidoTipSolicitud> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	public static List<GmGesContenidoTipSolicitud> getDepTipSolXCarrTipSolAct(
			GmParParametros tipSol, GmParParametros carr) {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesContenidoTipSolicitud o where ";
		select += " (o.depTipSolTipSolId = :tipSol or :tipSol =null) and ";
		select += " (o.depTipSolCarreraId = :carr or :carr =null) and ";
		select += " o.estado = 'ACT' ";
		sql.append(select);
		Query query = em.createQuery(sql.toString());
		query.setParameter("tipSol", tipSol);
		query.setParameter("carr", carr);

		List<GmGesContenidoTipSolicitud> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	public static List<GmGesContenidoTipSolicitud> getDepTipSolXCarrUsuSolAct(
			String tipSol) {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesContenidoTipSolicitud o where ";
		select += "( (o.depTipSolCarreraId.parDes like '%'||:tipSol||'%' or :tipSol =null) or ";
		select += " (o.depDepUsuId.usuUsuario like '%'||:tipSol||'%' or :tipSol =null) or ";
		select += " (o.depTipSolTipSolId.parDes like '%'||:tipSol||'%' or :tipSol =null) ) ";
		select += " and o.estado = 'ACT' ";
		sql.append(select);
		Query query = em.createQuery(sql.toString());
		query.setParameter("tipSol", tipSol);

		List<GmGesContenidoTipSolicitud> result = query.getResultList();
		return result;
	}

}
