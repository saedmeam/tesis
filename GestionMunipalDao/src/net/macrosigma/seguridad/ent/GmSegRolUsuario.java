package net.macrosigma.seguridad.ent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.macrosigma.util.ent.EntityBase;

@Entity
@Table(name = "gm_seg_rol_usuario")
public class GmSegRolUsuario extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_seg_rol_usuario", sequenceName = "seq_seg_rol_usuario", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_seg_rol_usuario", strategy = GenerationType.SEQUENCE)
	@Column(name = "rol_usu_id")
	private Long rolUsuId;

	@ManyToOne
    @JoinColumn(name = "id_usuario")
    private GmSegUsuario gmSegUsuario;

    @OneToOne
    @JoinColumn(name = "id_rol")
    private GmSegRol gmSegRol;

	public Long getRolUsuId() {
		return rolUsuId;
	}

	public void setRolUsuId(Long rolUsuId) {
		this.rolUsuId = rolUsuId;
	}

	public GmSegUsuario getGmSegUsuario() {
		return gmSegUsuario;
	}

	public void setGmSegUsuario(GmSegUsuario gmSegUsuario) {
		this.gmSegUsuario = gmSegUsuario;
	}

	public GmSegRol getGmSegRol() {
		return gmSegRol;
	}

	public void setGmSegRol(GmSegRol gmSegRol) {
		this.gmSegRol = gmSegRol;
	}

}
