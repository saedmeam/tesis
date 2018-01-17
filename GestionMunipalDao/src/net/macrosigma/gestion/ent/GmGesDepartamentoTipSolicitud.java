package net.macrosigma.gestion.ent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.macrosigma.parametro.ent.GmParParametros;
import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.ent.EntityBase;

@Entity
@Table(name = "gm_ges_departamento_tip_solicitud")
public class GmGesDepartamentoTipSolicitud extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_ges_departamento_tip_solicitud", sequenceName = "seq_ges_departamento_tip_solicitud", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_ges_departamento_tip_solicitud", strategy = GenerationType.SEQUENCE)
	@Column(name = "ins_id")
	private Long insId;

	@ManyToOne
	@JoinColumn(name = "dep_tip_sol_dep_id")
	private GmParParametros depTipSolTipSolId;

	@ManyToOne
	@JoinColumn(name = "dep_tip_sol_carr_id")
	private GmParParametros depTipSolCarreraId;

	@ManyToOne
	@JoinColumn(name = "dep_dep_usu_id")
	private GmSegUsuario depDepUsuId;

	public GmParParametros getDepTipSolTipSolId() {
		return depTipSolTipSolId;
	}

	public void setDepTipSolTipSolId(GmParParametros depTipSolTipSolId) {
		this.depTipSolTipSolId = depTipSolTipSolId;
	}

	public Long getInsId() {
		return insId;
	}

	public void setInsId(Long insId) {
		this.insId = insId;
	}

	public GmParParametros getDepTipSolCarreraId() {
		return depTipSolCarreraId;
	}

	public void setDepTipSolCarreraId(GmParParametros depTipSolCarreraId) {
		this.depTipSolCarreraId = depTipSolCarreraId;
	}

	public GmSegUsuario getDepDepUsuId() {
		return depDepUsuId;
	}

	public void setDepDepUsuId(GmSegUsuario depDepUsuId) {
		this.depDepUsuId = depDepUsuId;
	}

}
