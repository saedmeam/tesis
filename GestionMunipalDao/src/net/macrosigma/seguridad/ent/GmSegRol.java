package net.macrosigma.seguridad.ent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.macrosigma.util.ent.EntityBase;

@Entity
@Table(name = "gm_seg_rol")
public class GmSegRol extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_seg_rol", sequenceName = "seq_seg_rol", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_seg_rol", strategy = GenerationType.SEQUENCE)
	@Column(name = "rol_id")
	private Long rolId;

	@Column(name = "rol_nombre")
	private String rolNombre;

	@Column(name = "rol_descripcion")
	private String rolDescripcion;

	@ManyToOne
	@JoinColumn(name = "rol_rol_usu_id")
	private GmSegRolUsuario rolRolUsuId;

	public String getRolDescripcion() {
		return rolDescripcion;
	}

	public void setRolDescripcion(String rolDescripcion) {
		this.rolDescripcion = rolDescripcion;
	}

	public Long getRolId() {
		return rolId;
	}

	public void setRolId(Long rolId) {
		this.rolId = rolId;
	}

	public String getRolNombre() {
		return rolNombre;
	}

	public void setRolNombre(String rolNombre) {
		this.rolNombre = rolNombre;
	}

	public GmSegRolUsuario getRolRolUsuId() {
		return rolRolUsuId;
	}

	public void setRolRolUsuId(GmSegRolUsuario rolRolUsuId) {
		this.rolRolUsuId = rolRolUsuId;
	}

}
