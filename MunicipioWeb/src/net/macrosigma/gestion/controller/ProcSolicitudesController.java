package net.macrosigma.gestion.controller;

import java.util.ArrayList;
import java.util.List;

import net.macrosigma.gestion.dao.GmGesDepartamentoDao;
import net.macrosigma.gestion.dao.GmGesProcesoSolicitudDao;
import net.macrosigma.gestion.dao.GmGesSolicitudDao;
import net.macrosigma.gestion.ent.GmGesDepartamento;
import net.macrosigma.gestion.ent.GmGesProcesoSolicitud;
import net.macrosigma.gestion.ent.GmGesSolicitud;
import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.controller.BaseController;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ProcSolicitudesController extends BaseController {

	String tipop;
	Window window;
	@Wire
	Window wincreahist;
	@Wire
	Combobox cmbdesc, cmbtiposolicitud;
	@Wire
	Listbox lbxreqsol;
	@Wire
	Groupbox gpbasig, gpbestado;
	@Wire
	Radio rdbasig, rdbcam, rdbobs, rdbrec, rdbter;
	@Wire
	Textbox txtobs;
	GmGesSolicitudDao intDao = new GmGesSolicitudDao();
	GmSegUsuario usu = (GmSegUsuario) Sessions.getCurrent().getAttribute(
			"usuario");

	GmGesSolicitud sol = new GmGesSolicitud();
	GmGesProcesoSolicitud procSol = new GmGesProcesoSolicitud();
	GmGesProcesoSolicitudDao procSolDao = new GmGesProcesoSolicitudDao();
	List<GmGesDepartamento> listdep = new ArrayList<GmGesDepartamento>();
	GmGesDepartamento depSel = new GmGesDepartamento();
	GmGesDepartamentoDao depDao = new GmGesDepartamentoDao();
	List<GmSegUsuario> listUsu = new ArrayList<GmSegUsuario>();
	GmSegUsuario usuSel = new GmSegUsuario();

	Long cont = 0L;

	public List<GmSegUsuario> getListUsu() {
		return listUsu;
	}

	public void setListUsu(List<GmSegUsuario> listUsu) {
		this.listUsu = listUsu;
	}

	public GmSegUsuario getUsuSel() {
		return usuSel;
	}

	public void setUsuSel(GmSegUsuario usuSel) {
		this.usuSel = usuSel;
	}

	public GmGesProcesoSolicitud getProcSol() {
		return procSol;
	}

	public void setProcSol(GmGesProcesoSolicitud procSol) {
		this.procSol = procSol;
	}

	public List<GmGesDepartamento> getListdep() {
		return listdep;
	}

	public void setListdep(List<GmGesDepartamento> listdep) {
		this.listdep = listdep;
	}

	public GmGesDepartamento getDepSel() {
		return depSel;
	}

	public void setDepSel(GmGesDepartamento depSel) {
		this.depSel = depSel;
	}

	public GmGesSolicitud getSol() {
		return sol;
	}

	public void setSol(GmGesSolicitud sol) {
		this.sol = sol;
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

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		cargaCombo();

		sol = ((GmGesSolicitud) Sessions.getCurrent().getAttribute("cod_int"));
		BindUtils.postNotifyChange(null, null, ProcSolicitudesController.this,
				"sol");
	}

	public void cargaCombo() {
		listdep = depDao.getPreFreAct();

	}

	@Command
	public void valsel() {
		if (rdbasig.isSelected()) {
			procSol.setProcSolTipProceso("ASIG");
			gpbasig.setVisible(true);
			gpbestado.setVisible(false);
		} else {
			if (rdbcam.isSelected()) {
				procSol.setProcSolTipProceso("CAMEST");
				gpbestado.setVisible(true);
				gpbasig.setVisible(false);
			}
		}
	}

	@Command
	public void cargalistado() {
		listUsu = new ArrayList<>();
		if (depSel != null)
			listUsu.addAll(depSel.getUsuDepId());
		BindUtils.postNotifyChange(null, null, ProcSolicitudesController.this,
				"listUsu");
	}

	@Command
	public void createUsuario() {
		// campos para validar los si estan vacio

		procSol.setProcSolSolid(sol);
		if (usuSel.getUsuId() != null) {
			procSol.setProcSolUsuAct(usuSel);
			procSol.setProcSolUsuAnt(sol.getSolUsuAsig());
		} else {
			procSol.setProcSolUsuAct(sol.getSolUsuAsig());
			procSol.setProcSolUsuAnt(sol.getSolUsuAsig());
		}

		procSol.setUsuario(usu.getUsuario());
		procSol.setEstado("ACT");
		if (rdbobs.isSelected()) {
			procSol.setProcSolEstado("OBS");
			sol.setSolEstado("OBS");
		}
		if (rdbrec.isSelected()) {
			procSol.setProcSolEstado("REC");
			sol.setSolEstado("REC");
		}
		if (rdbter.isSelected()) {
			procSol.setProcSolEstado("APR");
			sol.setSolEstado("APR");
		}
		if (rdbasig.isSelected()) {
			if (usuSel == usu) {
				Messagebox.show("No se puede asignar a usted mismo.",
						"Informe", Messagebox.OK, Messagebox.ERROR);
			}
			if (depSel.getDepNomDep().equals("ESTUDIANTE")) {
				Messagebox
						.show("No se puede asignar a un usuario del departamento estudiante en el proceso de la solicitud.",
								"Informe", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			procSol.setProcSolEstado("PRO");
			procSol.setProcSolTipProceso("ASIG");
			sol.setSolEstado("PRO");
			sol.setSolUsuAsig(usuSel);

		}
		if (txtobs.getText() == null || txtobs.getText().equals("")) {
			txtobs.setErrorMessage("Debe ingresar una observación a la transacción");
			return;
		}

		if (tipop == "M")
			procSolDao.actualizar(procSol);
		else {
			if (procSol.getSolId() != null)
				Messagebox.show(
						"Usted ya ingeso esa transacción de la solicitud",
						"Informe", Messagebox.OK, Messagebox.ERROR,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event e) throws Exception {
								Events.postEvent(new Event(Events.ON_CLOSE,
										wincreahist));
							}
						});
			else
				procSolDao.crear(procSol);
		}

		intDao.actualizar(sol);
		limpiar();
		if (tipop == "M")
			Messagebox.show("Solicitud Modificada", "Informe", Messagebox.OK,
					Messagebox.ERROR, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});
		else
			Messagebox.show("Solicitud Ingresada", "Informe", Messagebox.OK,
					Messagebox.ERROR, new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});

		BindUtils.postNotifyChange(null, null, ProcSolicitudesController.this,
				"sol");

		BindUtils.postNotifyChange(null, null, ProcSolicitudesController.this,
				"listparReqSol");

	}

	public void limpiar() {
		procSol = new GmGesProcesoSolicitud();
		usuSel = new GmSegUsuario();
		listUsu = new ArrayList<>();
		depSel = new GmGesDepartamento();
		sol = new GmGesSolicitud();
		BindUtils.postNotifyChange(null, null, ProcSolicitudesController.this,
				"sol");

		BindUtils.postNotifyChange(null, null, ProcSolicitudesController.this,
				"listparReqSol");
	}
}
