package net.macrosigma.gestion.dao;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.gestion.ent.GmGesSolicitud;
import net.macrosigma.parametro.ent.GmParParametros;
import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.dao.GenericDao;

public class GmGesSolicitudDao extends GenericDao<GmGesSolicitud, Long> {

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
	public static List<GmGesSolicitud> getSolbyTipSol(GmSegUsuario usu,
			GmParParametros tipSol) {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesSolicitud o where (o.solTipoSolicitud = :tipSol or :tipSol =null)";
		select += "(o.solUsu = :usu or :usu = null)";
		select += "and o.estado = 'ACT'";
		sql.append(select);
		Query query = em.createQuery(sql.toString());
		query.setParameter("tipSol", tipSol);
		query.setParameter("usu", usu);

		List<GmGesSolicitud> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<GmGesSolicitud> getPreFreAct(GmSegUsuario usu) {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesSolicitud o where o.estado = 'ACT' and (o.solUsu = :usu or :usu = null)";
		sql.append(select);
		Query query = em.createQuery(sql.toString());
		query.setParameter("usu", usu);

		List<GmGesSolicitud> result = query.getResultList();
		return result;
	}

}
