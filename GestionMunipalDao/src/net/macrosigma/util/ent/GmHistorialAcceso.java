package net.macrosigma.util.ent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="gm_historia_acceso")
public class GmHistorialAcceso extends EntityBase {
	 @Id
	 @SequenceGenerator(name = "sec_historial_acceso", sequenceName = "seq_historial_acceso", allocationSize = 1)
	 @GeneratedValue(generator = "sec_historial_acceso", strategy = GenerationType.SEQUENCE)
	 @Column(name = "his_acc_id")
	 private Long hisAccId;
	 
	 @Column(name = "his_acc_tipo_acceso")
	 private String hisAccTipoAcceso;

	 @Column(name = "his_acc_detalle")
	 private String hisAccDetalle;
	 
	 @Column(name = "his_acc_ip")
	 private String hisAccIp;

	 @Column(name = "his_acc_nombre_maquina")
	 private String hisAccNombreMaquina;

	public String getHisAccIp() {
		return hisAccIp;
	}

	public void setHisAccIp(String hisAccIp) {
		this.hisAccIp = hisAccIp;
	}

	public String getHisAccNombreMaquina() {
		return hisAccNombreMaquina;
	}

	public void setHisAccNombreMaquina(String hisAccNombreMaquina) {
		this.hisAccNombreMaquina = hisAccNombreMaquina;
	}

	public Long getHisAccId() {
		return hisAccId;
	}

	public void setHisAccId(Long hisAccId) {
		this.hisAccId = hisAccId;
	}

	public String getHisAccTipoAcceso() {
		return hisAccTipoAcceso;
	}

	public void setHisAccTipoAcceso(String hisAccTipoAcceso) {
		this.hisAccTipoAcceso = hisAccTipoAcceso;
	}

	public String getHisAccDetalle() {
		return hisAccDetalle;
	}

	public void setHisAccDetalle(String hisAccDetalle) {
		this.hisAccDetalle = hisAccDetalle;
	}	 

}
