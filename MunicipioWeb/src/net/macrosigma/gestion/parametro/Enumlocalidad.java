package net.macrosigma.gestion.parametro;

public enum Enumlocalidad {

	PROVINCIA("11"), CANTON("03"), PARROQUIA("05");

	private String value;

	Enumlocalidad(String id) {
		value = id;
	}

	public String getValue() {
		return value;
	}

}