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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
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
	List<GmParParametros> listaRoles = new ArrayList<GmParParametros>();
	GmParParametroDao rolDao = new GmParParametroDao();
	List<GmGesDepartamentoCarrera> listaRolUsuarioGuardar = new ArrayList<GmGesDepartamentoCarrera>();
	List<GmGesDepartamentoCarrera> listaRolUsuarioBorrar = new ArrayList<GmGesDepartamentoCarrera>();
	GmGesDepartamentoCarreraDao gmSegRolUsuarioDao = new GmGesDepartamentoCarreraDao();

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
		if (usuario.getDepNomDep() == null) {
			txtdepartamento.setErrorMessage("Por favor ingrese Nombres");
			return;
		}
		if (listaRolUsuarioGuardar.size() == 0) {
			Messagebox.show("Debe escojer almenos un ROL", "Información",
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		} else {
			usuario.setDepCarreraDepId(listaRolUsuarioGuardar);
		}
		usuario.setUsuario(usu.getUsuario().toUpperCase());
		usuario.setUsuario(((GmSegUsuario) Sessions.getCurrent().getAttribute(
				"usuario")).getUsuUsuario());

		if (opcion == 0) {
			usuarioDao.crear(usuario);
			Messagebox.show("Departamento ingresado con exito", "Informe",
					Messagebox.OK, Messagebox.INFORMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});
			for (GmGesDepartamentoCarrera rolUsuBorrar : listaRolUsuarioGuardar) {
				rolUsuBorrar.setDepCarreraDepId(usuario);
				gmSegRolUsuarioDao.crear(rolUsuBorrar);
				gmSegRolUsuarioDao = new GmGesDepartamentoCarreraDao();
			}
		} else {

			usuario.setFechaModificacion(new Date());
			usuarioDao.actualizar(usuario);

			for (GmGesDepartamentoCarrera rolUsuBorrar : listaRolUsuarioBorrar) {
				rolUsuBorrar.setDepCarreraDepId(usuario);
				rolUsuBorrar.setEstado("INA");
				gmSegRolUsuarioDao.actualizar(rolUsuBorrar);
				gmSegRolUsuarioDao = new GmGesDepartamentoCarreraDao();
			}
			for (GmGesDepartamentoCarrera rolUsuBorrar : listaRolUsuarioGuardar) {
				rolUsuBorrar.setDepCarreraDepId(usuario);
				gmSegRolUsuarioDao.actualizar(rolUsuBorrar);
				gmSegRolUsuarioDao = new GmGesDepartamentoCarreraDao();
			}
			Messagebox.show("Departamento modificado con exito", "Informe",
					Messagebox.OK, Messagebox.INFORMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {
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

	@Command
	public void nuevo() {
		Sessions.getCurrent().setAttribute("tip_op", "N");
		Tabbox tabs = (Tabbox) winNuevoUsu.getParent().getParent().getParent()
				.getParent().getParent().getParent();
		Tabpanels tabpanels = (Tabpanels) winNuevoUsu.getParent().getParent()
				.getParent().getParent().getParent();
		Borderlayout bl = new Borderlayout();
		if (tabs.hasFellow("/seguridad/rol/rol_001.zul")) {
			Tab tab2 = (Tab) tabs.getFellow("/seguridad/rol/rol_001.zul");
			tab2.close();
		}
		// Nombre del tab
		Tab tab = new Tab("INGRESO DE ROLES");
		tab.setClosable(true);
		tab.setSelected(true);
		// Id del tab
		tab.setId("/seguridad/rol/rol_001.zul");
		tabs.getTabs().appendChild(tab);
		Tabpanel tabpanel = new Tabpanel();
		tabpanels.appendChild(tabpanel);
		Include include = new Include("/seguridad/rol/rol_001.zul");
		Center c = new Center();
		c.setAutoscroll(true);
		c.appendChild(include);
		bl.appendChild(c);
		tabpanel.appendChild(bl);
	}

}
