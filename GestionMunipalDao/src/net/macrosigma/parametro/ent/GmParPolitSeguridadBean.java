package net.macrosigma.parametro.ent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.macrosigma.util.ent.EntityBase;

@Entity
@Table(name = "gm_par_pol_seg")
public class GmParPolitSeguridadBean extends EntityBase {
	@Id
	@SequenceGenerator(name = "sec_par_polit_seguridad", sequenceName = "sec_par_polit_seguridad", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_par_polit_seguridad", strategy = GenerationType.SEQUENCE)
	@Column(name = "pol_seg_id")
	private Long polSegId;

	@Column(name = "pol_seg_lon_max_usu")
	private Long polSegLonMaxUsu;

	@Column(name = "pol_seg_num_int_falli_cla")
	private Long polSegNumIntFalliCla;

	@Column(name = "pol_seg_long_min_con")
	private Long polSegLongMinCon;

	@Column(name = "pol_seg_long_max_con")
	private Long polSegLongMaxCon;

	@Column(name = "pol_seg_dias_rec_cam_con")
	private Long polSegDiasRecCamCon;

	@Column(name = "pol_seg_vig_con")
	private Long polSegVigCon;

	@Column(name = "pol_seg_per_sim")
	private String polSegPerSim;

	@Column(name = "pol_seg_per_num")
	private String polSegPerNum;

	@Column(name = "pol_seg_per_min")
	private String polSegPerMin;

	@Column(name = "pol_seg_per_may")
	private String polSegPerMay;

	public Long getPolSegId() {
		return polSegId;
	}

	public void setPolSegId(Long polSegId) {
		this.polSegId = polSegId;
	}

	public Long getPolSegLonMaxUsu() {
		return polSegLonMaxUsu;
	}

	public void setPolSegLonMaxUsu(Long polSegLonMaxUsu) {
		this.polSegLonMaxUsu = polSegLonMaxUsu;
	}

	public Long getPolSegNumIntFalliCla() {
		return polSegNumIntFalliCla;
	}

	public void setPolSegNumIntFalliCla(Long polSegNumIntFalliCla) {
		this.polSegNumIntFalliCla = polSegNumIntFalliCla;
	}

	public Long getPolSegLongMinCon() {
		return polSegLongMinCon;
	}

	public void setPolSegLongMinCon(Long polSegLongMinCon) {
		this.polSegLongMinCon = polSegLongMinCon;
	}

	public Long getPolSegLongMaxCon() {
		return polSegLongMaxCon;
	}

	public void setPolSegLongMaxCon(Long polSegLongMaxCon) {
		this.polSegLongMaxCon = polSegLongMaxCon;
	}

	public Long getPolSegDiasRecCamCon() {
		return polSegDiasRecCamCon;
	}

	public void setPolSegDiasRecCamCon(Long polSegDiasRecCamCon) {
		this.polSegDiasRecCamCon = polSegDiasRecCamCon;
	}

	public Long getPolSegVigCon() {
		return polSegVigCon;
	}

	public void setPolSegVigCon(Long polSegVigCon) {
		this.polSegVigCon = polSegVigCon;
	}

	public String getPolSegPerSim() {
		return polSegPerSim;
	}

	public void setPolSegPerSim(String polSegPerSim) {
		this.polSegPerSim = polSegPerSim;
	}

	public String getPolSegPerNum() {
		return polSegPerNum;
	}

	public void setPolSegPerNum(String polSegPerNum) {
		this.polSegPerNum = polSegPerNum;
	}

	public String getPolSegPerMin() {
		return polSegPerMin;
	}

	public void setPolSegPerMin(String polSegPerMin) {
		this.polSegPerMin = polSegPerMin;
	}

	public String getPolSegPerMay() {
		return polSegPerMay;
	}

	public void setPolSegPerMay(String polSegPerMay) {
		this.polSegPerMay = polSegPerMay;
	}
}
