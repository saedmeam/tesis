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
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zkmax.zul.Navitem;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

public class MenuController extends BaseController {

	GmSegMenu idmenu = new GmSegMenu();
	Window window;

	@Wire
	Navbar navbar;
	@Wire
	Label lbNombreModulo;
	@Wire("#incCont")
	private Include incCont;

	GmSegMenuDao menuDao = new GmSegMenuDao();
	List<GmSegMenu> listaMenu = new ArrayList<GmSegMenu>();
	GmSegUsuario usu = (GmSegUsuario) Sessions.getCurrent().getAttribute(
			"usuario");

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		menuDao.newManager();
		Selectors.wireComponents(view, this, false);
		idmenu = (GmSegMenu) Sessions.getCurrent().getAttribute("IdMenu");
		llenarNavbar(idmenu);
		cargarNombreMenu();

	}

	public GmSegUsuario getUsu() {
		return usu;
	}

	public void setUsu(GmSegUsuario usu) {
		this.usu = usu;
	}

	@SuppressWarnings("static-access")
	public void llenarNavbar(GmSegMenu Id) {
		listaMenu = menuDao.getMenuPorModulo(usu, Id);
		if (listaMenu == null)
			return;
		for (final GmSegMenu padre : listaMenu) {
			Navitem item = new Navitem();
			item.setLabel(padre.getMenNombre());
			item.setImage(padre.getMenIcono());
			item.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					incCont.setSrc(padre.getMenRuta());
				}
			});
			navbar.appendChild(item);
		}

	}

	// @SuppressWarnings("static-access")
	// public void llenarNavbar(GmMenu Id) {
	// listaMenu = menuDao.getMenuPorIdPadreMenu(Id);
	// if (listaMenu == null)
	// return;
	// for (final GmMenu padre : listaMenu) {
	// List<GmMenu> hijos = menuDao.getMenuPorIdPadreSubmenu(padre);
	// if (hijos == null) {
	// Navitem item = new Navitem();
	// item.setLabel(padre.getMenNombre());
	// item.setImage(padre.getMenIcono());
	// item.addEventListener(Events.ON_CLICK,
	// new EventListener<Event>() {
	// @Override
	// public void onEvent(Event arg0) throws Exception {
	// incCont.setSrc(padre.getMenRuta());
	// }
	// });
	// navbar.appendChild(item);
	// } else {
	// Nav nav = new Nav();
	// nav.setLabel(padre.getMenNombre());
	// nav.setImage(padre.getMenIcono());
	// if (hijos.size() > 0)
	// nav.setBadgeText(">>");
	// // nav.setBadgeText(hijos.size() + "");
	// navbar.appendChild(nav);
	// for (GmMenu hijo : hijos)
	// padreHijo(nav, hijo);
	//
	// }
	// }
	//
	// }
	//
	// @SuppressWarnings("static-access")
	// public void padreHijo(Nav navObj, final GmMenu obj) {
	// List<GmMenu> hijos = menuDao.getMenuPorIdPadreSubmenu(obj);
	// if (hijos == null) {
	// final Navitem item = new Navitem();
	// item.setLabel(obj.getMenNombre());
	// item.setImage(obj.getMenIcono());
	// item.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
	// @Override
	// public void onEvent(Event arg0) throws Exception {
	// incCont.setSrc(obj.getMenRuta());
	// }
	// });
	// navObj.appendChild(item);
	// } else {
	// Nav nav = new Nav();
	// nav.setLabel(obj.getMenNombre());
	// nav.setImage(obj.getMenIcono());
	// // nav.setBadgeText(hijos.size() + "");
	// if (hijos.size() > 0)
	// nav.setBadgeText(">>");
	// navObj.appendChild(nav);
	// for (GmMenu hijo : hijos)
	// padreHijo(nav, hijo);
	// }
	// }

	@Command
	public void doLogoff() {
		Sessions.getCurrent().invalidate();
		Executions.sendRedirect("/Login.zul");
	}

	public void cargarNombreMenu() {
		lbNombreModulo.setValue("MODULO " + idmenu.getMenDescripcionMenu());
	}

	@Command
	public void cambioClave() {
		if (window == null) {
			window = (Window) Executions.createComponents(
					"/parametro/par_026.zul", null, null);
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

}
