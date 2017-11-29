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

import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.ent.EntityBase;

@Entity
@Table(name = "gm_ges_preguntas_usuario")
public class GmGesPreguntasUsuario extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_ges_preguntas_usuario", sequenceName = "seq_ges_preguntas_usuario", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_ges_preguntas_usuario", strategy = GenerationType.SEQUENCE)
	@Column(name = "ins_id")
	private Long insId;

	@ManyToOne
	@JoinColumn(name = "pre_usu_id")
	private GmSegUsuario preUsu;

	@ManyToOne
	@JoinColumn(name = "pre_fre_usu_id")
	private GmGesPreguntaFrecuente preFreUsu;

	@Column(name = "resp_preg")
	private String resPreg;

	public String getResPreg() {
		return resPreg;
	}

	public void setResPreg(String resPreg) {
		this.resPreg = resPreg;
	}

	public Long getInsId() {
		return insId;
	}

	public void setInsId(Long insId) {
		this.insId = insId;
	}

	public GmSegUsuario getPreUsu() {
		return preUsu;
	}

	public void setPreUsu(GmSegUsuario preUsu) {
		this.preUsu = preUsu;
	}

	public GmGesPreguntaFrecuente getPreFreUsu() {
		return preFreUsu;
	}

	public void setPreFreUsu(GmGesPreguntaFrecuente preFreUsu) {
		this.preFreUsu = preFreUsu;
	}

}
