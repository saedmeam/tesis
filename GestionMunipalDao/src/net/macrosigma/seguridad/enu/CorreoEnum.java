package net.macrosigma.seguridad.enu;

public enum CorreoEnum {

	mailsmtpHOST("smtp.gmail.com"), // 0
	mailsmtpsocketFactoryport("465"), // 1
	mailsmtpsocketFactoryclass("javax.net.ssl.SSLSocketFactory"), // 2
	mailsmtpauth("true"), // 3
	mailsmtpport("465"), // 4
	mailsmtpuser("saedmeam@gmail.com"), // 5
	mailsmtppassword("30061992Sa"); // 6

	private CorreoEnum(final String descripcion) {
		this.descripcion = descripcion;

	}

	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

}