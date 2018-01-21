package net.macrosigma.gestion.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.macrosigma.gestion.dao.GeneralUtilsDao;
import net.macrosigma.gestion.dao.GmGesDepartamentoTipSolicitudDao;
import net.macrosigma.gestion.dao.GmGesSolicitudDao;
import net.macrosigma.gestion.dao.GmGesSolicitudRequisitoDocumentoDao;
import net.macrosigma.gestion.ent.GmGesDepartamentoTipSolicitud;
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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

public class IngSolicitudesController extends BaseController {

	String tipop;

	@Wire
	Combobox cmbdesc, cmbtiposolicitud;
	@Wire
	Window winingsol;
	@Wire
	Listbox lbxreqsol;

	GmGesSolicitudDao intDao = new GmGesSolicitudDao();
	GmParParametroDao parDao = new GmParParametroDao();
	GeneralUtilsDao imgdao = new GeneralUtilsDao();
	GmGesDepartamentoTipSolicitudDao deptipsoldao = new GmGesDepartamentoTipSolicitudDao();
	GmGesSolicitudRequisitoDocumentoDao reqSolDao = new GmGesSolicitudRequisitoDocumentoDao();
	GmSegUsuario usu = (GmSegUsuario) Sessions.getCurrent().getAttribute(
			"usuario");
	List<GmParParametros> listparCarrera = new ArrayList<GmParParametros>();
	GmParParametros parCarreraSel = new GmParParametros();
	List<GmParParametros> listparSol = new ArrayList<GmParParametros>();
	GmParParametros parSolSel = new GmParParametros();
	List<GmGesSolicitudRequisitoDocumento> listparReqSol = new ArrayList<GmGesSolicitudRequisitoDocumento>();
	GmGesSolicitud sol = new GmGesSolicitud();
	Media media;

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

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		tipop = ((String) Sessions.getCurrent().getAttribute("tip_op"));
		cargaCombo();
		if (tipop == "M") {
			sol = ((GmGesSolicitud) Sessions.getCurrent().getAttribute(
					"cod_int"));
			parCarreraSel = sol.getSolCarrera();
			parSolSel = sol.getSolTipoSolicitud();
			listparReqSol = sol.getSolReqDoc();

		}

