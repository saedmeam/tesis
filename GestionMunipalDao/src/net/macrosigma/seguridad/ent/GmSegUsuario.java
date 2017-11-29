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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.macrosigma.gestion.ent.GmGesPreguntasUsuario;
import net.macrosigma.gestion.ent.GmGesSolicitud;
import net.macrosigma.util.ent.EntityBase;

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

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "gmSegUsuario")
	private List<GmSegRolUsuario> usuRolUsuId;

	@OneToMany(mappedBy = "preUsu")
	private List<GmGesPreguntasUsuario> preUsu;

	@OneToMany(mappedBy = "solUsu")
	private List<GmGesSolicitud> solUsu;

	public List<GmGesSolicitud> getSolUsu() {
		return solUsu;
	}

	public void setSolUsu(List<GmGesSolicitud> solUsu) {
		this.solUsu = solUsu;
	}

	@Column(name = "usu_nombres")
	private String usuNombres;

	@Column(name = "usu_apellidos")
	private String usuApellidos;

	@Column(name = "usu_departamento")
	private String usudepartamento;

	@Column(name = "usu_email")
	private String usuEmail;

	@Column(name = "usu_fecha_cambio_clave")
	private Date usuFechaCambioClave;

	@Column(name = "usu_intentos_fallidos")
	private int usuIntentosFallidos;

	public int getUsuIntentosFallidos() {
		return usuIntentosFallidos;
	}

	public void setUsuIntentosFallidos(int usuIntentosFallidos) {
		this.usuIntentosFallidos = usuIntentosFallidos;
	}

	public String getUsudepartamento() {
		return usudepartamento;
	}

	public void setUsudepartamento(String usudepartamento) {
		this.usudepartamento = usudepartamento;
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

}
