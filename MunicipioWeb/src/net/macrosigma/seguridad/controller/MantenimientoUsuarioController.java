package net.macrosigma.seguridad.controller;

import java.util.ArrayList;
import java.util.List;

import net.macrosigma.gestion.dao.GmGesDepartamentoDao;
import net.macrosigma.gestion.ent.GmGesDepartamento;
import net.macrosigma.parametro.dao.GmParParametroDao;
import net.macrosigma.parametro.ent.GmParParametros;
import net.macrosigma.seguridad.dao.GmSegRolDao;
import net.macrosigma.seguridad.dao.GmSegUsuarioDao;
import net.macrosigma.seguridad.ent.GmSegRol;
import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.controller.BaseController;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class MantenimientoUsuarioController extends BaseController {

	@Wire
	Window winManUsu;
	Window window;
	@Wire
	Textbox txtbusqueda, txtnom;
	@Wire
	Combobox cmbdesc, cmbdpto, cmbrol, cmbtestado;
	List<GmSegUsuario> listaUsuario = new ArrayList<GmSegUsuario>();
	GmSegUsuarioDao usuarioDao = new GmSegUsuarioDao();
	GmSegUsuario usuarioSelecionado = null;

	List<GmParParametros> listparCarrera = new ArrayList<GmParParametros>();
	GmParParametros parCarreraSel = new GmParParametros();
	GmParParametroDao parDao = new GmParParametroDao();
	List<GmGesDepartamento> listparSol = new ArrayList<GmGesDepartamento>();
	GmGesDepartamentoDao dptoDao = new GmGesDepartamentoDao();
	GmGesDepartamento parSolSel = new GmGesDepartamento();
	List<GmSegRol> listaRoles = new ArrayList<GmSegRol>();
	GmSegRol rolSel = new GmSegRol();
	GmSegRolDao rolDao = new GmSegRolDao();

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

	public List<GmGesDepartamento> getListparSol() {
		return listparSol;
	}

	public void setListparSol(List<GmGesDepartamento> listparSol) {
		this.listparSol = listparSol;
	}

	public GmGesDepartamento getParSolSel() {
		return parSolSel;
	}

	public void setParSolSel(GmGesDepartamento parSolSel) {
		this.parSolSel = parSolSel;
	}

	public List<GmSegRol> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List<GmSegRol> listaRoles) {
		this.listaRoles = listaRoles;
	}

	public GmSegRol getRolSel() {
		return rolSel;
	}

	public void setRolSel(GmSegRol rolSel) {
		this.rolSel = rolSel;
	}

	@SuppressWarnings("static-access")
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		listaRoles = rolDao.getRoles();
		listparSol = dptoDao.getPreFreAct();
		listparCarrera = parDao.getParametroByDesPad("CARRERAS");
		buscar(null, null, null, null, null, null);

	}

	@Command
	public void nuevo() {
		Sessions.getCurrent().setAttribute("opcion", 0);
		if (window == null) {
			window = (Window) Executions.createComponents(
					"/seguridad/usuario/usu_001.zul", null, null);
			window.doModal();
			window.setMaximizable(true);
			window.setClosable(true);
			window.setWidth("60%");
			window.setHeight("60%");
			window.addEventListener(Events.ON_CLOSE,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							window = null;
							buscar(null, null, null, null, null, null);
							BindUtils.postNotifyChange(null, null,
									MantenimientoUsuarioController.this,
									"listaUsuario");
						}
					});
		}

	}

	@Command
	// RECIBIR PARAMETRO ENVIADO DESDE ZK :)
	public void modificar() {
		if (usuarioSelecionado != null) {
			Sessions.getCurrent().setAttribute("usuarioModificar",
					usuarioSelecionado);
			Sessions.getCurrent().setAttribute("opcion", 1);
			if (window == null) {
				window = (Window) Executions.createComponents(
						"/seguridad/usuario/usu_001.zul", null, null);
				window.doModal();
				window.setClosable(true);
				window.setMaximizable(true);
				window.setWidth("60%");
				window.setHeight("60%");
				window.addEventListener(Events.ON_CLOSE,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								window = null;
								buscar(null, null, null, null, null, null);
								BindUtils.postNotifyChange(null, null,
										MantenimientoUsuarioController.this,
										"listaUsuario");
							}
						});
			}
		} else {
			Messagebox.show("Debe Seleccionar el registro que desea modificar",
					"Informe", Messagebox.OK, Messagebox.EXCLAMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});
		}
	}

	@SuppressWarnings("static-access")
	public void buscar(String usuario, String nombre, String carrera,
			String dpto, String rol, String estado) {
		usuarioDao.newManager();
		listaUsuario = usuarioDao.getUsuarioPorNombre(usuario, nombre, carrera,
				dpto, rol, estado);
		BindUtils.postNotifyChange(null, null,
				MantenimientoUsuarioController.this, "listaUsuario");
	}

	@SuppressWarnings("static-access")
	@Command
	@NotifyChange("listaUsuario")
	public void limpiar() {
		txtbusqueda.setText("");
		txtnom.setText("");
		parCarreraSel = new GmParParametros();
		parSolSel = new GmGesDepartamento();
		rolSel = new GmSegRol();
		listaUsuario = usuarioDao.getUsuarioPorNombre(null, null, null, null,
				null, null);
		BindUtils.postNotifyChange(null, null,
				MantenimientoUsuarioController.this, "parCarreraSel");
		BindUtils.postNotifyChange(null, null,
				MantenimientoUsuarioController.this, "parSolSel");
		BindUtils.postNotifyChange(null, null,
				MantenimientoUsuarioController.this, "rolSel");
		BindUtils.postNotifyChange(null, null,
				MantenimientoUsuarioController.this, "listaUsuario");
	}

	@Command
	@NotifyChange("listaUsuario")
	public void buscarUsuario() {
		String usuario = "", nombre = "", carr = "", dpto = "", rol = "", estado = "";
		// txtbusqueda, txtnom;
		// cmbdesc,cmbdpto,cmbrol,cmbtestado;
		usuarioDao.newManager();
		if (!txtbusqueda.getText().isEmpty()) {
			usuario = txtbusqueda.getText();
		} else {
			usuario = null;
		}
		if (!txtnom.getText().isEmpty())
			nombre = txtnom.getText();
		else
			nombre = null;
		if (cmbdesc.getSelectedIndex() != -1)
			carr = cmbdesc.getSelectedItem().getValue().toString();
		else
			carr = null;
		if (cmbdpto.getSelectedIndex() != -1)
			dpto = cmbdpto.getSelectedItem().getValue().toString();
		else
			dpto = null;
		if (cmbrol.getSelectedIndex() != -1)
			rol = cmbrol.getSelectedItem().getValue().toString();
		else
			rol = null;
		if (cmbtestado.getSelectedIndex() != -1)
			estado = cmbtestado.getSelectedItem().getValue().toString();
		else
			estado = null;
		buscar(usuario, nombre, carr, dpto, rol, estado);
	}

	public List<GmSegUsuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<GmSegUsuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public GmSegUsuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(GmSegUsuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

}
