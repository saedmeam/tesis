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

import net.macrosigma.util.ent.EntityBase;

@Entity
@Table(name = "gm_ges_foto")
public class GmGesFoto extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_ges_foto", sequenceName = "seq_ges_foto", initialValue = 1, allocationSize = 1)
	@GeneratedValue(generator = "sec_ges_foto", strategy = GenerationType.SEQUENCE)
	@Column(name = "fot_id")
	private long fotId;

//	@ManyToOne
//	@JoinColumn(name = "fot_cla_cat_id")
//	private GmParClaveCatastro fotClaCatId;

	public long getFotId() {
		return fotId;
	}

	public void setFotId(long fotId) {
		this.fotId = fotId;
	}

//	public GmParClaveCatastro getFotClaCatId() {
//		return fotClaCatId;
//	}
//
//	public void setFotClaCatId(GmParClaveCatastro fotClaCatId) {
//		this.fotClaCatId = fotClaCatId;
//	}

}
