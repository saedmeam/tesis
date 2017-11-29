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
@Table(name = "GM_SEG_ROL_MENU_ACCION")
public class GmSegRolMenuAccion extends EntityBase {

    @Id
    @SequenceGenerator(name = "sec_seg_rol_menu_accion", sequenceName = "seq_seg_rol_menu_accion", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "sec_seg_rol_menu_accion", strategy = GenerationType.SEQUENCE)
    @Column(name = "id_rol_menu_accion")
    private Long idRolMenuAccion;

    @ManyToOne
    @JoinColumn(name = "id_rol_menu")
    private GmSegRolMenu rolMenu;

    @ManyToOne
    @JoinColumn(name = "id_accion")
    private GmSegAcciones accion;

	public Long getIdRolMenuAccion() {
		return idRolMenuAccion;
	}

	public void setIdRolMenuAccion(Long idRolMenuAccion) {
		this.idRolMenuAccion = idRolMenuAccion;
	}

	public GmSegRolMenu getRolMenu() {
		return rolMenu;
	}

	public void setRolMenu(GmSegRolMenu rolMenu) {
		this.rolMenu = rolMenu;
	}

	public GmSegAcciones getAccion() {
		return accion;
	}

	public void setAccion(GmSegAcciones accion) {
		this.accion = accion;
	}

   
}
