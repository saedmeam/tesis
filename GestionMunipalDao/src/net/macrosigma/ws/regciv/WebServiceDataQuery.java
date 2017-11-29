package net.macrosigma.ws.regciv;

import javax.ejb.Stateless;

@Stateless
public class WebServiceDataQuery extends BaseWebService implements IWebServiceDataQuery{
	
	
	 public Cedula getInfoCedula(String id) {
			WSRegistroCivilConsultaCedula_Service service = new WSRegistroCivilConsultaCedula_Service();
			DatosHeader header = getDatosHeader(TipoWSenum.SNAP, EntidadWSEnum.REGCIV);

			HeaderHandlerResolver handler = new HeaderHandlerResolver(header);
			service.setHandlerResolver(handler);

			WSRegistroCivilConsultaCedula port = service.getWSRegistroCivilConsultaCedulaPort();

			GmWebserviceDato datoREGCIV = this.getAutenticidad(EntidadWSEnum.REGCIV);

			Cedula cedula = port.busquedaPorCedula(id, datoREGCIV.getWsUsuario(), datoREGCIV.getWsPwd());
			return cedula;
		    }

	 
	@Override
	public boolean validarCedula(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validarRuc(String Ruc) {
		// TODO Auto-generated method stub
		return false;
	}
}
