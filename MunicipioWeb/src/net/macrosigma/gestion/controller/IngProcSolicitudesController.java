package net.macrosigma.gestion.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.macrosigma.gestion.dao.GmGesProcesoSolicitudDao;
import net.macrosigma.gestion.dao.GmGesSolicitudDao;
import net.macrosigma.gestion.dao.GmGesSolicitudRequisitoDocumentoDao;
import net.macrosigma.gestion.ent.GmGesProcesoSolicitud;
import net.macrosigma.gestion.ent.GmGesSolicitud;
import net.macrosigma.gestion.ent.GmGesSolicitudRequisitoDocumento;
import net.macrosigma.parametro.dao.GmParParametroDao;
import net.macrosigma.parametro.ent.GmParParametros;
import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.controller.BaseController;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

public class IngProcSolicitudesController extends BaseController {

	String tipop;
	Window window;
	@Wire
	Combobox cmbdesc, cmbtiposolicitud;
	@Wire
	Button btnagrega;
	@Wire
	Listbox lbxreqsol;

	GmGesSolicitudDao intDao = new GmGesSolicitudDao();
	GmParParametroDao parDao = new GmParParametroDao();
	GmGesProcesoSolicitudDao procSolDao = new GmGesProcesoSolicitudDao();
	GmGesSolicitudRequisitoDocumentoDao reqSolDao = new GmGesSolicitudRequisitoDocumentoDao();
	GmSegUsuario usu = (GmSegUsuario) Sessions.getCurrent().getAttribute(
			"usuario");
	List<GmParParametros> listparCarrera = new ArrayList<GmParParametros>();
	GmParParametros parCarreraSel = new GmParParametros();
	List<GmParParametros> listparSol = new ArrayList<GmParParametros>();
	GmParParametros parSolSel = new GmParParametros();
	List<GmGesSolicitudRequisitoDocumento> listparReqSol = new ArrayList<GmGesSolicitudRequisitoDocumento>();
	GmGesSolicitud sol = new GmGesSolicitud();
	List<GmGesProcesoSolicitud> listProcSol = new ArrayList<GmGesProcesoSolicitud>();

	Long cont = 0L;

	public List<GmGesProcesoSolicitud> getListProcSol() {
		return listProcSol;
	}

	public void setListProcSol(List<GmGesProcesoSolicitud> listProcSol) {
		this.listProcSol = listProcSol;
	}

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
			if (sol.getSolUsuAsig().getUsuario().equals(usu.getUsuario()))
				btnagrega.setVisible(true);
			else
				btnagrega.setVisible(false);

			parCarreraSel = sol.getSolCarrera();
			parSolSel = sol.getSolTipoSolicitud();
			listparReqSol = sol.getSolReqDoc();
			for (int i = (sol.getProcSolSolid().size() - 1); i >= 0; i--) {
				listProcSol.add(sol.getProcSolSolid().get(i));
			}
		}

		BindUtils.postNotifyChange(null, null,
				IngProcSolicitudesController.this, "interes");
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
		BindUtils.postNotifyChange(null, null,
				IngProcSolicitudesController.this, "listparReqSol");
	}

	public String concat(String... a) {
		String returnString = "";
		for (int i = 0; i < a.length; i++) {
			returnString = returnString + a[i];
		}
		return returnString;
	}

	@Command
	public void createUsuario() {
		// campos para validar los si estan vacio
		intDao.newManager();
		procSolDao.newManager();
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
			Messagebox.show(reqfal, "Informe", Messagebox.OK,
					Messagebox.EXCLAMATION, new EventListener<Event>() {
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
		sol.setSolEstado("ING");
		if (tipop == "M")
			intDao.actualizar(sol);
		else
			intDao.crear(sol);
		for (int i = 0; i < listparReqSol.size(); i++) {
			listparReqSol.get(i).setSolReqDoc(sol);
			reqSolDao.newManager();
			if (listparReqSol.get(i).getInsId() != null)
				reqSolDao.actualizar(listparReqSol.get(i));
			else
				reqSolDao.crear(listparReqSol.get(i));
		}
		limpiar();
		if (tipop == "M")
			Messagebox.show("Solicitud Modificada", "Informe", Messagebox.OK,
					Messagebox.INFORMATION, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});
		else
			Messagebox.show("Solicitud Ingresada", "Informe", Messagebox.OK,
					Messagebox.INFORMATION, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});

		BindUtils.postNotifyChange(null, null,
				IngProcSolicitudesController.this, "sol");

		BindUtils.postNotifyChange(null, null,
				IngProcSolicitudesController.this, "listparReqSol");

	}

	public void limpiar() {
		listparReqSol = new ArrayList<>();
		sol = new GmGesSolicitud();
		BindUtils.postNotifyChange(null, null,
				IngProcSolicitudesController.this, "sol");

		BindUtils.postNotifyChange(null, null,
				IngProcSolicitudesController.this, "listparReqSol");
	}

	@Command
	public void agregarDet() {
		if (!sol.getSolUsuAsig().getUsuId().equals(usu.getUsuId())) {
			Messagebox
					.show("La modificación de la solicitud sólo la puede realizar el usuario responsable actual",
							"Error", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			if (!sol.getSolEstado().equals("APR")
					|| !sol.getSolEstado().equals("REC")) {
				if (window == null) {
					boolean b = false;
					for (int i = 0; i < listProcSol.size(); i++) {
						if (listProcSol.get(i).getProcSolEstado().equals("APR")
								|| listProcSol.get(i).getProcSolEstado()
										.equals("REC")) {
							b = true;
							break;
						}
					}
					if (!b) {
						window = (Window) Executions.createComponents(
								"/catastroadm/cat_003_B.zul", null, null);
						window.doModal();
						window.addEventListener(Events.ON_CLOSE,
								new EventListener<Event>() {
									@Override
									public void onEvent(Event arg0)
											throws Exception {
										window = null;
										listProcSol = procSolDao
												.getProcSolBySol(sol);
										if (listProcSol.get(0)
												.getProcSolEstado()
												.equals("TER")
												|| listProcSol.get(0)
														.getProcSolEstado()
														.equals("APR")
												|| listProcSol.get(0)
														.getProcSolEstado()
														.equals("REC"))
											btnagrega.setVisible(false);
										BindUtils
												.postNotifyChange(
														null,
														null,
														IngProcSolicitudesController.this,
														"listProcSol");
									}
								});
					} else {
						Messagebox
								.show("No puede agregar más detalle si la solicitud esta Aprobada o Rechazada ",
										"Error", Messagebox.OK,
										Messagebox.ERROR);
					}
				}
			} else {
				Messagebox
						.show("No puede agregar más detalle si la solicitud esta Aprobada o Rechazada ",
								"Error", Messagebox.OK, Messagebox.ERROR);
			}
		}
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
}
