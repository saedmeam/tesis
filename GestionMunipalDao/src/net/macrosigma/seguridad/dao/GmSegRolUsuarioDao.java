package net.macrosigma.seguridad.dao;

import javax.persistence.EntityTransaction;

import net.macrosigma.seguridad.ent.GmSegRolUsuario;
import net.macrosigma.util.dao.GenericDao;

public class GmSegRolUsuarioDao extends GenericDao<GmSegRolUsuario, Long> {

	public GmSegRolUsuarioDao() {
		super(GmSegRolUsuario.class);
	}

	public void crear(GmSegRolUsuario pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmSegRolUsuario pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmSegRolUsuario pers) {
		super.eliminar(pers);
	}

}
