package net.macrosigma.gestion.dao;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.macrosigma.gestion.ent.GmGesSolicitud;
import net.macrosigma.gestion.ent.GmGesSolicitudRequisitoDocumento;
import net.macrosigma.util.dao.GenericDao;

public class GeneralUtilsDao extends
		GenericDao<GmGesSolicitudRequisitoDocumento, Long> {

	public void crear(GmGesSolicitudRequisitoDocumento pers) {

		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.crear(pers);
		}
		trx.commit();
	}

	public void actualizar(GmGesSolicitudRequisitoDocumento pers) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		if (trx.isActive()) {
			super.actualizar(pers);
		}
		trx.commit();

	}

	public void eliminar(GmGesSolicitudRequisitoDocumento pers) {
		super.eliminar(pers);
	}

	public String creaRuta(Long idObjeto, String pathFolder) {

		File folder = new File(pathFolder + File.separator + idObjeto
				+ File.separator);
		System.out.println("crea carpeta folder ==> " + folder);
		folder.mkdirs();
		folder.setWritable(true);
		System.out.println("retorna carpeta folder ==> "
				+ folder.getPath().toString());
		return folder.getPath().toString() + File.separator;
	}

	public String creaRutaExcel(String pathFolder) {

		File folder = new File(pathFolder + File.separator);
		System.out.println("crea carpeta folder ==> " + folder);
		folder.mkdirs();
		folder.setWritable(true);
		System.out.println("retorna carpeta folder ==> "
				+ folder.getPath().toString());
		return folder.getPath().toString() + File.separator;
	}

	public void copyFile(String fileName, InputStream in, String destino) {
		try {
			OutputStream out = new FileOutputStream(
					new File(destino + fileName));
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Permite crear un archivo temporal para almacenar la Imagen
	 * 
	 * @param bytes
	 * @param nombreArchivo
	 * @return
	 */
	public String guardaBlobEnFifheroTemporal(InputStream stream,
			String destino, Long codObj, String subfolder,
			String nombreArchivo, boolean condicion) {

		String ubicacionImagen = null;
		String path = null;
		try {
			stream.reset();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (condicion) {
			path = destino + subfolder + nombreArchivo;
		} else {
			path = destino + File.separatorChar + subfolder
					+ File.separatorChar + nombreArchivo;
		}
		File f = new File(path);

		try (FileOutputStream out = new FileOutputStream(f.getAbsolutePath())) {
			int c = 0;
			while ((c = stream.read()) >= 0) {
				out.write(c);
			}
			out.flush();
			if (condicion) {
				ubicacionImagen = destino + nombreArchivo;
			} else {
				ubicacionImagen = "../../resources/images/tmp/" + subfolder
						+ "/" + nombreArchivo;
			}

		} catch (Exception e) {
			System.err.println("No se pudo Cargar la Imagen");
		}

		return ubicacionImagen;

	}

	public String guardaImagenTemporal(byte[] bytes, String pathProyecto,
			Long codObj, String nombreArchivo) {

		if (bytes == null)
			return "";

		String ubicacionImagen = null;
		String path = null;

		path = pathProyecto + File.separatorChar + codObj + File.separatorChar
				+ nombreArchivo;

		// path = pathProyecto + File.separatorChar
		// + RutaFileEnum.RUTA_IMAGEN_TEMPORAL.getDescripcion()
		// + File.separatorChar + nombreArchivo;
		// }

		File f = null;
		InputStream in = null;

		try {

			f = new File(path);
			in = new ByteArrayInputStream(bytes);
			FileOutputStream out = new FileOutputStream(f.getAbsoluteFile());
			int c = 0;
			while ((c = in.read()) >= 0) {
				out.write(c);
			}
			out.flush();
			out.close();
			// if (condicion) {
			ubicacionImagen = pathProyecto+ File.separator + codObj;
			// } else {
			// ubicacionImagen = "/img/tmp/" + nombreArchivo;
			// }

		} catch (Exception e) {
			System.err.println("No se pudo Cargar la Imagen");
			e.printStackTrace();
		}

		return ubicacionImagen;

	}

	public InputStream getFileFromPath(String path) {
		File file = new File(path);
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<GmGesSolicitudRequisitoDocumento> getCargaFot(GmGesSolicitud gan) {
		StringBuilder sql = new StringBuilder();
		sql.append("select o from GmGesSolicitudRequisitoDocumento o where o.solReqDoc = :gan and o.estado = 'ACT'");
		Query query = em.createQuery(sql.toString());
		query.setParameter("gan", gan);
		List<GmGesSolicitudRequisitoDocumento> result = query.getResultList();
		return result == null || result.isEmpty() ? null : result;
	}

}
