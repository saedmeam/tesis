package net.macrosigma.gestion.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.macrosigma.gestion.dao.GmGesContenidoTipSolicitudDao;
import net.macrosigma.gestion.ent.GmGesContenidoTipSolicitud;
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

public class IngContenidoSolicitudController extends BaseController {

	@Wire
	Window winNuevoUsu;

	@Wire
	Combobox cmbdep, cmbtiposolicitud, cmbdesc;

	int opcion = (Integer) Sessions.getCurrent().getAttribute("opcion");

	GmSegUsuario usu = (GmSegUsuario) Sessions.getCurrent().getAttribute(
			"usuario");
	GmGesContenidoTipSolicitud cont = new GmGesContenidoTipSolicitud();
	GmGesContenidoTipSolicitudDao contDao = new GmGesContenidoTipSolicitudDao();
	GmParParametroDao parDao = new GmParParametroDao();
	List<GmParParametros> listparSol = new ArrayList<GmParParametros>();
	GmParParametros parSolSel = new GmParParametros();

	public GmGesContenidoTipSolicitud getCont() {
		return cont;
	}

	public void setCont(GmGesContenidoTipSolicitud cont) {
		this.cont = cont;
	}

	public GmSegUsuario getUsu() {
		return usu;
	}

	public void setUsu(GmSegUsuario usu) {
		this.usu = usu;
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

	@SuppressWarnings("static-access")
	public void cargarRoles() {

		listparSol = parDao.getParametroByDesPad("TIPOS DE SOLICITUD");
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		Sessions.getCurrent().removeAttribute("opcion");
		cargarUsuarioMod();
		cargarRoles();

	}

	public void cargarUsuarioMod() {
		if (opcion == 1) {
			cont = (GmGesContenidoTipSolicitud) Sessions.getCurrent()
					.getAttribute("cod_int");
			winNuevoUsu.setTitle("Modificar_Usuario [CAT_004_A]");
			Sessions.getCurrent().removeAttribute("opcion");
			Sessions.getCurrent().removeAttribute("usuarioModificar");

			parSolSel = cont.getContTipSolId();

			BindUtils.postNotifyChange(null, null,
					IngContenidoSolicitudController.this, "cont");
		}
	}

	@Command
	public void crearUsuario() {
		contDao.newManager();

		if (parSolSel == null) {
			cmbdep.setErrorMessage("Por favor seleccione alguna solicitud");
			return;
		} else {
			cont.setContTipSolId(parSolSel);
		}

		cont.setUsuario(usu.getUsuario().toUpperCase());

		if (opcion == 0) {
			contDao.crear(cont);
			Messagebox.show("Contenido Solicitud ingresado con éxito",
					"Informe", Messagebox.OK, Messagebox.INFORMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
							Events.postEvent(new Event(Events.ON_CLOSE,
									winNuevoUsu));
						}
					});
		} else {

			cont.setFechaModificacion(new Date());
			contDao.actualizar(cont);

			Messagebox.show("Contenido Solicitud modificado con éxito",
					"Informe", Messagebox.OK, Messagebox.INFORMATION,
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
		cont = new GmGesContenidoTipSolicitud();
		parSolSel = new GmParParametros();
	}

}
