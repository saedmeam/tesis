package net.macrosigma.seguridad.ent;

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

import org.hibernate.annotations.Where;

import net.macrosigma.util.ent.EntityBase;

@Entity
@Table(name = "gm_seg_rol_menu")
public class GmSegRolMenu extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_seg_rol_menu", sequenceName = "seq_seg_rol_menu", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_seg_rol_menu", strategy = GenerationType.SEQUENCE)
	@Column(name = "rol_men_id")
	private Long rolMenId;
    
	@ManyToOne
	@JoinColumn(name = "id_menu")
	private GmSegMenu menu;

	@ManyToOne
	@JoinColumn(name = "id_rol")
	private GmSegRol rol;

	@OneToMany(mappedBy = "rolMenu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Where(clause = "aud_estado = 'ACT'")
	private List<GmSegRolMenuAccion> rolesAccesos;

	public Long getRolMenId() {
		return rolMenId;
	}

	public void setRolMenId(Long rolMenId) {
		this.rolMenId = rolMenId;
	}

	public GmSegMenu getMenu() {
		return menu;
	}

	public void setMenu(GmSegMenu menu) {
		this.menu = menu;
	}

	public GmSegRol getRol() {
		return rol;
	}

	public void setRol(GmSegRol rol) {
		this.rol = rol;
	}

	public List<GmSegRolMenuAccion> getRolesAccesos() {
		return rolesAccesos;
	}

	public void setRolesAccesos(List<GmSegRolMenuAccion> rolesAccesos) {
		this.rolesAccesos = rolesAccesos;
	}
	
	

}
