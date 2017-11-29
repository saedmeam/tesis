package net.macrosigma.seguridad.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import net.macrosigma.seguridad.ent.GmSegAcciones;
import net.macrosigma.util.dao.GenericDao;

@Stateless
public class GmSegAccionesDao extends GenericDao<GmSegAcciones, Long> {
	private StringBuilder sql;

	public GmSegAccionesDao() {
		super(GmSegAcciones.class);
	}

	public void crear(GmSegAcciones pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmSegAcciones pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmSegAcciones pers) {
		super.eliminar(pers);
	}

	public List<GmSegAcciones> listarAccesos() {
		sql = new StringBuilder("from GmSegAcciones o ");
		sql.append("where o.estado= 'ACT' ");
		TypedQuery<GmSegAcciones> query = em.createQuery(sql.toString(),
				GmSegAcciones.class);
		return query.getResultList();
	}

}
