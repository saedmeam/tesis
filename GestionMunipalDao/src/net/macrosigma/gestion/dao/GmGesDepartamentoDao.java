package net.macrosigma.gestion.dao;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.gestion.ent.GmGesDepartamento;
import net.macrosigma.gestion.ent.GmGesPreguntaFrecuente;
import net.macrosigma.util.dao.GenericDao;

public class GmGesDepartamentoDao extends
		GenericDao<GmGesDepartamento, Long> {

	public GmGesDepartamentoDao() {
		super(GmGesDepartamento.class);
	}

	public void crear(GmGesDepartamento pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmGesDepartamento pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmGesDepartamento pers) {
		super.eliminar(pers);
	}

	@SuppressWarnings("unchecked")
	public static List<GmGesPreguntaFrecuente> getValPrebyPre(String claCat) {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesDepartamento o where (o.desPregunta = :claCat or :claCat =null)";
		select += "and o.estado = 'ACT'";
		sql.append(select);
		Query query = em.createQuery(sql.toString());
		query.setParameter("claCat", claCat);

		List<GmGesPreguntaFrecuente> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<GmGesDepartamento> getPreFreAct() {
		StringBuilder sql = new StringBuilder();
		String select = "";
		select = "select o from GmGesDepartamento o where o.estado = 'ACT'";
		sql.append(select);
		Query query = em.createQuery(sql.toString());

		List<GmGesDepartamento> result = query.getResultList();
		return result;
	}

}
