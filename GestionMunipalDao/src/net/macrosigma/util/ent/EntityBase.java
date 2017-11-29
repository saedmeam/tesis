package net.macrosigma.util.ent;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class EntityBase {
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "aud_fecha_ingreso")
    private Date fechaIngreso;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "aud_fecha_registra")
    private Date fechaModificacion;

    @Column(name = "aud_usuario")
    private String usuario;

    @Column(name = "aud_estado")
    private String estado;
    

    public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	{
	if (estado == null)
	    estado = "ACT";

	if (fechaModificacion == null)
	    fechaModificacion = new Date();

	if (fechaIngreso == null)
	    fechaIngreso = new Date();
	
    }

    public Date getFechaIngreso() {
	return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
	this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaModificacion() {
	return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
	this.fechaModificacion = fechaModificacion;
    }


}