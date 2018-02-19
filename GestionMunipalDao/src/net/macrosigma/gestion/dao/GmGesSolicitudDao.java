package net.macrosigma.gestion.dao;

import java.util.Date;
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
	public static List<GmGesSolicitud> getSolbyTipSolValEst(GmSegUsuario usu,
			GmParParametros tipSol) {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesSolicitud o where (o.solTipoSolicitud = :tipSol or :tipSol =null)";
		select += "and (o.solUsu = :usu or :usu = null)";
		select += "and o.estado = 'ACT'";
		select += "and o.solEstado in ('ING','OBS','PRO')";

		sql.append(select);
		Query query = em.createQuery(sql.toString());
		query.setParameter("tipSol", tipSol);
		query.setParameter("usu", usu);

		List<GmGesSolicitud> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<GmGesSolicitud> getPreFreAct(GmSegUsuario usu, String id,
			String res, String tipsol, String carr, String estado, Date fecdes,
			Date fechas) {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesSolicitud o where o.estado = 'ACT' ";
		if (usu != null)
			select += "and (o.solUsu = :usu or :usu = null)";
		if (id != null)
			select += " and o.solUsu.usuUsuario like '%'||:id||'%' ";
		if (res != null)
			select += " and concat(o.solUsuAsig.usuNombres,' ',o.solUsuAsig.usuApellidos ) like '%'||:res||'%' ";
		if (tipsol != null)
			select += " and o.solTipoSolicitud.par_id = :tipsol ";
		if (carr != null)
			select += " and o.solCarrera.par_id = :carr ";
		if (estado != null)
			select += " and o.solEstado= :estado ";
		if (fecdes != null)
			select += " and to_date(to_char(o.fechaIngreso,'ddmmyyyy'),'ddmmyyyy') >= :fecdes ";
		if (fechas != null)
			select += " and to_date(to_char(o.fechaIngreso,'ddmmyyyy'),'ddmmyyyy') <= :fechas ";
		sql.append(select);
		Query query = em.createQuery(sql.toString());
		if (usu != null)
			query.setParameter("usu", usu);
		if (id != null)
			query.setParameter("id", id);
		if (res != null)
			query.setParameter("res", res);
		if (tipsol != null)
			query.setParameter("tipsol", Long.parseLong(tipsol));
		if (carr != null)
			query.setParameter("carr", Long.parseLong(carr));
		if (estado != null)
			query.setParameter("estado", estado);
		if (fecdes != null)
			query.setParameter("fecdes", fecdes);
		if (fechas != null)
			query.setParameter("fechas", fechas);
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