		BindUtils.postNotifyChange(null, null, IngSolicitudesController.this,
				"interes");
		parCarreraSel = usu.getUsuCarrId();
		BindUtils.postNotifyChange(null, null, IngSolicitudesController.this,
				"parCarreraSel");
		cmbdesc.setDisabled(true);
	}

	@SuppressWarnings("static-access")
	public void cargaCombo() {
		listparCarrera = parDao.getParametroByDesPad("CARRERAS");
		listparSol = parDao.getParametroByDesPad("TIPOS DE SOLICITUD");

	}

	@Command
	public void cargalistado() {
		List<GmParParametros> listReq = new ArrayList<GmParParametros>();
		listparReqSol = new ArrayList<>();
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

	public String concat(String... a) {
		String returnString = "";
		for (int i = 0; i < a.length; i++) {
			returnString = returnString + a[i];
		}
		return returnString;
	}

	@SuppressWarnings("static-access")
	@Command
	public void createUsuario() {
		intDao.newManager();
		// campos para validar los si estan vacio

		if (cmbdesc.getValue() == null) {
			cmbdesc.setErrorMessage("campo obligatorio");
			return;
		}

		if (cmbtiposolicitud.getValue() == null) {
			cmbtiposolicitud.setErrorMessage("campo obligatorio");
			return;
		}

		String reqfal = null;
		boolean b = false;
		for (int i = 0; i < listparReqSol.size(); i++) {
			if (listparReqSol.get(i).getNombreImagen() == null) {
				reqfal = "Debe Ingresar todos los requisitos, falta. "
						+ listparReqSol.get(i).getSolReqTipSol().getParDes();
				b = true;
				break;

			}
		}
		if (b) {
			Messagebox.show(reqfal, "Informe", Messagebox.OK, Messagebox.ERROR,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});
			return;
		}
		sol.setSolCarrera(parCarreraSel);
		sol.setSolTipoSolicitud(parSolSel);
		sol.setSolUsu(usu);
		sol.setUsuario(usu.getUsuario());
		sol.setEstado("ACT");
		// sol.setSolEstado("ING");
		List<GmGesDepartamentoTipSolicitud> ldepsol = new ArrayList<>();
		deptipsoldao.newManager();
		ldepsol = deptipsoldao.getDepTipSolXCarrTipSolAct(parSolSel,
				parCarreraSel);
		if (ldepsol.size() > 0)
			sol.setSolUsuAsig(ldepsol.get(0).getDepDepUsuId());
		else {
			Messagebox
					.show("Esta solicitud no tiene responsable, favor comuniquese con el administrador",
							"Informe", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		if (tipop == "M") {
			intDao.actualizar(sol);
			for (int i = 0; i < listparReqSol.size(); i++) {
				listparReqSol.get(i).setSolReqDoc(sol);
				reqSolDao.newManager();
				if (listparReqSol.get(i).getInsId() > 0)
					reqSolDao.actualizar(listparReqSol.get(i));
				else
					reqSolDao.crear(listparReqSol.get(i));
			}
			limpiar();
			Messagebox.show("Solicitud Procesada", "Informe", Messagebox.OK,
					Messagebox.INFORMATION, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							Events.postEvent(new Event(Events.ON_CLOSE,
									winingsol));
						}
					});
		} else {
			List<GmGesSolicitud> lsol = new ArrayList<>();
			lsol = intDao.getSolbyTipSolValEst(usu, parSolSel);
			if (lsol.size() > 0) {
				Messagebox
						.show("Usted ya tiene en tramite una solicitud por el mismo tipo",
								"Informe", Messagebox.OK, Messagebox.ERROR);
				return;
			} else {

				intDao.crear(sol);
				for (int i = 0; i < listparReqSol.size(); i++) {
					listparReqSol.get(i).setSolReqDoc(sol);
					reqSolDao.newManager();
					reqSolDao.crear(listparReqSol.get(i));
				}
				limpiar();
				Messagebox.show("Solicitud Procesada", "Informe",
						Messagebox.OK, Messagebox.INFORMATION,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event e) throws Exception {
								Events.postEvent(new Event(Events.ON_CLOSE,
										winingsol));
							}
						});
			}

		}

		BindUtils.postNotifyChange(null, null, IngSolicitudesController.this,
				"sol");

		BindUtils.postNotifyChange(null, null, IngSolicitudesController.this,
				"listparReqSol");

	}

	public void limpiar() {
		listparReqSol = new ArrayList<>();
		sol = new GmGesSolicitud();
		BindUtils.postNotifyChange(null, null, IngSolicitudesController.this,
				"sol");

		BindUtils.postNotifyChange(null, null, IngSolicitudesController.this,
				"listparReqSol");
	}

	@Command
	public void downloadFile(
			@BindingParam("obj") GmGesSolicitudRequisitoDocumento parSel) {
		File archivo = new File(parSel.getImagenPath());
		try {
			Filedownload.save(archivo, null);
		} catch (Exception e) {
			Messagebox.show(
					"Error descargando archivo" + parSel.getImagenPath(),
					"Informe", Messagebox.OK, Messagebox.ERROR,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});

			e.printStackTrace();
		}
	}

	@Command
	public void uploadFile(
			@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event,
			@BindingParam("obj") GmGesSolicitudRequisitoDocumento parSel) {
		if (!event.getMedia().getFormat().equals("pdf")
				&& !event.getMedia().getFormat().equals("jpeg")
				&& !event.getMedia().getFormat().equals("jpg")
				&& !event.getMedia().getFormat().equals("png")) {
			Messagebox.show("Debe ingresar solo los formatos permitidos",
					"Información", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		byte[] fileData = event.getMedia().getByteData();

		if (fileData.length > 6291456) {
			Messagebox.show("El tamaño del archivo es mayor al limite",
					"Información", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (parCarreraSel.getPar_id() != null) {

			media = event.getMedia();
			media.getStreamData();
			try {

				parSel.setImagen(IOUtils.toByteArray(media.getStreamData()));
				String pathProyecto = RutaFileEnum.RUTA_PROYECTO_DEPLOYED
						.getDescripcion();
				// String pathImagenTemp = RutaFileEnum.RUTA_IMAGEN_TEMPORAL
				// .getDescripcion();

				String nombre = usu.getUsuario() + parCarreraSel.getPar_id()
						+ parSolSel.getPar_id()
						+ parSel.getSolReqTipSol().getPar_id() + "."
						+ media.getFormat();
				String ruta = imgdao.creaRuta(parSel.getSolReqTipSol()
						.getPar_id(), pathProyecto + File.separatorChar);
				ruta = imgdao.guardaImagenTemporal(parSel.getImagen(),
						pathProyecto, parSel.getSolReqTipSol().getPar_id(),
						nombre);
				cont++;

				parSel.setNombreImagen(nombre);
				// this.fot = fot;
				parSel.setCarpeta(ruta);
				parSel.setImagenPath(ruta + "/" + nombre);

			} catch (IOException e) {
				e.printStackTrace();
			}

			for (int i = 0; i < listparReqSol.size(); i++) {
				if (parSel.getSolReqTipSol() == listparReqSol.get(i)
						.getSolReqTipSol()) {
					listparReqSol.set(i, parSel);
				}
			}
		} else {
			cmbdesc.setErrorMessage("Debe Seleccionar la Carrera a la que Pertenece el estudiante antes de cargar un documento");
			return;
		}
		BindUtils.postNotifyChange(null, null, IngSolicitudesController.this,
				"listparReqSol");
	}
}
