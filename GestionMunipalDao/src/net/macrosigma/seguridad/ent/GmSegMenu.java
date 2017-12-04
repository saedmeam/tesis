package net.macrosigma.seguridad.ent;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "gm_seg_menu")
public class GmSegMenu extends EntityBase {

	    @Id
	    @SequenceGenerator(name = "sec_menu", sequenceName = "seq_menu", allocationSize = 1)
	    @GeneratedValue(generator = "sec_menu", strategy = GenerationType.SEQUENCE)
	    @Column(name = "men_id")
	    private Long menId;

	    @Column(name = "men_nombre")
	    private String menNombre;

	    @Column(name = "men_descripcion")
	    private String menDescripcionMenu;

	    @Column(name = "men_icono")
	    private String menIcono;

	    @Column(name = "men_ruta")
	    private String menRuta;

	    @Column(name = "men_orden")
	    private int menOrden;
	    
	    @OneToMany(mappedBy = "padreMenu")
	    @Where(clause = "aud_estado = 'ACT'")
		private List<GmSegMenu> listPadreMenu;
	    
	    @ManyToOne
		@JoinColumn(name = "men_padre_menu")
		private GmSegMenu padreMenu ;
		
		public Long getMenId() {
			return menId;
		}

		public void setMenId(Long menId) {
			this.menId = menId;
		}

		public String getMenNombre() {
			return menNombre;
		}

		public void setMenNombre(String menNombre) {
			this.menNombre = menNombre;
		}

		public String getMenDescripcionMenu() {
			return menDescripcionMenu;
		}

		public void setMenDescripcionMenu(String menDescripcionMenu) {
			this.menDescripcionMenu = menDescripcionMenu;
		}

		public String getMenIcono() {
			return menIcono;
		}

		public void setMenIcono(String menIcono) {
			this.menIcono = menIcono;
		}

		public String getMenRuta() {
			return menRuta;
		}

		public void setMenRuta(String menRuta) {
			this.menRuta = menRuta;
		}

		public int getMenOrden() {
			return menOrden;
		}

		public void setMenOrden(int menOrden) {
			this.menOrden = menOrden;
		}

		public List<GmSegMenu> getListPadreMenu() {
			return listPadreMenu;
		}

		public void setListPadreMenu(List<GmSegMenu> listPadreMenu) {
			this.listPadreMenu = listPadreMenu;
		}


		public GmSegMenu getPadreMenu() {
			return padreMenu;
		}

		public void setPadreMenu(GmSegMenu padreMenu) {
			this.padreMenu = padreMenu;
		}

	

		
}
