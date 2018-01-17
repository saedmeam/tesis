package net.macrosigma.gestion.ent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.ent.EntityBase;

@Entity
@Table(name = "gm_ges_proceso_solicitud")
public class GmGesProcesoSolicitud extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_gm_ges_proceso_solicitud", sequenceName = "seq_gm_ges_proceso_solicitud", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_gm_ges_proceso_solicitud", strategy = GenerationType.SEQUENCE)
	@Column(name = "sol_id")
	private Long solId;

	@ManyToOne
	@JoinColumn(name = "proc_sol_id_usu_ant")
	private GmSegUsuario procSolUsuAnt;

	@ManyToOne
	@JoinColumn(name = "proc_sol_id_usu_act")
	private GmSegUsuario procSolUsuAct;

	@ManyToOne
	@JoinColumn(name = "proc_sol_sol_id")
	private GmGesSolicitud procSolSolid;

	@Column(name = "proc_sol_estado")
	private String procSolEstado;

	@Column(name = "proc_sol_tip_proceso")
	private String procSolTipProceso;

	@Column(name = "proc_sol_obs")
	private String procSolObs;
	@Transient
	private String strFecha;

	public String getStrFecha() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyyy HH:mm");
		strFecha = df.format(this.getFechaIngreso());
		return strFecha;
	}

	public void setStrFecha(String strFecha) {
		this.strFecha = strFecha;
	}

	public Long getSolId() {
		return solId;
	}

	public void setSolId(Long solId) {
		this.solId = solId;
	}

	public GmSegUsuario getProcSolUsuAnt() {
		return procSolUsuAnt;
	}

	public void setProcSolUsuAnt(GmSegUsuario procSolUsuAnt) {
		this.procSolUsuAnt = procSolUsuAnt;
	}

	public GmSegUsuario getProcSolUsuAct() {
		return procSolUsuAct;
	}

	public void setProcSolUsuAct(GmSegUsuario procSolUsuAct) {
		this.procSolUsuAct = procSolUsuAct;
	}

	public GmGesSolicitud getProcSolSolid() {
		return procSolSolid;
	}

	public void setProcSolSolid(GmGesSolicitud procSolSolid) {
		this.procSolSolid = procSolSolid;
	}

	public String getProcSolEstado() {
		return procSolEstado;
	}

	public void setProcSolEstado(String procSolEstado) {
		this.procSolEstado = procSolEstado;
	}

	public String getProcSolTipProceso() {
		return procSolTipProceso;
	}

	public void setProcSolTipProceso(String procSolTipProceso) {
		this.procSolTipProceso = procSolTipProceso;
	}

	public String getProcSolObs() {
		return procSolObs;
	}

	public void setProcSolObs(String procSolObs) {
		this.procSolObs = procSolObs;
	}

}
