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

import org.hibernate.annotations.Where;

import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.ent.EntityBase;

@Entity
@Table(name = "gm_ges_departamento")
public class GmGesDepartamento extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_ges_departamento", sequenceName = "seq_ges_departamento", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_ges_departamento", strategy = GenerationType.SEQUENCE)
	@Column(name = "dep_id")
	private Long depId;

	@OneToMany(mappedBy = "usuDepId")
	private List<GmSegUsuario> usuDepId;

	@OneToMany(mappedBy = "DepCarreraDepId")
	@Where(clause = "aud_estado = 'ACT'")
	private List<GmGesDepartamentoCarrera> DepCarreraDepId;

	@Column(name = "dep_nom_dep")
	private String depNomDep;

	public Long getDepId() {
		return depId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

	public List<GmSegUsuario> getUsuDepId() {
		return usuDepId;
	}

	public void setUsuDepId(List<GmSegUsuario> usuDepId) {
		this.usuDepId = usuDepId;
	}

	public List<GmGesDepartamentoCarrera> getDepCarreraDepId() {
		return DepCarreraDepId;
	}

	public void setDepCarreraDepId(
			List<GmGesDepartamentoCarrera> depCarreraDepId) {
		DepCarreraDepId = depCarreraDepId;
	}

	public String getDepNomDep() {
		return depNomDep;
	}

	public void setDepNomDep(String depNomDep) {
		this.depNomDep = depNomDep;
	}

}
