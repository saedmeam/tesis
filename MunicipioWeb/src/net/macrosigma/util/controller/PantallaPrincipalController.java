package net.macrosigma.util.controller;

import java.util.ArrayList;
import java.util.List;

import net.macrosigma.seguridad.dao.GmSegMenuDao;
import net.macrosigma.seguridad.ent.GmSegMenu;
import net.macrosigma.seguridad.ent.GmSegUsuario;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

public class PantallaPrincipalController extends BaseController {

	@Wire
	Window window;

	@Wire
	Rows rowsMenu;

	GmSegMenuDao menuDao = new GmSegMenuDao();
	List<GmSegMenu> listaMenu = new ArrayList<GmSegMenu>();
	long diasRestantes = (Long) Sessions.getCurrent().getAttribute(
			"diasRestantes");
	long diasMensaje = (Long) Sessions.getCurrent().getAttribute("diasMensaje");

	public List<GmSegMenu> getListaMenu() {
		return listaMenu;
	}

	public void setListaMenu(List<GmSegMenu> listaMenu) {
		this.listaMenu = listaMenu;
	}

	GmSegUsuario usu = (GmSegUsuario) Sessions.getCurrent().getAttribute(
			"usuario");

	public GmSegUsuario getUsu() {
		return usu;
	}

	public void setUsu(GmSegUsuario usu) {
		this.usu = usu;
	}

	@SuppressWarnings("static-access")
	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		Sessions.getCurrent().removeAttribute("IdMenu");
		menuDao.newManager();
		listaMenu = menuDao.getMenuPorUsuario(usu);
		llenarMenu();
		cargaMensaje();
	}

	public void cargaMensaje() {
		if (diasRestantes >= 0 && diasRestantes <= diasMensaje) {
			if (diasRestantes == 1) {
				Messagebox
						.show("Por seguridad se recomienda cambiar su clave de acceso al sistema, Le queda "
								+ diasRestantes
								+ " dia para cambiar su clave, ¡ recuerde que solo hasta hoy puede cambiar su clave, si no la cambia se bloqueará !",
								"Informe", Messagebox.OK,
								Messagebox.INFORMATION);
			} else {
				Messagebox
						.show("Por seguridad se recomienda cambiar su clave de acceso al sistema, Le quedan "
								+ diasRestantes + " dias para cambiar su clave",
								"Informe", Messagebox.OK,
								Messagebox.INFORMATION);
			}
		}
		Sessions.getCurrent().setAttribute("diasRestantes", -1L);
		Sessions.getCurrent().setAttribute("diasMensaje", 10L);
	}

	@Command
	public void loadSubmenu(GmSegMenu id) {
		Sessions.getCurrent().setAttribute("IdMenu", id);
		Executions.sendRedirect("/Menu.zul");
	}

	@Command
	public void doLogoff() {
		Sessions.getCurrent().invalidate();
		Executions.sendRedirect("/Login.zul");
	}

	@Command
	public void cambioClave() {
		if (window == null) {
			window = (Window) Executions.createComponents(
					"/parametro/par_002.zul", null, null);
			window.doModal();
			window.addEventListener(Events.ON_CLOSE,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							window = null;
						}
					});
		}
	}

	public void llenarMenu() {
		int cont = 0;
		Row r = new Row();
		for (final GmSegMenu menu : listaMenu) {
			if (cont == 5) {
				rowsMenu.appendChild(r);
				cont = 0;
				r = new Row();
			}
			Button b = new Button();
			b.setClass("menu");
			b.setTooltip(menu.getMenNombre());
			b.setImage(menu.getMenIcono());
			b.setAutodisable("+self");
			b.setWidth("100%");
			b.setHeight("100%");
			b.setOrient("vertical");
			b.setLabel(menu.getMenNombre());
			b.setStyle("font-weight:bold;font-size:13pt;");
			b.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					loadSubmenu(menu);
				}
			});
			Cell c = new Cell();
			c.setClass("sobremenu");
			c.setHeight("155px");
			c.appendChild(b);
			r.appendChild(c);
			cont++;
		}
		if (cont != 0) {
			rowsMenu.appendChild(r);
		}

	}
}
