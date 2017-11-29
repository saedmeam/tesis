package net.macrosigma.seguridad.enu;

public enum EstadoEnum {

    ACT("ACTIVO", 0),
    INA("INACTIVO", 1);

    private String estado;
    private int indice;

    EstadoEnum(String estadoDesc, int indx) {
	this.estado = estadoDesc;
	this.indice = indx;
    }

    /**
     * Query The status description from the named Enumeration
     * 
     * @return Enum's Description
     */

    public String getDescripcion() {
	return this.estado;
    }

    public int getIndice() {
	return this.indice;
    }

 

}
