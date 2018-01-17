package net.macrosigma.seguridad.enu;

import java.io.File;

public enum RutaFileEnum {

	RUTA_INSTITUCION(File.separator + "reuniones" + File.separator
			+ "institucion" + File.separator), RUTA_EVENTO(getPath(
			"GerenciaSocial", "Evento")),

	RUTA_DOC_PROYECTO(File.separator + "reuniones" + File.separator
			+ "documento_proyecto" + File.separator), RUTA_ACTOR(getPath(
			"MantenimientoPersona", "Actor")), RUTA_PROYECTO_DEPLOYED(
			getPathProyecto()),RUTA_EXCEL(
					getPathExcel()), RUTA_IMAGEN_TEMPORAL("img" + File.separator
			+ "tmp"), RUTA_IMAGEN_TEMP(""), SubCarpeta("Ganado"), Server("C:"
			+ File.separator + "documentos");

	private RutaFileEnum(String descripcion) {
		this.descripcion = descripcion;
	}

	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public static String getPath(String... path) {
		StringBuilder fileDir = new StringBuilder(
				File.listRoots()[0].getAbsolutePath());
		for (String fl : path) {
			fileDir.append(File.separator + fl);
		}
		return fileDir.toString() + File.separator;
	}

	public static String getPathProyecto(String... path) {
		// C:\workspace\ganado\jboss-as-7.1.1.Final\standalone\deployments\GanaderoWeb.war\img\temp
		String pathLocal = null;

		// Local
//		pathLocal = "C:" + File.separator + "documentos";

		// Servidor
		 pathLocal = File.separator +"opt" + File.separator + "documentos";

		return pathLocal;
	}
	
	public static String getPathExcel(String... path) {
		// C:\workspace\ganado\jboss-as-7.1.1.Final\standalone\deployments\GanaderoWeb.war\img\temp
		String pathLocal = null;

		// Local
//		pathLocal = "C:" + File.separator + "archivos";

		// Servidor
		pathLocal = File.separator +"opt" + File.separator + "archivos";

		return pathLocal;
	}
	
	
}
