package net.macrosigma.gestion.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import net.macrosigma.gestion.dao.GeneralUtilsDao;
import net.macrosigma.gestion.dao.GmGesPreguntaFrecuenteDao;
import net.macrosigma.gestion.ent.GmGesPreguntaFrecuente;
import net.macrosigma.gestion.ent.GmGesSolicitud;
import net.macrosigma.gestion.ent.GmGesSolicitudRequisitoDocumento;
import net.macrosigma.parametro.dao.GmParParametroDao;
import net.macrosigma.parametro.ent.GmParParametros;
import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.seguridad.enu.RutaFileEnum;
import net.macrosigma.util.controller.BaseController;

import org.apache.commons.io.IOUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

public class IngSolicitudesController extends BaseController {

	@Wire
	Textbox txtpreg;
	String tipop;
	@Wire
	Listbox lbxreqsol;

	GmGesPreguntaFrecuente interes = new GmGesPreguntaFrecuente();
	GmGesPreguntaFrecuenteDao intDao = new GmGesPreguntaFrecuenteDao();
	GmSegUsuario usu = (GmSegUsuario) Sessions.getCurrent().getAttribute(
			"usuario");
	List<GmParParametros> listparCarrera = new ArrayList<GmParParametros>();
	GmParParametros parCarreraSel = new GmParParametros();
	GmParParametroDao parDao = new GmParParametroDao();
	List<GmParParametros> listparSol = new ArrayList<GmParParametros>();
	GmParParametros parSolSel = new GmParParametros();
	List<GmGesSolicitudRequisitoDocumento> listparReqSol = new ArrayList<GmGesSolicitudRequisitoDocumento>();
	GmGesSolicitud sol = new GmGesSolicitud();
	Media media;
	GeneralUtilsDao imgdao = new GeneralUtilsDao();

	Long cont = 0L;

	public GmGesSolicitud getSol() {
		return sol;
	}

	public void setSol(GmGesSolicitud sol) {
		this.sol = sol;
	}

	public List<GmGesSolicitudRequisitoDocumento> getListparReqSol() {
		return listparReqSol;
	}

	public void setListparReqSol(
			List<GmGesSolicitudRequisitoDocumento> listparReqSol) {
		this.listparReqSol = listparReqSol;
	}

	public String getTipop() {
		return tipop;
	}

	public void setTipop(String tipop) {
		this.tipop = tipop;
	}

	public GmSegUsuario getUsu() {
		return usu;
	}

	public void setUsu(GmSegUsuario usu) {
		this.usu = usu;
	}

	public List<GmParParametros> getListparCarrera() {
		return listparCarrera;
	}

	public void setListparCarrera(List<GmParParametros> listparCarrera) {
		this.listparCarrera = listparCarrera;
	}

	public GmParParametros getParCarreraSel() {
		return parCarreraSel;
	}

	public void setParCarreraSel(GmParParametros parCarreraSel) {
		this.parCarreraSel = parCarreraSel;
	}

	public List<GmParParametros> getListparSol() {
		return listparSol;
	}

	public void setListparSol(List<GmParParametros> listparSol) {
		this.listparSol = listparSol;
	}

	public GmParParametros getParSolSel() {
		return parSolSel;
	}

	public void setParSolSel(GmParParametros parSolSel) {
		this.parSolSel = parSolSel;
	}

	public GmGesPreguntaFrecuenteDao getIntDao() {
		return intDao;
	}

	public void setIntDao(GmGesPreguntaFrecuenteDao intDao) {
		this.intDao = intDao;
	}

	public GmGesPreguntaFrecuente getInteres() {
		return interes;
	}

	public void setInteres(GmGesPreguntaFrecuente interes) {
		this.interes = interes;
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		tipop = ((String) Sessions.getCurrent().getAttribute("tip_op"));
		if (tipop == "M") {
			interes = ((GmGesPreguntaFrecuente) Sessions.getCurrent()
					.getAttribute("cod_int"));
		}
		cargaCombo();
		BindUtils.postNotifyChange(null, null, IngSolicitudesController.this,
				"interes");
	}

