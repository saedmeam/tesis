package net.macrosigma.ws.regciv;

import javax.ejb.Local;



@Local
public interface IWebServiceDataQuery{

    boolean validarCedula(String id);

    boolean validarRuc(String Ruc);

    Cedula getInfoCedula(String id);


}
