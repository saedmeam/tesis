package net.macrosigma.gestion.ent;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.macrosigma.parametro.ent.GmParParametros;
import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.ent.EntityBase;

@Entity
@Table(name = "gm_ges_solicitud")
public class GmGesSolicitud extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_gm_ges_solicitud", sequenceName = "seq_gm_ges_solicitud", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_gm_ges_solicitud", strategy = GenerationType.SEQUENCE)
	@Column(name = "sol_id")
	private Long solId;

	@ManyToOne
	@JoinColumn(name = "sol_id_usu")
	private GmSegUsuario solUsu;

	@ManyToOne
	@JoinColumn(name = "sol_id_carrera")
	private GmParParametros solCarrera;

	@ManyToOne
	@JoinColumn(name = "sol_id_tipo_solicitud")
	private GmSegUsuario solTipoSolicitud;

	@OneToMany(mappedBy = "solReqDoc")
	private List<GmGesSolicitudRequisitoDocumento> solReqDoc;

	public Long getSolId() {
		return solId;
	}

	public void setSolId(Long solId) {
		this.solId = solId;
	}

	public GmSegUsuario getSolUsu() {
		return solUsu;
	}

	public void setSolUsu(GmSegUsuario solUsu) {
		this.solUsu = solUsu;
	}

	public GmParParametros getSolCarrera() {
		return solCarrera;
	}

	public void setSolCarrera(GmParParametros solCarrera) {
		this.solCarrera = solCarrera;
	}

	public GmSegUsuario getSolTipoSolicitud() {
		return solTipoSolicitud;
	}

	public void setSolTipoSolicitud(GmSegUsuario solTipoSolicitud) {
		this.solTipoSolicitud = solTipoSolicitud;
	}

	public List<GmGesSolicitudRequisitoDocumento> getSolReqDoc() {
		return solReqDoc;
	}

	public void setSolReqDoc(List<GmGesSolicitudRequisitoDocumento> solReqDoc) {
		this.solReqDoc = solReqDoc;
	}

}
