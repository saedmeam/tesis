package net.macrosigma.seguridad.controller;

import java.util.ArrayList;
import java.util.List;

import net.macrosigma.seguridad.dao.GmSegMenuDao;
import net.macrosigma.seguridad.ent.GmSegMenu;
import net.macrosigma.util.controller.BaseController;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
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

public class MantenimientoModuloController extends BaseController {

	@Wire
	Window winManMod;
	Window window;

	@Wire
	Bandbox txtbusqueda;

	// llenar tabla
	List<GmSegMenu> listaMod = new ArrayList<GmSegMenu>();
	GmSegMenuDao moduloDao = new GmSegMenuDao();
	GmSegMenu moduloSelecionado = null;

	public GmSegMenu getModuloSelecionado() {
		return moduloSelecionado;
	}

	public void setModuloSelecionado(GmSegMenu moduloSelecionado) {
		this.moduloSelecionado = moduloSelecionado;
	}

	@SuppressWarnings("static-access")
	public void buscar(String nombre) {
		listaMod = moduloDao.getModuloPorNombre(nombre);
	}

	@Command
	@NotifyChange("listaMod")
	public void buscarModulo() {
		if (!txtbusqueda.getText().isEmpty()) {
			buscar(txtbusqueda.getText());
		} else {
			buscar(null);
		}
	}

	@Command
	public void nuevo() {
		Sessions.getCurrent().setAttribute("opcion", 0);
		if (window == null) {
			window = (Window) Executions.createComponents(
					"/seguridad/modulo/mod_001.zul", null, null);
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
									MantenimientoModuloController.this,
									"listaMod");
						}
					});
		}
	}

	@Command
	public void modificar() {
		if (moduloSelecionado != null) {
			Sessions.getCurrent().setAttribute("opcion", 1);
			Sessions.getCurrent().setAttribute("modulo", moduloSelecionado);
			if (window == null) {
				window = (Window) Executions.createComponents(
						"/seguridad/modulo/mod_001.zul", null, null);
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
										MantenimientoModuloController.this,
										"listaMod");
							}
						});
			}
		} else {
			
			Messagebox.show(
					"Debe Seleccionar el registro que desea modificar",
					"Informe", Messagebox.OK, Messagebox.EXCLAMATION,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event e) throws Exception {

						}
					});
		}
	}

	@Command
	public void eliminar(@BindingParam("objeto") final GmSegMenu objeto) {
		Messagebox.show(
				"Esta seguro que sesea eliminar el módulo * "
						+ objeto.getMenNombre() + " *", "Informe",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if ("onOK".equals(e.getName())) {
							moduloDao.eliminar(objeto);
							Messagebox.show("Módulo eliminado exitosamente",
									"Informe", Messagebox.OK,
									Messagebox.INFORMATION);
							buscar(null);
							BindUtils.postNotifyChange(null, null,
									MantenimientoModuloController.this,
									"listaMod");
						}
					}
				});

	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		buscar(null);
	}

	public List<GmSegMenu> getListaMod() {
		return listaMod;
	}

	public void setListaMod(List<GmSegMenu> listaMod) {
		this.listaMod = listaMod;
	}

}
