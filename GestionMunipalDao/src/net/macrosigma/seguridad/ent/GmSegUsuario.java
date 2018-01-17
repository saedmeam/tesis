package net.macrosigma.seguridad.ent;

import java.util.Date;
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
import javax.persistence.Transient;

import net.macrosigma.gestion.ent.GmGesDepartamento;
import net.macrosigma.gestion.ent.GmGesDepartamentoTipSolicitud;
import net.macrosigma.gestion.ent.GmGesPreguntasUsuario;
import net.macrosigma.gestion.ent.GmGesSolicitud;
import net.macrosigma.parametro.ent.GmParParametros;
import net.macrosigma.util.ent.EntityBase;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "gm_seg_usuario")
public class GmSegUsuario extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_seg_usuario", sequenceName = "seq_seg_usuario", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_seg_usuario", strategy = GenerationType.SEQUENCE)
	@Column(name = "usu_id")
	private Long usuId;

	@Column(name = "usu_usuario")
	private String usuUsuario;

	@Column(name = "usu_clave")
	private String usuClave;
	@Transient
	private String usuNomComp;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "gmSegUsuario")
	@Where(clause = "aud_estado = 'ACT'")
	private List<GmSegRolUsuario> usuRolUsuId;

	@OneToMany(mappedBy = "preUsu")
	@Where(clause = "aud_estado = 'ACT'")
	private List<GmGesPreguntasUsuario> preUsu;

	@OneToMany(mappedBy = "solUsu")
	@Where(clause = "aud_estado = 'ACT'")
	private List<GmGesSolicitud> solUsu;

	@OneToMany(mappedBy = "solUsuAsig")
	@Where(clause = "aud_estado = 'ACT'")
	private List<GmGesSolicitud> solUsuAsig;

	@OneToMany(mappedBy = "depDepUsuId")
	@Where(clause = "aud_estado = 'ACT'")
	private List<GmGesDepartamentoTipSolicitud> depDepUsuId;

	@ManyToOne
	@JoinColumn(name = "usu_dep_id")
	private GmGesDepartamento usuDepId;

	@ManyToOne
	@JoinColumn(name = "usu_carr_id")
	private GmParParametros usuCarrId;

	@Column(name = "usu_nombres")
	private String usuNombres;

	@Column(name = "usu_apellidos")
	private String usuApellidos;

	@Column(name = "usu_email")
	private String usuEmail;

	@Column(name = "usu_fecha_cambio_clave")
	private Date usuFechaCambioClave;

	@Column(name = "usu_intentos_fallidos")
	private int usuIntentosFallidos;

	public String getUsuNomComp() {
		if (usuApellidos != null)
			usuNomComp = this.usuApellidos + " " + this.usuNombres;
		else {
			usuNomComp = "";
		}
		return usuNomComp;
	}

	public void setUsuNomComp(String usuNomComp) {
		this.usuNomComp = usuNomComp;
	}

	public List<GmGesSolicitud> getSolUsu() {
		return solUsu;
	}

	public void setSolUsu(List<GmGesSolicitud> solUsu) {
		this.solUsu = solUsu;
	}

	public int getUsuIntentosFallidos() {
		return usuIntentosFallidos;
	}

	public void setUsuIntentosFallidos(int usuIntentosFallidos) {
		this.usuIntentosFallidos = usuIntentosFallidos;
	}

	public String getUsuNombres() {
		return usuNombres;
	}

	public void setUsuNombres(String usuNombres) {
		this.usuNombres = usuNombres;
	}

	public String getUsuApellidos() {
		return usuApellidos;
	}

	public void setUsuApellidos(String usuApellidos) {
		this.usuApellidos = usuApellidos;
	}

	public String getUsuEmail() {
		return usuEmail;
	}

	public void setUsuEmail(String usuEmail) {
		this.usuEmail = usuEmail;
	}

	public Long getUsuId() {
		return usuId;
	}

	public void setUsuId(Long usuId) {
		this.usuId = usuId;
	}

	public String getUsuUsuario() {
		return usuUsuario;
	}

	public void setUsuUsuario(String usuUsuario) {
		this.usuUsuario = usuUsuario;
	}

	public String getUsuClave() {
		return usuClave;
	}

	public void setUsuClave(String usuClave) {
		this.usuClave = usuClave;
	}

	public List<GmSegRolUsuario> getUsuRolUsuId() {
		return usuRolUsuId;
	}

	public void setUsuRolUsuId(List<GmSegRolUsuario> usuRolUsuId) {
		this.usuRolUsuId = usuRolUsuId;
	}

	public Date getUsuFechaCambioClave() {
		return usuFechaCambioClave;
	}

	public void setUsuFechaCambioClave(Date usuFechaCambioClave) {
		this.usuFechaCambioClave = usuFechaCambioClave;
	}

	public List<GmGesPreguntasUsuario> getPreUsu() {
		return preUsu;
	}

	public void setPreUsu(List<GmGesPreguntasUsuario> preUsu) {
		this.preUsu = preUsu;
	}

	public GmGesDepartamento getUsuDepId() {
		return usuDepId;
	}

	public void setUsuDepId(GmGesDepartamento usuDepId) {
		this.usuDepId = usuDepId;
	}

	public GmParParametros getUsuCarrId() {
		return usuCarrId;
	}

	public void setUsuCarrId(GmParParametros usuCarrId) {
		this.usuCarrId = usuCarrId;
	}

	public List<GmGesDepartamentoTipSolicitud> getDepDepUsuId() {
		return depDepUsuId;
	}

	public void setDepDepUsuId(List<GmGesDepartamentoTipSolicitud> depDepUsuId) {
		this.depDepUsuId = depDepUsuId;
	}

	public List<GmGesSolicitud> getSolUsuAsig() {
		return solUsuAsig;
	}

	public void setSolUsuAsig(List<GmGesSolicitud> solUsuAsig) {
		this.solUsuAsig = solUsuAsig;
	}

}
