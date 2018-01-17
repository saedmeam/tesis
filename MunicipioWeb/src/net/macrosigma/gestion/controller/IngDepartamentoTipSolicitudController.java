package net.macrosigma.gestion.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.macrosigma.gestion.dao.GmGesDepartamentoDao;
import net.macrosigma.gestion.dao.GmGesDepartamentoTipSolicitudDao;
import net.macrosigma.gestion.ent.GmGesDepartamento;
import net.macrosigma.gestion.ent.GmGesDepartamentoTipSolicitud;
import net.macrosigma.parametro.dao.GmParParametroDao;
import net.macrosigma.parametro.ent.GmParParametros;
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
import org.zkoss.zul.Window;

public class IngDepartamentoTipSolicitudController extends BaseController {

	@Wire
	Window winNuevoUsu;

	@Wire
	Combobox cmbdep, cmbtiposolicitud, cmbdesc;

	int opcion = (Integer) Sessions.getCurrent().getAttribute("opcion");

	GmSegUsuario usu = (GmSegUsuario) Sessions.getCurrent().getAttribute(
			"usuario");
	GmGesDepartamentoTipSolicitud usuario = new GmGesDepartamentoTipSolicitud();
	GmGesDepartamentoTipSolicitud usuarioModificar = new GmGesDepartamentoTipSolicitud();
	GmGesDepartamentoTipSolicitudDao usuarioDao = new GmGesDepartamentoTipSolicitudDao();
	GmParParametroDao parDao = new GmParParametroDao();
	List<GmParParametros> listparSol = new ArrayList<GmParParametros>();
	GmParParametros parSolSel = new GmParParametros();
	List<GmSegUsuario> listdep = new ArrayList<GmSegUsuario>();
	GmSegUsuario depSel = new GmSegUsuario();
	List<GmParParametros> listparCarrera = new ArrayList<GmParParametros>();
	GmParParametros parCarreraSel = new GmParParametros();

	List<GmGesDepartamento> listRol = new ArrayList<GmGesDepartamento>();
	GmGesDepartamento rolSel = new GmGesDepartamento();
	GmGesDepartamentoDao rolDao = new GmGesDepartamentoDao();

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

	public List<GmGesDepartamento> getListRol() {
		return listRol;
	}

	public void setListRol(List<GmGesDepartamento> listRol) {
		this.listRol = listRol;
	}

	public GmGesDepartamento getRolSel() {
		return rolSel;
	}

	public void setRolSel(GmGesDepartamento rolSel) {
		this.rolSel = rolSel;
	}

	public GmSegUsuario getUsu() {
		return usu;
	}

	public void setUsu(GmSegUsuario usu) {
		this.usu = usu;
	}

	public GmGesDepartamentoTipSolicitud getUsuario() {
		return usuario;
	}

	public void setUsuario(GmGesDepartamentoTipSolicitud usuario) {
		this.usuario = usuario;
	}

	public GmGesDepartamentoTipSolicitud getUsuarioModificar() {
		return usuarioModificar;
	}

	public void setUsuarioModificar(
			GmGesDepartamentoTipSolicitud usuarioModificar) {
		this.usuarioModificar = usuarioModificar;
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

	public List<GmSegUsuario> getListdep() {
		return listdep;
	}

	public void setListdep(List<GmSegUsuario> listdep) {
		this.listdep = listdep;
	}

	public GmSegUsuario getDepSel() {
		return depSel;
	}

	public void setDepSel(GmSegUsuario depSel) {
		this.depSel = depSel;
	}

	@SuppressWarnings("static-access")
	public void cargarRoles() {
		listRol = rolDao.getPreFreAct();
		listparCarrera = parDao.getParametroByDesPad("CARRERAS");
		listparSol = parDao.getParametroByDesPad("TIPOS DE SOLICITUD");
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		Sessions.getCurrent().removeAttribute("opcion");
		cargarUsuarioMod();
		cargarRoles();

	}

	@Command
	public void getUsuarioXRol() {
		listdep = new ArrayList<>();
		if (rolSel != null) {
			for (int i = 0; i < rolSel.getUsuDepId().size(); i++) {
				listdep.add(rolSel.getUsuDepId().get(i));
			}
		}

		BindUtils.postNotifyChange(null, null,
				IngDepartamentoTipSolicitudController.this, "listdep");
	}

	public void cargarUsuarioMod() {
		if (opcion == 1) {
			usuarioModificar = (GmGesDepartamentoTipSolicitud) Sessions
					.getCurrent().getAttribute("cod_int");
			winNuevoUsu.setTitle("Modificar_Usuario [CAT_004_A]");
			Sessions.getCurrent().removeAttribute("opcion");
			Sessions.getCurrent().removeAttribute("usuarioModificar");
			usuario = usuarioModificar;
			depSel = usuario.getDepDepUsuId();
			parSolSel = usuario.getDepTipSolCarreraId();
			parCarreraSel = usuario.getDepTipSolCarreraId();

			BindUtils.postNotifyChange(null, null,
					IngDepartamentoTipSolicitudController.this, "usuario");
		}
	}

	@Command
	public void crearUsuario() {
		if (depSel == null) {
			cmbdep.setErrorMessage("Por favor seleccione algún departamento");
			return;
		} else {
			usuario.setDepDepUsuId(depSel);
		}
		if (parSolSel == null) {
			cmbdep.setErrorMessage("Por favor seleccione alguna solicitud");
			return;
		} else {
			usuario.setDepTipSolTipSolId(parSolSel);
		}
		if (parCarreraSel == null) {
			cmbdesc.setErrorMessage("Por favor seleccione alguna solicitud");
			return;
		} else
			usuario.setDepTipSolCarreraId(parCarreraSel);
		usuario.setUsuario(usu.getUsuario().toUpperCase());
		usuario.setUsuario(((GmSegUsuario) Sessions.getCurrent().getAttribute(
				"usuario")).getUsuUsuario());

		if (opcion == 0) {
			usuarioDao.crear(usuario);
			Messagebox.show("Departamento ingresado con éxito", "Informe",
					Messagebox.OK, Messagebox.INFORMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							Events.postEvent(new Event(Events.ON_CLOSE,
									winNuevoUsu));
						}
					});
		} else {

			usuario.setFechaModificacion(new Date());
			usuarioDao.actualizar(usuario);

			Messagebox.show("Departamento modificado con éxito", "Informe",
					Messagebox.OK, Messagebox.INFORMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							Events.postEvent(new Event(Events.ON_CLOSE,
									winNuevoUsu));
						}
					});
		}
	}

	public void limpiar() {
		usuario = new GmGesDepartamentoTipSolicitud();
		parSolSel = new GmParParametros();
		depSel = new GmSegUsuario();
	}

}
