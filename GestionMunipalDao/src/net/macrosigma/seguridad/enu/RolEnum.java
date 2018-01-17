package net.macrosigma.seguridad.enu;

public enum RolEnum {

	ESTUDIANTE("ESTUDIANTE", 32L), 
	ADMINISTRADOR("ADMINISTRADOR", 19L), 
	SECRETARIA("SECRETARIA", 19L);

	private String estado;
	private Long indice;

	RolEnum(String estado, Long indice) {
		this.estado = estado;
		this.indice = indice;
	}

	/**
	 * Query The status description from the named Enumeration
	 * 
	 * @return Enum's Description
	 */

	public String getDescripcion() {
		return this.estado;
	}

	public Long getIndice() {
		return this.indice;
	}

}
