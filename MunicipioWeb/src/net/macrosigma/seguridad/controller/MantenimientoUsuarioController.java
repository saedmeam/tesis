package net.macrosigma.seguridad.controller;

import java.util.ArrayList;
import java.util.List;

import net.macrosigma.seguridad.dao.GmSegUsuarioDao;
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
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Window;

public class MantenimientoUsuarioController extends BaseController {

	@Wire
	Window winManUsu;
	Window window;
	@Wire
	Bandbox txtbusqueda;

	List<GmSegUsuario> listaUsuario = new ArrayList<GmSegUsuario>();
	GmSegUsuarioDao usuarioDao = new GmSegUsuarioDao();
	GmSegUsuario usuarioSelecionado = null;

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		buscar(null);
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
							buscar(null);
							BindUtils.postNotifyChange(null, null,
									MantenimientoUsuarioController.this, "listaUsuario");
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
								buscar(null);
								BindUtils.postNotifyChange(null, null,
										MantenimientoUsuarioController.this, "listaUsuario");
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
	public void buscar(String nombre) {
		usuarioDao.newManager();
		listaUsuario = usuarioDao.getUsuarioPorNombre(nombre);
		BindUtils.postNotifyChange(null, null,
				MantenimientoUsuarioController.this, "listaUsuario");
	}

	@Command
	@NotifyChange("listaUsuario")
	public void buscarUsuario() {
		usuarioDao.newManager();
		if (!txtbusqueda.getText().isEmpty()) {
			buscar(txtbusqueda.getText());
		} else {
			buscar(null);
		}
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
