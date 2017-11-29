package net.macrosigma.ws.regciv;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import net.macrosigma.util.dao.GenericDao;

@Stateless
public class AccesoWSDao extends GenericDao<GmWebserviceDato, Long>{

	public AccesoWSDao() {
		super(GmWebserviceDato.class);
	}

	@SuppressWarnings("unchecked")
	public GmWebserviceDato getAccesobyEntidadServicioProveedor(TipoWSenum wsProvider, EntidadWSEnum entidadws) {
		StringBuilder sql = new StringBuilder("from GmWebserviceDato o ");
		sql.append("where o.wsdEntidad=:ent ");
		sql.append("and o.usuarioWs.tipoWs=:proveed ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("ent", entidadws);
		query.setParameter("proveed", wsProvider);
		List<GmWebserviceDato> result = query.getResultList();
		if (!result.isEmpty())
			return result.get(0);
		return null;
	}

	public GmWebserviceDato getAutenticidad(EntidadWSEnum ent) {
		StringBuilder sql = new StringBuilder("from GmWebserviceDato o ");
		sql.append("where o.wsdEntidad=:entidad ");
		sql.append("and o.estado= 'ACT' ");
		Query query = em.createQuery(sql.toString());
		query.setParameter("entidad", ent);
		try {
			return (GmWebserviceDato) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
