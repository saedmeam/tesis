package net.macrosigma.ws.regciv;


public class BaseWebService {

	AccesoWSDao accesoDao = new AccesoWSDao();
	protected DatosHeader getDatosHeader(TipoWSenum srv, EntidadWSEnum ent) {
		GmWebserviceDato datoWs = accesoDao.getAccesobyEntidadServicioProveedor(srv, ent);

		DatosHeader head = new DatosHeader();
		AccesoBSGService proxy = new AccesoBSGService();
		BSG04AccederBSG port = proxy.getBSG04AccederBSGPort();

		ValidarPermisoPeticion peticion = new ValidarPermisoPeticion();
		peticion.setCedula(datoWs.getUsuarioWs().getWsPwd());
		peticion.setUrlsw(datoWs.getWsdUrl());

		ValidarPermisoRespuesta result = port.validarPermiso(peticion);

		if (!result.getMensaje().getCodError().equals("000")) {
			System.out.println(result.getMensaje().getDesError());
			head.setNonce(result.getMensaje().getDesError());
			head.setDigest(result.getMensaje().getDesError());
			head.setFecha(result.getMensaje().getDesError());
			head.setFechaf(result.getMensaje().getDesError());
		} else {

			head.setNonce(result.getNonce());
			head.setDigest(result.getDigest());
			head.setFecha(result.getFecha());
			head.setFechaf(result.getFechaF());
			head.setUsuario(datoWs.getUsuarioWs().getWsPwd());
		}
		System.out.println("Acceso = " + result.getMensaje().getDesError());

		return head;
	}

	protected GmWebserviceDato getAutenticidad(EntidadWSEnum ent) {
		return accesoDao.getAutenticidad(ent);
	}
	
	

}
