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

public class MantenimientoOpcionesController extends BaseController {

	@Wire
	Bandbox txtbusqueda;
	@Wire
	Window winManOpc;
	Window window;

	// private Window window;
	private GmSegMenuDao opcionDao = new GmSegMenuDao();
	private List<GmSegMenu> listOpciones = new ArrayList<GmSegMenu>();
	private GmSegMenu opcionSeleccionada = null;

	public void buscar(String nombre) {
		listOpciones = opcionDao.getOpcionPorNombre(nombre);
	}

	public List<GmSegMenu> getListOpciones() {
		return listOpciones;
	}

	public void setListOpciones(List<GmSegMenu> listOpciones) {
		this.listOpciones = listOpciones;
	}

	public GmSegMenu getOpcionSeleccionada() {
		return opcionSeleccionada;
	}

	public void setOpcionSeleccionada(GmSegMenu opcionSeleccionada) {
		this.opcionSeleccionada = opcionSeleccionada;
	}

	@Command
	public void nuevo() {
		Sessions.getCurrent().setAttribute("opcion", 0);
		if (window == null) {
			window = (Window) Executions.createComponents(
					"/seguridad/opciones/opc_001.zul", null, null);
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
									MantenimientoOpcionesController.this,
									"listOpciones");
						}
					});
		}
	}

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		buscar(null);
	}

	@Command
	public void modificar() {
		Sessions.getCurrent().setAttribute("opcionModificar",
				opcionSeleccionada);
		Sessions.getCurrent().setAttribute("opcion", 1);
		if (window == null) {
			window = (Window) Executions.createComponents(
					"/seguridad/opciones/opc_001.zul", null, null);
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
									MantenimientoOpcionesController.this,
									"listOpciones");
						}
					});
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

	@Command
	public void eliminar(@BindingParam("objeto") final GmSegMenu objeto) {
		Messagebox.show(
				"Esta seguro que sesea eliminar la opción * "
						+ objeto.getMenNombre() + " *", "Informe",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if ("onOK".equals(e.getName())) {
							opcionDao.eliminar(objeto);
							Messagebox.show("Opción eliminada exitosamente",
									"Informe", Messagebox.OK,
									Messagebox.INFORMATION);
							buscar(null);
							BindUtils.postNotifyChange(null, null,
									MantenimientoOpcionesController.this,
									"listOpciones");
						}
					}
				});
	}

	@Command
	@NotifyChange("listOpciones")
	public void buscarOpcion() {
		if (!txtbusqueda.getText().isEmpty()) {
			buscar(txtbusqueda.getText());
		} else {
			buscar(null);
		}
	}

}
