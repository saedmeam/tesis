package net.macrosigma.gestion.dao;

import javax.persistence.EntityTransaction;

import net.macrosigma.gestion.ent.GmGesPreguntasUsuario;
import net.macrosigma.util.dao.GenericDao;

public class GmGesPreguntaUsuarioDao extends
		GenericDao<GmGesPreguntasUsuario, Long> {

	public GmGesPreguntaUsuarioDao() {
		super(GmGesPreguntasUsuario.class);
	}

	public void crear(GmGesPreguntasUsuario pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmGesPreguntasUsuario pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmGesPreguntasUsuario pers) {
		super.eliminar(pers);
	}

	// @SuppressWarnings("unchecked")
	// public static List<GmGesPreguntasUsuario> getValPrebyPre(String claCat) {
	// StringBuilder sql = new StringBuilder();
	// String select = "";
	// select =
	// "select o from GmGesValorPredio o where (o.valPrePreId = :claCat or :claCat =null)";
	// select += "and o.estado = 'ACT'";
	// sql.append(select);
	// Query query = em.createQuery(sql.toString());
	// query.setParameter("claCat", claCat);
	//
	// List<GmGesPreguntasUsuario> result = query.getResultList();
	// return result;
	// }

}
