package net.macrosigma.gestion.dao;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.gestion.ent.GmGesDepartamentoTipSolicitud;
import net.macrosigma.parametro.ent.GmParParametros;
import net.macrosigma.util.dao.GenericDao;

public class GmGesDepartamentoTipSolicitudDao extends
		GenericDao<GmGesDepartamentoTipSolicitud, Long> {

	public GmGesDepartamentoTipSolicitudDao() {
		super(GmGesDepartamentoTipSolicitud.class);
	}

	public void crear(GmGesDepartamentoTipSolicitud pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmGesDepartamentoTipSolicitud pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmGesDepartamentoTipSolicitud pers) {
		super.eliminar(pers);
	}

	 @SuppressWarnings("unchecked")
	 public static List<GmGesDepartamentoTipSolicitud> getDepTipSolAct() {
	 StringBuilder sql = new StringBuilder();
	 String select = "";
	 select =
	 "select o from GmGesDepartamentoTipSolicitud o where ";
	 select += " o.estado = 'ACT'";
	 sql.append(select);
	 Query query = em.createQuery(sql.toString());
//	 query.setParameter("claCat", claCat);
	
	 List<GmGesDepartamentoTipSolicitud> result = query.getResultList();
	 return result;
	 }
	 
	 @SuppressWarnings("unchecked")
	 public static List<GmGesDepartamentoTipSolicitud> getDepTipSolXCarrTipSolAct(GmParParametros tipSol,GmParParametros carr) {
	 StringBuilder sql = new StringBuilder();
	 String select = "";
	 select =
	 "select o from GmGesDepartamentoTipSolicitud o where ";
	 select += " (o.depTipSolTipSolId = :tipSol or :tipSol =null) and ";
	 select += " (o.depTipSolCarreraId = :carr or :carr =null) and ";
	 select += " o.estado = 'ACT' ";
	 sql.append(select);
	 Query query = em.createQuery(sql.toString());
	 query.setParameter("tipSol", tipSol);
	 query.setParameter("carr", carr);
	
	 List<GmGesDepartamentoTipSolicitud> result = query.getResultList();
	 return result;
	 }

}
