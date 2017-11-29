package net.macrosigma.ws.regciv;

public enum EntidadWSEnum {

	SRI("Servicio Rentas Internas"),
	REGCIV("REgistro Civil"),
	SNAP("Secretaría Nacional de la Administración Pública"),
	DINARDAP("Dirección Nacional de Registro de Datos Públicos");

	private String id;

	EntidadWSEnum(String value) {
		this.id = value;
	}

	public String getValue() {
		return this.id;
	}

}