	@SuppressWarnings("static-access")
	public void cargaCombo() {
		listparCarrera = parDao.getParametroByDesPad("CARRERAS");
		listparSol = parDao.getParametroByDesPad("TIPOS DE SOLICITUD");

	}

	@Command
	public void cargalistado() {
		List<GmParParametros> listReq = new ArrayList<GmParParametros>();

		if (parSolSel != null) {
			for (GmParParametros par : parSolSel.getCarIdHij())
				listReq.add(par);
		}
		if (listReq != null) {
			if (listReq.size() > 0) {
				for (GmParParametros par : listReq) {
					GmGesSolicitudRequisitoDocumento parReqSol = new GmGesSolicitudRequisitoDocumento();
					parReqSol.setSolReqTipSol(par);
					listparReqSol.add(parReqSol);
				}

			}

		}
		BindUtils.postNotifyChange(null, null, IngSolicitudesController.this,
				"listparReqSol");
	}

	@Command
	public void createUsuario() {
		// campos para validar los si estan vacio

		if (txtpreg.getValue() == null) {
			txtpreg.setErrorMessage("campo obligatorio");
			return;
		}
		interes.setEstado("ACT");
		if (tipop == "M")
			intDao.actualizar(interes);
		else
			intDao.crear(interes);
		limpiar();
		Messagebox.show("Pregunta Frecuente Ingresada");

	}

	public void limpiar() {
		interes = new GmGesPreguntaFrecuente();
		txtpreg.setValue("");

	}

	@Command
	public void downloadFile(@BindingParam("obj") GmGesSolicitudRequisitoDocumento parSel){
		try {
			File file = new File(parSel.getCarpeta()+ parSel.getNombreImagen());
			URLConnection conn = new URL(url).openConnection();
			conn.connect();
			System.out.println("\nempezando descarga: \n");
			System.out.println(">> URL: " + url);
			System.out.println(">> Nombre: " + name);
			System.out.println(">> tamaño: " + conn.getContentLength() + " bytes");
			
			InputStream in = conn.getInputStream();
			OutputStream out = new FileOutputStream(file);
			Mediante un bucle vamos leyendo del InputStream y vamos escribiendo en el OutputStream. Vamos leyendo de a un byte por vez y los escribe en un archivo. El -1 significa que se llego al final.

			int b = 0;
			while (b != -1) {
			  b = in.read();
			  if (b != -1)
			    out.write(b);
			}
			
			out.close();
			in.close();

		 ...
		} catch (MalformedURLException e) {
		  System.out.println("la url: " + url + " no es valida!");
		} catch (IOException e) {
		  e.printStackTrace();
		}
	}

	@Command
	public void uploadFile(
			@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event,
			@BindingParam("obj") GmGesSolicitudRequisitoDocumento parSel) {
		media = event.getMedia();
		media.getStreamData();
		try {

			parSel.setImagen(IOUtils.toByteArray(media.getStreamData()));
			String pathProyecto = RutaFileEnum.RUTA_PROYECTO_DEPLOYED
					.getDescripcion();
			// String pathImagenTemp = RutaFileEnum.RUTA_IMAGEN_TEMPORAL
			// .getDescripcion();
			imgdao.creaRuta(parSel.getSolReqTipSol().getPar_id(), pathProyecto
					+ File.separatorChar);
			String ruta = imgdao.guardaImagenTemporal(parSel.getImagen(),
					pathProyecto, parSel.getSolReqTipSol().getPar_id(),
					media.getName());
			cont++;
			parSel.setNombreImagen(media.getName());
			// this.fot = fot;
			parSel.setCarpeta(ruta);
			parSel.setImagenPath(ruta + "/" + media.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// if (listFot != null) {
		// for (int i = 0; i < listFot.size(); i++) {
		// listFotClon.add(listFot.get(i));
		// listFot.remove(0);
		// }
		// }
		// listFot.add(fot);
		for (int i = 0; i < listparReqSol.size(); i++) {
			if (parSel.getSolReqTipSol() == listparReqSol.get(i)
					.getSolReqTipSol()) {
				listparReqSol.set(i, parSel);
			}
		}
		// Messagebox.show("Debe Ingresar un código de Ganado");
		BindUtils.postNotifyChange(null, null, IngSolicitudesController.this,
				"listparReqSol");
	}

}
