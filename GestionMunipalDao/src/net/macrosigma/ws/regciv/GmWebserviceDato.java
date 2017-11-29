package net.macrosigma.ws.regciv;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.macrosigma.util.ent.EntityBase;


@Entity
@Table(name = "gm_wsdato")
public class GmWebserviceDato extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_wsdata", sequenceName = "seq_wsdata", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_wsdata")
	@Column(name = "wsd_id")
	private Long wsdid;

	@Column(name = "wsd_url")
	private String wsdUrl;

	@ManyToOne
	@JoinColumn(name = "ws_id")
	private GmUsuarioServiciosWs usuarioWs;

	@Column(name = "wsd_entidad")
	@Enumerated(EnumType.STRING)
	private EntidadWSEnum wsdEntidad;

	@Column(name = "wsd_Usuario")
	private String wsUsuario;

	@Column(name = "wsd_pwd")
	private String wsPwd;

	public Long getWsdid() {
		return wsdid;
	}

	public void setWsdid(Long wsdid) {
		this.wsdid = wsdid;
	}

	public String getWsdUrl() {
		return wsdUrl;
	}

	public void setWsdUrl(String wsdUrl) {
		this.wsdUrl = wsdUrl;
	}

	public GmUsuarioServiciosWs getUsuarioWs() {
		return usuarioWs;
	}

	public void setUsuarioWs(GmUsuarioServiciosWs usuarioWs) {
		this.usuarioWs = usuarioWs;
	}

	public EntidadWSEnum getWsdEntidad() {
		return wsdEntidad;
	}

	public void setWsdEntidad(EntidadWSEnum wsdEntidad) {
		this.wsdEntidad = wsdEntidad;
	}

	public String getWsUsuario() {
		return wsUsuario;
	}

	public void setWsUsuario(String wsUsuario) {
		this.wsUsuario = wsUsuario;
	}

	public String getWsPwd() {
		return wsPwd;
	}

	public void setWsPwd(String wsPwd) {
		this.wsPwd = wsPwd;
	}

}
