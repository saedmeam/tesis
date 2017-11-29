package net.macrosigma.ws.regciv;


public class PermissionClient {

	public DatosHeader GeneraToken(String id, String URI) {
		try {

			DatosHeader Headers = new DatosHeader();

			AccesoBSGService service = new AccesoBSGService();
			BSG04AccederBSG port = service.getBSG04AccederBSGPort();
			ValidarPermisoPeticion validarPermisoPeticion = new ValidarPermisoPeticion();
			validarPermisoPeticion.setCedula(id);
			validarPermisoPeticion.setUrlsw(URI);

			ValidarPermisoRespuesta result = port.validarPermiso(validarPermisoPeticion);

			if (!result.getMensaje().getCodError().equals("000")) {
				System.out.println(result.getMensaje().getDesError());
				Headers.setNonce(result.getMensaje().getDesError());
				Headers.setDigest(result.getMensaje().getDesError());
				Headers.setFecha(result.getMensaje().getDesError());
				Headers.setFechaf(result.getMensaje().getDesError());
			} else {
				Headers.setNonce(result.getNonce());
				Headers.setDigest(result.getDigest());
				Headers.setFecha(result.getFecha());
				Headers.setFechaf(result.getFechaF());
				Headers.setUsuario("1204441628");
			}
			System.out.println("Acceso = " + result.getMensaje().getDesError());
			return Headers;
		} catch (Exception ex) {
			System.out.println("  ERROR " + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}

}
