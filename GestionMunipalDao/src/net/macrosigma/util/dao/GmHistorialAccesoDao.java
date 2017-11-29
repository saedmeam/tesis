package net.macrosigma.util.dao;

import javax.persistence.EntityTransaction;

import net.macrosigma.util.ent.GmHistorialAcceso;

public class GmHistorialAccesoDao extends GenericDao<GmHistorialAcceso, Long> {

	public GmHistorialAccesoDao() {
		super(GmHistorialAcceso.class);
	}

	public void crear(GmHistorialAcceso pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmHistorialAcceso pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmHistorialAcceso pers) {
		super.eliminar(pers);
	}

}
