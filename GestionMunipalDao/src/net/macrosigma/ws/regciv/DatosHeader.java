package net.macrosigma.ws.regciv;

/**
 * @rbailon
 */
public class DatosHeader {

	private String fecha;
	private String fechaf;
	private String nonce;
	private String digest;
	private String usuario;

	public DatosHeader(String fecha, String nonce, String digest) {
		this.fecha = fecha;
		this.nonce = nonce;
		this.digest = digest;
	}

	public DatosHeader() {
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFechaf() {
		return fechaf;
	}

	public void setFechaf(String fechaf) {
		this.fechaf = fechaf;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
