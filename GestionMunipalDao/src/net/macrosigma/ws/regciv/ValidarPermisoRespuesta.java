
package net.macrosigma.ws.regciv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for validarPermisoRespuesta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="validarPermisoRespuesta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Digest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fecha" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FechaF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Mensaje" type="{http://bsg.gob.ec/AccesoBSGService}mensajeError" minOccurs="0"/>
 *         &lt;element name="Nonce" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TienePermiso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validarPermisoRespuesta", propOrder = {
    "digest",
    "fecha",
    "fechaF",
    "mensaje",
    "nonce",
    "tienePermiso"
})
public class ValidarPermisoRespuesta {

    @XmlElement(name = "Digest")
    protected String digest;
    @XmlElement(name = "Fecha")
    protected String fecha;
    @XmlElement(name = "FechaF")
    protected String fechaF;
    @XmlElement(name = "Mensaje")
    protected MensajeError mensaje;
    @XmlElement(name = "Nonce")
    protected String nonce;
    @XmlElement(name = "TienePermiso")
    protected String tienePermiso;

    /**
     * Gets the value of the digest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDigest() {
        return digest;
    }

    /**
     * Sets the value of the digest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDigest(String value) {
        this.digest = value;
    }

    /**
     * Gets the value of the fecha property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecha(String value) {
        this.fecha = value;
    }

    /**
     * Gets the value of the fechaF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaF() {
        return fechaF;
    }

    /**
     * Sets the value of the fechaF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaF(String value) {
        this.fechaF = value;
    }

    /**
     * Gets the value of the mensaje property.
     * 
     * @return
     *     possible object is
     *     {@link MensajeError }
     *     
     */
    public MensajeError getMensaje() {
        return mensaje;
    }

    /**
     * Sets the value of the mensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link MensajeError }
     *     
     */
    public void setMensaje(MensajeError value) {
        this.mensaje = value;
    }

    /**
     * Gets the value of the nonce property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonce() {
        return nonce;
    }

    /**
     * Sets the value of the nonce property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonce(String value) {
        this.nonce = value;
    }

    /**
     * Gets the value of the tienePermiso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTienePermiso() {
        return tienePermiso;
    }

    /**
     * Sets the value of the tienePermiso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTienePermiso(String value) {
        this.tienePermiso = value;
    }

}
