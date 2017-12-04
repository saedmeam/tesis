package net.macrosigma.parametro.ent;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import net.macrosigma.gestion.ent.GmGesDepartamentoCarrera;
import net.macrosigma.gestion.ent.GmGesSolicitud;
import net.macrosigma.gestion.ent.GmGesSolicitudRequisitoDocumento;
import net.macrosigma.util.ent.EntityBase;

@Entity
@Table(name = "gm_par_parametros")
public class GmParParametros extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_par_parametros", sequenceName = "sec_par_parametros", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_par_parametros", strategy = GenerationType.SEQUENCE)
	@Column(name = "par_id")
	private Long par_id;

	@Column(name = "par_codigo")
	private String parCodigo;

	@Column(name = "par_valor")
	private String parValor;

	@Column(name = "par_des")
	private String parDes;

	@OneToMany(mappedBy = "carIdPad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Where(clause = "aud_estado = 'ACT'")
	private List<GmParParametros> carIdHij;

	@OneToMany(mappedBy = "solCarrera")
	@Where(clause = "aud_estado = 'ACT'")
	private List<GmGesSolicitud> solCarrera;

	@OneToMany(mappedBy = "depCarreraId")
	@Where(clause = "aud_estado = 'ACT'")
	private List<GmGesDepartamentoCarrera> depCarreraId;

	@OneToMany(mappedBy = "solTipoSolicitud")
	@Where(clause = "aud_estado = 'ACT'")
	private List<GmGesSolicitud> solTipoSolicitud;
	
	@OneToMany(mappedBy = "solReqTipSol")
	@Where(clause = "aud_estado = 'ACT'")
	private List<GmGesSolicitudRequisitoDocumento> solReqTipSol;

	public List<GmGesSolicitudRequisitoDocumento> getSolReqTipSol() {
		return solReqTipSol;
	}

	public void setSolReqTipSol(
			List<GmGesSolicitudRequisitoDocumento> solReqTipSol) {
		this.solReqTipSol = solReqTipSol;
	}

	public List<GmGesSolicitud> getSolCarrera() {
		return solCarrera;
	}

	public void setSolCarrera(List<GmGesSolicitud> solCarrera) {
		this.solCarrera = solCarrera;
	}

	public List<GmGesSolicitud> getSolTipoSolicitud() {
		return solTipoSolicitud;
	}

	public void setSolTipoSolicitud(List<GmGesSolicitud> solTipoSolicitud) {
		this.solTipoSolicitud = solTipoSolicitud;
	}

	@ManyToOne
	@JoinColumn(name = "car_id_pad")
	private GmParParametros carIdPad;

	public GmParParametros getCarIdPad() {
		return carIdPad;
	}

	public void setCarIdPad(GmParParametros carIdPad) {
		this.carIdPad = carIdPad;
	}

	public List<GmParParametros> getCarIdHij() {
		return carIdHij;
	}

	public void setCarIdHij(List<GmParParametros> carIdHij) {
		this.carIdHij = carIdHij;
	}

	public String getParDes() {
		return parDes;
	}

	public void setParDes(String parDes) {
		this.parDes = parDes;
	}

	public Long getPar_id() {
		return par_id;
	}

	public void setPar_id(Long par_id) {
		this.par_id = par_id;
	}

	public String getParCodigo() {
		return parCodigo;
	}

	public void setParCodigo(String parCodigo) {
		this.parCodigo = parCodigo;
	}

	public String getParValor() {
		return parValor;
	}

	public void setParValor(String parValor) {
		this.parValor = parValor;
	}

	public List<GmGesDepartamentoCarrera> getDepCarreraId() {
		return depCarreraId;
	}

	public void setDepCarreraId(List<GmGesDepartamentoCarrera> depCarreraId) {
		this.depCarreraId = depCarreraId;
	}

}
