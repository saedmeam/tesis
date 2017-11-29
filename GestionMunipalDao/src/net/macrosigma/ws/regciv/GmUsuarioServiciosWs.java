package net.macrosigma.ws.regciv;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.macrosigma.util.ent.EntityBase;



@Entity
@Table(name = "gm_UsuarioWs")
public class GmUsuarioServiciosWs extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_usuario_srv", sequenceName = "seq_usuario_srv", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_usuario_srv")
	@Column(name = "ws_id")
	private Long wsId;

	@Column(name = "ws_Usuario")
	private String wsUsuario;

	@Column(name = "ws_pwd")
	private String wsPwd;

	@Enumerated(EnumType.STRING)
	@Column(name = "ws_tipows", length = 10)
	private TipoWSenum tipoWs;

	public Long getWsId() {
		return wsId;
	}

	public void setWsId(Long wsId) {
		this.wsId = wsId;
	}

	public String getWsUsuario() {
		return wsUsuario;
	}

	public void setWsUsuario(String wsUsuario) {
		this.wsUsuario = wsUsuario;
	}

	public TipoWSenum getTipoWs() {
		return tipoWs;
	}

	public void setTipoWs(TipoWSenum tipoWs) {
		this.tipoWs = tipoWs;
	}

	public String getWsPwd() {
		return wsPwd;
	}

	public void setWsPwd(String wsPwd) {
		this.wsPwd = wsPwd;
	}

}
