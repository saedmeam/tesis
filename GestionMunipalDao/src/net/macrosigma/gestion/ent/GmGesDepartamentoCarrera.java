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
@Table(name = "gm_ges_departamento_carrera")
public class GmGesDepartamentoCarrera extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_ges_departamento_carrera", sequenceName = "seq_ges_departamento_carrera", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_ges_departamento_carrera", strategy = GenerationType.SEQUENCE)
	@Column(name = "ins_id")
	private Long insId;

	@ManyToOne
	@JoinColumn(name = "dep_carrera_id")
	private GmParParametros depCarreraId;

	@ManyToOne
	@JoinColumn(name = "dep_carrera_dep_id")
	private GmGesDepartamento DepCarreraDepId;

	public Long getInsId() {
		return insId;
	}

	public void setInsId(Long insId) {
		this.insId = insId;
	}

	public GmParParametros getDepCarreraId() {
		return depCarreraId;
	}

	public void setDepCarreraId(GmParParametros depCarreraId) {
		this.depCarreraId = depCarreraId;
	}

	public GmGesDepartamento getDepCarreraDepId() {
		return DepCarreraDepId;
	}

	public void setDepCarreraDepId(GmGesDepartamento depCarreraDepId) {
		DepCarreraDepId = depCarreraDepId;
	}

}
