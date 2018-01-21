package net.macrosigma.gestion.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.macrosigma.gestion.dao.GmGesDepartamentoCarreraDao;
import net.macrosigma.gestion.dao.GmGesDepartamentoDao;
import net.macrosigma.gestion.ent.GmGesDepartamento;
import net.macrosigma.gestion.ent.GmGesDepartamentoCarrera;
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
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class IngDepartamentoController extends BaseController {

	@Wire
	Window winNuevoUsu, window;
	@Wire
	Listbox lbxRolesAgregados, lbxAgregarRoles;
	@Wire
	Textbox txtdepartamento;
	@Wire
	Popup popRoles, nombrerol;

	int opcion = (Integer) Sessions.getCurrent().getAttribute("opcion");

	GmSegUsuario usu = (GmSegUsuario) Sessions.getCurrent().getAttribute(
			"usuario");
	GmGesDepartamento usuario = new GmGesDepartamento();
	GmGesDepartamento usuarioModificar = new GmGesDepartamento();
	GmGesDepartamentoDao usuarioDao = new GmGesDepartamentoDao();
	GmParParametroDao rolDao = new GmParParametroDao();
	GmGesDepartamentoCarreraDao gmSegRolUsuarioDao = new GmGesDepartamentoCarreraDao();
	List<GmParParametros> listaRoles = new ArrayList<GmParParametros>();

	List<GmGesDepartamentoCarrera> listaRolUsuarioGuardar = new ArrayList<GmGesDepartamentoCarrera>();
	List<GmGesDepartamentoCarrera> listaRolUsuarioBorrar = new ArrayList<GmGesDepartamentoCarrera>();

	public GmGesDepartamento getUsuario() {
		return usuario;
	}

	public void setUsuario(GmGesDepartamento usuario) {
		this.usuario = usuario;
	}

	public GmGesDepartamento getUsuarioModificar() {
		return usuarioModificar;
	}

	public void setUsuarioModificar(GmGesDepartamento usuarioModificar) {
		this.usuarioModificar = usuarioModificar;
	}

	public List<GmGesDepartamentoCarrera> getListaRolUsuarioGuardar() {
		return listaRolUsuarioGuardar;
	}

	public void setListaRolUsuarioGuardar(
			List<GmGesDepartamentoCarrera> listaRolUsuarioGuardar) {
		this.listaRolUsuarioGuardar = listaRolUsuarioGuardar;
	}

	@SuppressWarnings("static-access")
	public void cargarRoles() {
		listaRoles = rolDao.getParametroByDesPad("CARRERAS");
	}

	public List<GmParParametros> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List<GmParParametros> listaRoles) {
		this.listaRoles = listaRoles;
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
			usuarioModificar = (GmGesDepartamento) Sessions.getCurrent()
					.getAttribute("cod_int");
			winNuevoUsu.setTitle("Modificar_Usuario [usu_001]");
			Sessions.getCurrent().removeAttribute("opcion");
			Sessions.getCurrent().removeAttribute("usuarioModificar");
			usuario = usuarioModificar;

			listaRolUsuarioGuardar = usuarioModificar.getDepCarreraDepId();
			BindUtils.postNotifyChange(null, null,
					IngDepartamentoController.this, "usuario");
		}
	}

	@Command
	public void crearUsuario() {
		usuarioDao.newManager();
		rolDao.newManager();
		gmSegRolUsuarioDao.newManager();
		if (usuario.getDepNomDep() == null) {
			txtdepartamento.setErrorMessage("Por favor ingrese Nombres");
			return;
		}
		if (listaRolUsuarioGuardar.size() == 0) {
			Messagebox.show("Debe escojer almenos un ROL", "Información",
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		} else {
			usuario.setDepCarreraDepId(listaRolUsuarioGuardar);
		}
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
			for (GmGesDepartamentoCarrera rolUsuBorrar : listaRolUsuarioGuardar) {
				rolUsuBorrar.setDepCarreraDepId(usuario);
				if (rolUsuBorrar.getInsId() > 0)
					gmSegRolUsuarioDao.actualizar(rolUsuBorrar);
				else
					gmSegRolUsuarioDao.crear(rolUsuBorrar);
				gmSegRolUsuarioDao.newManager();
			}
		} else {

			usuario.setFechaModificacion(new Date());
			usuarioDao.actualizar(usuario);

			for (GmGesDepartamentoCarrera rolUsuBorrar : listaRolUsuarioGuardar) {
				rolUsuBorrar.setDepCarreraDepId(usuario);
				if (rolUsuBorrar.getInsId() > 0)
					gmSegRolUsuarioDao.actualizar(rolUsuBorrar);
				else
					gmSegRolUsuarioDao.crear(rolUsuBorrar);
				gmSegRolUsuarioDao.newManager();
			}
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
		usuario = new GmGesDepartamento();
		txtdepartamento.setText("");
	}

	@Command
	public void agregarRoles() {
		GmParParametros rol = new GmParParametros();
		GmGesDepartamentoCarrera ru = new GmGesDepartamentoCarrera();
		for (Listitem item : lbxAgregarRoles.getItems()) {
			int band = 0;
			if (item.isSelected()) {
				rol = (GmParParametros) item.getValue();
				if (listaRolUsuarioGuardar.size() > 0) {
					for (GmGesDepartamentoCarrera r : listaRolUsuarioGuardar) {
						System.out.println(rol.getPar_id());
						System.out.println(r.getDepCarreraId().getPar_id());
						Long idnuevcarr = rol.getPar_id();
						Long idregcarr = r.getDepCarreraId().getPar_id();
						if (idnuevcarr.equals(idregcarr)) {
							band = 1;
						}
					}
				}
				if (band == 0) {
					ru = new GmGesDepartamentoCarrera();
					ru.setDepCarreraId(rol);
					ru.setDepCarreraDepId(usuario);
					ru.setUsuario(((GmSegUsuario) Sessions.getCurrent()
							.getAttribute("usuario")).getUsuUsuario());
					listaRolUsuarioGuardar.add(ru);
				}
			}

		}
		popRoles.close();
		lbxAgregarRoles.clearSelection();
		BindUtils.postNotifyChange(null, null, IngDepartamentoController.this,
				"listaRolUsuarioGuardar");

	}

	@Command
	public void removerRol(
			@BindingParam("objeto") GmGesDepartamentoCarrera objeto) {
		listaRolUsuarioGuardar.remove(objeto);
		listaRolUsuarioBorrar.remove(objeto);
		objeto.setEstado("INA");
		listaRolUsuarioBorrar.add(objeto);
		BindUtils.postNotifyChange(null, null, IngDepartamentoController.this,
				"listaRolUsuarioGuardar");

	}

}
