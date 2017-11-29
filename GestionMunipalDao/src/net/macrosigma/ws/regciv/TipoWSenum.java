package net.macrosigma.ws.regciv;

public enum TipoWSenum {

	SNAP("Secretaria Nacional de la Administracion Publica"),
	DINARDAP("Direccion Nacional de Registro de Datos Publicos");

	private String id;

	TipoWSenum(String value) {
		this.id = value;
	}

	public String getValue() {
		return this.id;
	}

}
