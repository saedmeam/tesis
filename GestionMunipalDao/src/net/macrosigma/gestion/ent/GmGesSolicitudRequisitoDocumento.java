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
@Table(name = "gm_ges_solicitud_requisito_documento")
public class GmGesSolicitudRequisitoDocumento extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_gm_ges_solicitud_requisito_documento", sequenceName = "seq_gm_ges_solicitud_requisito_documento", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_gm_ges_solicitud_requisito_documento", strategy = GenerationType.SEQUENCE)
	@Column(name = "ins_id")
	private Long insId;

	@Column(name = "imagen")
	private byte[] imagen;

	@Column(name = "imagen_path")
	private String imagenPath;
	
	@Column(name = "carpeta")
	private String carpeta;

	@Column(name = "nombre_imagen")
	private String nombreImagen;

	@ManyToOne
	@JoinColumn(name = "sol_id_req_doc")
	private GmGesSolicitud solReqDoc;

	@ManyToOne
	@JoinColumn(name = "sol_id_req_tip_sol")
	private GmParParametros solReqTipSol;

	public Long getInsId() {
		return insId;
	}

	public void setInsId(Long insId) {
		this.insId = insId;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getImagenPath() {
		return imagenPath;
	}

	public void setImagenPath(String imagenPath) {
		this.imagenPath = imagenPath;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public GmGesSolicitud getSolReqDoc() {
		return solReqDoc;
	}

	public void setSolReqDoc(GmGesSolicitud solReqDoc) {
		this.solReqDoc = solReqDoc;
	}

	public GmParParametros getSolReqTipSol() {
		return solReqTipSol;
	}

	public void setSolReqTipSol(GmParParametros solReqTipSol) {
		this.solReqTipSol = solReqTipSol;
	}

	public String getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}

}
