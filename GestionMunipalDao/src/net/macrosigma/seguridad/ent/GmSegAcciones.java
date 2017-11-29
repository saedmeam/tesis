package net.macrosigma.seguridad.ent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import net.macrosigma.seguridad.enu.AccionesEnum;
import net.macrosigma.util.ent.EntityBase;


@Entity
@Table(name = "GM_SEG_ACCIONES")
public class GmSegAcciones extends EntityBase {

	@Id
	@SequenceGenerator(name = "sec_acciones", sequenceName = "seq_acciones", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "sec_acciones", strategy = GenerationType.SEQUENCE)
	@Column(name = "acc_id")
	private Long accId;

	@Column(length = 4)
	@Enumerated(EnumType.ORDINAL)
	private AccionesEnum accionEnum;

	public Long getAccId() {
		return accId;
	}

	public void setAccId(Long accId) {
		this.accId = accId;
	}

	public AccionesEnum getAccionEnum() {
		return accionEnum;
	}

	public void setAccionEnum(AccionesEnum accionEnum) {
		this.accionEnum = accionEnum;
	}

	public boolean equals(Object obj) {
		GmSegAcciones other = (GmSegAcciones) obj;

		if (other == null) {
			return false;
		}

		if (this.accId == null) {
			return this == other;
		}

		return this.accId.equals(other.accId);
	}

}