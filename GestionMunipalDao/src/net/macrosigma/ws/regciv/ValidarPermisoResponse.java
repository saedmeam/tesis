
package net.macrosigma.ws.regciv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidarPermisoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidarPermisoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://bsg.gob.ec/AccesoBSGService}validarPermisoRespuesta" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidarPermisoResponse", propOrder = {
    "_return"
})
public class ValidarPermisoResponse {

    @XmlElement(name = "return")
    protected ValidarPermisoRespuesta _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link ValidarPermisoRespuesta }
     *     
     */
    public ValidarPermisoRespuesta getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidarPermisoRespuesta }
     *     
     */
    public void setReturn(ValidarPermisoRespuesta value) {
        this._return = value;
    }

}
