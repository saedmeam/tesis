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
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Window;

public class MantenimientoModuloController extends BaseController {

	@Wire
	Window winManMod;

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
		Tabbox tabs = (Tabbox) winManMod.getParent().getParent().getParent()
				.getParent().getParent().getParent();
		Tabpanels tabpanels = (Tabpanels) winManMod.getParent().getParent()
				.getParent().getParent().getParent();
		Borderlayout bl = new Borderlayout();
		if (tabs.hasFellow("/seguridad/modulo/mod_001.zul")) {
			Tab tab2 = (Tab) tabs.getFellow("/seguridad/modulo/mod_001.zul");
			tab2.close();
		}
		// Nombre del tab
		Tab tab = new Tab("INGRESO DE MODULOS");
		tab.setClosable(true);
		tab.setSelected(true);
		// Id del tab
		tab.setId("/seguridad/modulo/mod_001.zul");
		tabs.getTabs().appendChild(tab);
		Tabpanel tabpanel = new Tabpanel();
		tabpanels.appendChild(tabpanel);
		Include include = new Include("/seguridad/modulo/mod_001.zul");
		Center c = new Center();
		c.setAutoscroll(true);
		c.appendChild(include);
		bl.appendChild(c);
		tabpanel.appendChild(bl);
	}

	@Command
	public void modificar() {
		if (moduloSelecionado != null) {
			Sessions.getCurrent().setAttribute("opcion", 1);
			Sessions.getCurrent().setAttribute("modulo", moduloSelecionado);
			Tabbox tabs = (Tabbox) winManMod.getParent().getParent()
					.getParent().getParent().getParent().getParent();
			Tabpanels tabpanels = (Tabpanels) winManMod.getParent().getParent()
					.getParent().getParent().getParent();
			Borderlayout bl = new Borderlayout();
			if (tabs.hasFellow("/seguridad/modulo/mod_001.zul")) {
				Tab tab2 = (Tab) tabs
						.getFellow("/seguridad/modulo/mod_001.zul");
				tab2.close();
			}
			// Nombre del tab
			Tab tab = new Tab("MODIFICACIÓN DE MODULOS");
			tab.setClosable(true);
			tab.setSelected(true);
			// Id del tab
			tab.setId("/seguridad/modulo/mod_001.zul");
			tabs.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanels.appendChild(tabpanel);
			Include include = new Include("/seguridad/modulo/mod_001.zul");
			Center c = new Center();
			c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} else {
			Messagebox.show("Debe Seleccionar el registro que desea modificar");
		}
	}

	@Command
	public void eliminar(@BindingParam("objeto") final GmSegMenu objeto) {
		Messagebox.show(
				"Esta seguro que sesea eliminar el modulo * "
						+ objeto.getMenNombre() + " *", "Informe",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if ("onOK".equals(e.getName())) {
							moduloDao.eliminar(objeto);
							Messagebox.show("Modulo eliminado exitosamente",
									"Informe", Messagebox.OK,
									Messagebox.INFORMATION);
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
