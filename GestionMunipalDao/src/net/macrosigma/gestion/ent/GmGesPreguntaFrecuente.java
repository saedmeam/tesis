package net.macrosigma.gestion.ent;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.macrosigma.util.ent.EntityBase;

@Entity
@Table(name = "gm_ges_preguntas_frecuente")
public class GmGesPreguntaFrecuente extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_ges_preguntas_frecuentes", sequenceName = "seq_ges_preguntas_frecuentes", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_ges_preguntas_frecuentes", strategy = GenerationType.SEQUENCE)
	@Column(name = "ins_id")
	private Long insId;

	@Column(name = "des_pregunta")
	private String desPregunta;

	@OneToMany(mappedBy = "preFreUsu")
	private List<GmGesPreguntasUsuario> preFreUsu;

	public Long getInsId() {
		return insId;
	}

	public void setInsId(Long insId) {
		this.insId = insId;
	}

	public String getDesPregunta() {
		return desPregunta;
	}

	public void setDesPregunta(String desPregunta) {
		this.desPregunta = desPregunta;
	}

	public List<GmGesPreguntasUsuario> getPreFreUsu() {
		return preFreUsu;
	}

	public void setPreFreUsu(List<GmGesPreguntasUsuario> preFreUsu) {
		this.preFreUsu = preFreUsu;
	}

}
