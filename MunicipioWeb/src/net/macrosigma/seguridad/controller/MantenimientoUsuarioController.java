package net.macrosigma.seguridad.controller;

import java.util.ArrayList;
import java.util.List;

import net.macrosigma.seguridad.dao.GmSegUsuarioDao;
import net.macrosigma.seguridad.ent.GmSegUsuario;
import net.macrosigma.util.controller.BaseController;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
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

public class MantenimientoUsuarioController extends BaseController {

	@Wire
	Window winManUsu;
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
		Tabbox tabs = (Tabbox) winManUsu.getParent().getParent().getParent()
				.getParent().getParent().getParent();
		Tabpanels tabpanels = (Tabpanels) winManUsu.getParent().getParent()
				.getParent().getParent().getParent();
		Borderlayout bl = new Borderlayout();
		if (tabs.hasFellow("/seguridad/usuario/usu_001.zul")) {
			Tab tab2 = (Tab) tabs.getFellow("/seguridad/usuario/usu_001.zul");
			tab2.close();
		}
		// Nombre del tab
		Tab tab = new Tab("INGRESO DE USUARIOS");
		tab.setClosable(true);
		tab.setSelected(true);
		// Id del tab
		tab.setId("/seguridad/usuario/usu_001.zul");
		tabs.getTabs().appendChild(tab);
		Tabpanel tabpanel = new Tabpanel();
		tabpanels.appendChild(tabpanel);
		Include include = new Include("/seguridad/usuario/usu_001.zul");
		Center c = new Center();
		c.setAutoscroll(true);
		c.appendChild(include);
		bl.appendChild(c);
		tabpanel.appendChild(bl);
	}

	@Command
	// RECIBIR PARAMETRO ENVIADO DESDE ZK :)
	public void modificar() {
		if (usuarioSelecionado != null) {
			Sessions.getCurrent().setAttribute("usuarioModificar",
					usuarioSelecionado);
			Sessions.getCurrent().setAttribute("opcion", 1);
			Tabbox tabs = (Tabbox) winManUsu.getParent().getParent()
					.getParent().getParent().getParent().getParent();
			Tabpanels tabpanels = (Tabpanels) winManUsu.getParent().getParent()
					.getParent().getParent().getParent();
			Borderlayout bl = new Borderlayout();
			if (tabs.hasFellow("/seguridad/usuario/usu_001.zul")) {
				Tab tab2 = (Tab) tabs
						.getFellow("/seguridad/usuario/usu_001.zul");
				tab2.close();
			}
			// Nombre del tab
			Tab tab = new Tab("MODIFICACION DE USUARIOS");
			tab.setClosable(true);
			tab.setSelected(true);
			// Id del tab
			tab.setId("/seguridad/usuario/usu_001.zul");
			tabs.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanels.appendChild(tabpanel);
			Include include = new Include("/seguridad/usuario/usu_001.zul");
			Center c = new Center();
			c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} else {
			Messagebox.show("Debe Seleccionar el registro que desea modificar");
		}
	}

	@SuppressWarnings("static-access")
	public void buscar(String nombre) {
		listaUsuario = usuarioDao.getUsuarioPorNombre(nombre);
	}

	@Command
	@NotifyChange("listaUsuario")
	public void buscarUsuario() {
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
