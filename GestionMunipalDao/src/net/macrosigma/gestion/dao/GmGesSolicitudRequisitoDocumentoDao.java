package net.macrosigma.gestion.dao;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.gestion.ent.GmGesSolicitudRequisitoDocumento;
import net.macrosigma.util.dao.GenericDao;

public class GmGesSolicitudRequisitoDocumentoDao extends
		GenericDao<GmGesSolicitudRequisitoDocumento, Long> {

	public GmGesSolicitudRequisitoDocumentoDao() {
		super(GmGesSolicitudRequisitoDocumento.class);
	}

	public void crear(GmGesSolicitudRequisitoDocumento pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmGesSolicitudRequisitoDocumento pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmGesSolicitudRequisitoDocumento pers) {
		super.eliminar(pers);
	}

	@SuppressWarnings("unchecked")
	public static List<GmGesSolicitudRequisitoDocumento> getValPrebyPre(String claCat) {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesPreguntasFrecuentes o where (o.desPregunta = :claCat or :claCat =null)";
		select += "and o.estado = 'ACT'";
		sql.append(select);
		Query query = em.createQuery(sql.toString());
		query.setParameter("claCat", claCat);

		List<GmGesSolicitudRequisitoDocumento> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<GmGesSolicitudRequisitoDocumento> getPreFreAct() {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesSolicitudRequisitoDocumento o where o.estado = 'ACT'";
		sql.append(select);
		Query query = em.createQuery(sql.toString());

		List<GmGesSolicitudRequisitoDocumento> result = query.getResultList();
		return result;
	}

}
