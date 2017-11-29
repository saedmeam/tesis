package net.macrosigma.parametro.dao;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.parametro.ent.GmParPolitSeguridadBean;
import net.macrosigma.util.dao.GenericDao;

public class GmParPolitSeguridadDao extends
		GenericDao<GmParPolitSeguridadBean, Long> {

	public GmParPolitSeguridadDao() {
		super(GmParPolitSeguridadBean.class);
	}

	public void crear(GmParPolitSeguridadBean pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmParPolitSeguridadBean pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmParPolitSeguridadBean pers) {
		super.eliminar(pers);
	}

	@SuppressWarnings("unchecked")
	public GmParPolitSeguridadBean getPolSegAct() {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmParPolitSeguridadBean o where o.estado = 'ACT'");
		Query query = em.createQuery(sql.toString());
		List<GmParPolitSeguridadBean> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result.get(0);
	}

	public String getContPolitSeg() {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(o) from GmParPolitSeguridadBean o where o.estado = 'ACT'");
		Query query = em.createQuery(sql.toString());
		String res = query.getResultList().toString();
		// long result = Long.parseLong(res);
		return res;
	}

}
