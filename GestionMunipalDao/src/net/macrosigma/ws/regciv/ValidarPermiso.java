
package net.macrosigma.ws.regciv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidarPermiso complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidarPermiso">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ValidarPermisoPeticion" type="{http://bsg.gob.ec/AccesoBSGService}validarPermisoPeticion" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidarPermiso", propOrder = {
    "validarPermisoPeticion"
})
public class ValidarPermiso {

    @XmlElement(name = "ValidarPermisoPeticion")
    protected ValidarPermisoPeticion validarPermisoPeticion;

    /**
     * Gets the value of the validarPermisoPeticion property.
     * 
     * @return
     *     possible object is
     *     {@link ValidarPermisoPeticion }
     *     
     */
    public ValidarPermisoPeticion getValidarPermisoPeticion() {
        return validarPermisoPeticion;
    }

    /**
     * Sets the value of the validarPermisoPeticion property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidarPermisoPeticion }
     *     
     */
    public void setValidarPermisoPeticion(ValidarPermisoPeticion value) {
        this.validarPermisoPeticion = value;
    }

}
