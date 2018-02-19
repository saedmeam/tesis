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
import net.macrosigma.util.ent.EntityBase;

@Entity
@Table(name = "gm_ges_contenido_tip_solicitud")
public class GmGesContenidoTipSolicitud extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_ges_contenido_tip_solicitud", sequenceName = "seq_ges_contenido_tip_solicitud", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_ges_contenido_tip_solicitud", strategy = GenerationType.SEQUENCE)
	@Column(name = "ins_id")
	private Long insId;

	@Column(name = "con_tip_sol_nom_rep")
	private String conTipSolNomRep;

	@Column(name = "con_tip_sol_contenido")
	private String conTipSolContenido;

	@ManyToOne
	@JoinColumn(name = "cont_tip_sol_id")
	private GmParParametros contTipSolId;

	public Long getInsId() {
		return insId;
	}

	public void setInsId(Long insId) {
		this.insId = insId;
	}

	public String getConTipSolNomRep() {
		return conTipSolNomRep;
	}

	public void setConTipSolNomRep(String conTipSolNomRep) {
		this.conTipSolNomRep = conTipSolNomRep;
	}

	public String getConTipSolContenido() {
		return conTipSolContenido;
	}

	public void setConTipSolContenido(String conTipSolContenido) {
		this.conTipSolContenido = conTipSolContenido;
	}

	public GmParParametros getContTipSolId() {
		return contTipSolId;
	}

	public void setContTipSolId(GmParParametros contTipSolId) {
		this.contTipSolId = contTipSolId;
	}

}
