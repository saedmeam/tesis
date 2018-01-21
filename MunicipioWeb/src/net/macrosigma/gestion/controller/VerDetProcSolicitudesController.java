package net.macrosigma.gestion.controller;

import java.util.ArrayList;
import java.util.List;

import net.macrosigma.gestion.dao.GmGesProcesoSolicitudDao;
import net.macrosigma.gestion.dao.GmGesSolicitudDao;
import net.macrosigma.gestion.ent.GmGesProcesoSolicitud;
import net.macrosigma.gestion.ent.GmGesSolicitud;
import net.macrosigma.gestion.ent.GmGesSolicitudRequisitoDocumento;
import net.macrosigma.parametro.dao.GmParParametroDao;
import net.macrosigma.parametro.ent.GmParParametros;
import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.controller.BaseController;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

public class VerDetProcSolicitudesController extends BaseController {

	String tipop;
	Window window;
	@Wire
	Combobox cmbdesc, cmbtiposolicitud;
	@Wire
	Button btnagrega;
	@Wire
	Listbox lbxreqsol;

	GmGesSolicitudDao intDao = new GmGesSolicitudDao();
	GmSegUsuario usu = (GmSegUsuario) Sessions.getCurrent().getAttribute(
			"usuario");
	List<GmParParametros> listparCarrera = new ArrayList<GmParParametros>();
	GmParParametros parCarreraSel = new GmParParametros();
	GmParParametroDao parDao = new GmParParametroDao();
	List<GmParParametros> listparSol = new ArrayList<GmParParametros>();
	GmParParametros parSolSel = new GmParParametros();
	List<GmGesSolicitudRequisitoDocumento> listparReqSol = new ArrayList<GmGesSolicitudRequisitoDocumento>();
	GmGesSolicitud sol = new GmGesSolicitud();
	List<GmGesProcesoSolicitud> listProcSol = new ArrayList<GmGesProcesoSolicitud>();
	GmGesProcesoSolicitudDao procSolDao = new GmGesProcesoSolicitudDao();

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

			parCarreraSel = sol.getSolCarrera();
			parSolSel = sol.getSolTipoSolicitud();
			listparReqSol = sol.getSolReqDoc();
			for (int i = (sol.getProcSolSolid().size() - 1); i >= 0; i--) {
				listProcSol.add(sol.getProcSolSolid().get(i));
			}
		}

		BindUtils.postNotifyChange(null, null,
				VerDetProcSolicitudesController.this, "interes");
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
				VerDetProcSolicitudesController.this, "listparReqSol");
	}

	public String concat(String... a) {
		String returnString = "";
		for (int i = 0; i < a.length; i++) {
			returnString = returnString + a[i];
		}
		return returnString;
	}

	public void limpiar() {
		listparReqSol = new ArrayList<>();
		sol = new GmGesSolicitud();
		BindUtils.postNotifyChange(null, null,
				VerDetProcSolicitudesController.this, "sol");

		BindUtils.postNotifyChange(null, null,
				VerDetProcSolicitudesController.this, "listparReqSol");
	}

}
